package be.kdg.repaircafe.frontend.controllers.web;

import be.kdg.repaircafe.backend.dom.repairs.Repair;
import be.kdg.repaircafe.backend.dom.users.User;
import be.kdg.repaircafe.backend.services.api.RepairService;
import be.kdg.repaircafe.frontend.controllers.resources.repairs.RepairResource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RepairWebController
{
    private final RepairService repairService;
    private final MapperFacade mapperFacade;

    @Autowired
    public RepairWebController(RepairService repairService, MapperFacade mapperFacade)
    {
        this.repairService = repairService;
        this.mapperFacade = mapperFacade;
    }

    @RequestMapping(value = "/getrepairs.do", method = RequestMethod.GET)
    public ModelAndView showRepairsForUser(@AuthenticationPrincipal User user, ModelAndView modelAndView)
    {
        List<Repair> repairs = repairService.findRepairsByUserId(user.getId());
        List<RepairResource> repairResources = mapperFacade.mapAsList(repairs, RepairResource.class);
        modelAndView.setViewName("repairs");
        modelAndView.addObject("repairResources", repairResources);
        return modelAndView;
    }

    @RequestMapping(value = "/newrepair.do", method = RequestMethod.GET)
    public ModelAndView showNewRepair(ModelAndView modelAndView)
    {
        modelAndView.addObject("repairResource", new RepairResource());
        modelAndView.setViewName("newrepair");
        return modelAndView;
    }

    @RequestMapping(value = "/saverepair.do", method = RequestMethod.POST)
    public ModelAndView saveRepair(
            @AuthenticationPrincipal User user,
            @Valid @ModelAttribute("repairResource") RepairResource repairResource,
            BindingResult bindingResult,
            ModelAndView modelAndView)
    {
        if (bindingResult.hasErrors())
        {
            modelAndView.setViewName("redirect:/newrepair.do");
            return modelAndView;
        }

        Repair repair = repairService.saveRepair(user.getId(), mapperFacade.map(repairResource, Repair.class));
        modelAndView.addObject("repairResource", mapperFacade.map(repair, RepairResource.class));
        modelAndView.setViewName("repairdetail");

        return modelAndView;
    }
}
