package be.kdg.repaircafe.frontend.controllers.rest;

import be.kdg.repaircafe.backend.dom.repairs.Bid;
import be.kdg.repaircafe.backend.dom.repairs.Repair;
import be.kdg.repaircafe.backend.dom.users.User;
import be.kdg.repaircafe.backend.dom.users.roles.Client;
import be.kdg.repaircafe.backend.dom.users.roles.Repairer;
import be.kdg.repaircafe.backend.dom.users.roles.Role;
import be.kdg.repaircafe.backend.persistence.criteria.SearchCriterium;
import be.kdg.repaircafe.backend.services.api.BidService;
import be.kdg.repaircafe.backend.services.api.RepairService;
import be.kdg.repaircafe.backend.services.exceptions.RepairServiceException;
import be.kdg.repaircafe.frontend.controllers.resources.assemblers.BidResourceAssembler;
import be.kdg.repaircafe.frontend.controllers.resources.assemblers.RepairResourceAssembler;
import be.kdg.repaircafe.frontend.controllers.resources.repairs.BidResource;
import be.kdg.repaircafe.frontend.controllers.resources.repairs.RepairResource;
import ma.glasnost.orika.MapperFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/repairs")
@ExposesResourceFor(RepairResource.class)
public class RepairRestController extends BaseRestController
{
    private final Logger logger = Logger.getLogger(UserRestController.class);
    private final RepairService repairService;
    private final BidService bidService;
    private final RepairResourceAssembler repairResourceAssembler;
    private final BidResourceAssembler bidResourceAssembler;
    private final MapperFacade mapper;

    @Autowired
    public RepairRestController(RepairService repairService, BidService bidService,
                                MapperFacade mapperFacade, RepairResourceAssembler repairResourceAssembler,
                                BidResourceAssembler bidResourceAssembler)
    {
        this.repairService = repairService;
        this.bidService = bidService;
        this.mapper = mapperFacade;
        this.bidResourceAssembler = bidResourceAssembler;
        this.repairResourceAssembler = repairResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RepairResource>> findRepairs(@RequestParam(value = "search", required = false) String criteria)
    {
        logger.info(this.getClass().toString() + ":" + "returning repairs by  zero or more criteria");

        List<SearchCriterium> searchCriteria = parseCriteria(criteria);

        return new ResponseEntity<>(repairResourceAssembler.toResources(
                repairService.findRepairsByCriteria(searchCriteria)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{repairId}", method = RequestMethod.GET)
    public ResponseEntity<RepairResource> getRepairByRepairId(
            @PathVariable("repairId") Integer repairId)
    {
        Repair repair = repairService.findRepairById(repairId);
        return new ResponseEntity<>(repairResourceAssembler.toResource(repair), HttpStatus.OK);
    }


    @RequestMapping(value = "/{repairId}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<RepairResource> updateRepairByRepairId(
            @AuthenticationPrincipal User user,
            @PathVariable("repairId") Integer repairId,
            @RequestBody @Valid RepairResource repairResource)
    {
        Repair repair_in = repairService.findRepairById(repairId);

        // user can only update his own repairs
        if (!user.getId().equals(repair_in.getClient().getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        mapper.map(repairResource, repair_in);
        Repair repair_out = repairService.saveRepair(user.getId(), repair_in);
        return new ResponseEntity<>(repairResourceAssembler.toResource(repair_out), HttpStatus.OK);
    }


    @RequestMapping(value = "/{repairId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<RepairResource> deleteRepairByRepairId(
            @AuthenticationPrincipal User user,
            @PathVariable("repairId") Integer repairId)
    {
        Repair repair = repairService.findRepairById(repairId);

        // user can only delete his own repairs
        if (!user.getId().equals(repair.getClient().getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repairService.deleteRepair(repairId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{repairId}/bids", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_REPAIRER')")
    public ResponseEntity<List<BidResource>> getBids(@AuthenticationPrincipal User user,
                                                     @PathVariable(value = "repairId") Integer repairId)
    {
        boolean isOwnerClient = Role.hasRole(user, Client.class);
        if (isOwnerClient)
        {
            Repair repair = repairService.findRepairById(repairId);
            List<Repair> repairs = repairService.findRepairsByUserId(user.getId());
            if (!repairs.contains(repair))
                throw new RepairServiceException("User doesn't own this repair");
        }
        boolean isRepairer = Role.hasRole(user, Repairer.class);
        if (isOwnerClient || isRepairer)
        {
            List<Bid> bids = bidService.findBidsByRepair(repairId);
            return new ResponseEntity<>(bidResourceAssembler.toResources(bids), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @RequestMapping(value = "/{repairId}/bids", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_REPAIRER')")
    public ResponseEntity<BidResource> placeBidOnRepair(
            @AuthenticationPrincipal User user,
            @PathVariable("repairId") Integer repairId,
            @RequestBody @Valid BidResource bidResource)
    {
        Integer bidId = bidService.placeBid(user.getId(), repairId, bidResource.getPrice());
        return new ResponseEntity<>(bidResourceAssembler.toResource(bidService.findBidById(bidId)), HttpStatus.CREATED);
    }
}
