package be.kdg.repaircafe.frontend.controllers.rest;

import be.kdg.repaircafe.backend.dom.repairs.Repair;
import be.kdg.repaircafe.backend.dom.users.User;
import be.kdg.repaircafe.backend.persistence.criteria.SearchCriterium;
import be.kdg.repaircafe.backend.services.api.RepairService;
import be.kdg.repaircafe.backend.services.api.UserService;
import be.kdg.repaircafe.frontend.controllers.resources.assemblers.RepairResourceAssembler;
import be.kdg.repaircafe.frontend.controllers.resources.assemblers.UserResourceAssembler;
import be.kdg.repaircafe.frontend.controllers.resources.repairs.RepairResource;
import be.kdg.repaircafe.frontend.controllers.resources.users.PersonResource;
import be.kdg.repaircafe.frontend.controllers.resources.users.UserResource;
import be.kdg.repaircafe.frontend.controllers.resources.validators.EmailValidator;
import ma.glasnost.orika.MapperFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@ExposesResourceFor(UserResource.class)
public class UserRestController extends BaseRestController
{
    private final Logger logger = Logger.getLogger(UserRestController.class);
    private final UserService userService;
    private final RepairService repairService;
    private final MapperFacade mapperFacade;
    private final UserResourceAssembler userResourceAssembler;
    private final RepairResourceAssembler repairResourceAssembler;

    @Autowired
    public UserRestController(UserService userService, RepairService repairService,
                              MapperFacade mapperFacade, UserResourceAssembler userResourceAssembler,
                              RepairResourceAssembler repairResourceAssembler)
    {
        this.userService = userService;
        this.repairService = repairService;
        this.mapperFacade = mapperFacade;
        this.userResourceAssembler = userResourceAssembler;
        this.repairResourceAssembler = repairResourceAssembler;
    }

    @InitBinder("userResource")
    public void initBinder(WebDataBinder webDataBinder)
    {
        webDataBinder.addValidators(new EmailValidator());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody UserResource userResource)
    {
        User user_in = mapperFacade.map(userResource, User.class);
        User user_out = userService.addUser(user_in);

        logger.info(this.getClass().toString() + ": adding new user " + user_out.getUserId());
        return new ResponseEntity<>(userResourceAssembler.toResource(user_out), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserResource>> findUsers(
            @RequestParam(value = "search", required = false) String criteria)
    {
        List<SearchCriterium> searchCriteria = parseCriteria(criteria);
        List<User> users = userService.findUsersByCriteria(searchCriteria);

        List<UserResource> userResources = users.stream().map(u -> userResourceAssembler.toResource(u)).collect(Collectors.toList());
        return new ResponseEntity<>(userResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserResource> findUserById(@PathVariable int userId)
    {

        logger.info(this.getClass().toString() + ":" + userId);
        User user = userService.findUserById(userId);
        UserResource userResource = userResourceAssembler.toResource(user);
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<UserResource> updateUserById(@PathVariable int userId,
                                                       @Valid @RequestBody PersonResource personResource)
    {
        logger.info(this.getClass().toString() + ":" + "updating user " + userId);

        User user_in = userService.findUserById(userId);
        mapperFacade.map(personResource, user_in.getPerson());
        User user_out = userService.saveUser(user_in);

        return new ResponseEntity<>(userResourceAssembler.toResource(user_out), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable int userId)
    {
        logger.info(this.getClass().toString() + ": deleting user " + userId);
        userService.deleteUser(userId);
    }

    @RequestMapping(value = "/{userId}/password", method = RequestMethod.PUT)
    public ResponseEntity<UserResource> updatePassword(@PathVariable int userId,
                                                       @Valid @RequestBody UserResource userResource)

    {

        logger.info(this.getClass().toString() + ": updating user " + userId);
        userService.updatePassword(userId, userResource.getOldPassword(), userResource.getPassword());
        User user = userService.findUserById(userId);
        return new ResponseEntity<>(userResourceAssembler.toResource(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userId}/repairs", method = RequestMethod.GET)
    public ResponseEntity<List<RepairResource>> getRepairsByUser(@PathVariable Integer userId)
    {
        logger.info(this.getClass().toString() + ":" + "returning for user:" + userId);

        return new ResponseEntity<>(
                repairResourceAssembler.toResources(repairService.findRepairsByUserId(userId)),
                HttpStatus.OK);

    }

    @RequestMapping(value = "/{userId}/repairs", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<RepairResource> createRepair(
            @AuthenticationPrincipal User user,
            @PathVariable Integer userId,
            @RequestBody @Valid RepairResource repairResource)

    {
        if (!userId.equals(user.getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Repair in_repair = mapperFacade.map(repairResource, Repair.class);
        Repair out_repair = repairService.saveRepair(user.getId(), in_repair);

        return new ResponseEntity<>(repairResourceAssembler.toResource(out_repair), HttpStatus.CREATED);

    }
}
