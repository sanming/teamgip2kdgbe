package be.kdg.repaircafe.frontend.controllers.web;

import be.kdg.repaircafe.backend.dom.users.User;
import be.kdg.repaircafe.backend.services.api.UserService;
import be.kdg.repaircafe.backend.services.exceptions.UserServiceException;
import be.kdg.repaircafe.frontend.controllers.resources.users.UserResource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController
{
    private final UserService userService;
    private final MapperFacade mapperFacade;

    @Autowired
    public HelloWorldController(UserService userService, MapperFacade mapperFacade)
    {
        this.userService = userService;
        this.mapperFacade = mapperFacade;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView showHomePage() throws UserServiceException
    {
        User user = userService.findUserByUsername("clarence.ho@gmail.com");
        UserResource userResource = mapperFacade.map(user, UserResource.class);

        ModelAndView mav = new ModelAndView();
        mav.addObject("userResource", userResource);
        mav.setViewName("home");

        return mav;
    }
}
