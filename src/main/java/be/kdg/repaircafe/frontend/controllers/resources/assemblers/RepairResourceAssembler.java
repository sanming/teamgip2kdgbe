package be.kdg.repaircafe.frontend.controllers.resources.assemblers;

import be.kdg.repaircafe.backend.dom.repairs.Bid;
import be.kdg.repaircafe.backend.dom.repairs.Repair;
import be.kdg.repaircafe.frontend.controllers.resources.repairs.RepairResource;
import be.kdg.repaircafe.frontend.controllers.rest.BidRestController;
import be.kdg.repaircafe.frontend.controllers.rest.RepairRestController;
import be.kdg.repaircafe.frontend.controllers.rest.UserRestController;
import ma.glasnost.orika.MapperFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by wouter on 4/11/15.
 */
@Component
public class RepairResourceAssembler extends ResourceAssemblerSupport<Repair, RepairResource>
{
    private Logger logger = Logger.getLogger(UserResourceAssembler.class);
    private MapperFacade mapper;

    @Autowired
    public RepairResourceAssembler(MapperFacade mapper)
    {
        super(RepairRestController.class, RepairResource.class);
        this.mapper = mapper;
    }

    @Override
    public List<RepairResource> toResources(Iterable<? extends Repair> repairs)
    {
        return StreamSupport
                .stream(repairs.spliterator(), false)
                .map(r -> toResource(r))
                .collect(Collectors.toList());
    }

    @Override
    public RepairResource toResource(Repair repair)
    {
        // map Entity class to Resource class using Orika Framework
        RepairResource repairResource = mapper.map(repair, RepairResource.class);

        // add HATEOAS stuff to mapped Resource
        // self link
        repairResource.add(
                linkTo(
                        methodOn(RepairRestController.class).getRepairByRepairId(repair.getId())).withSelfRel());

        // link to client own this repair
        repairResource.add(
                linkTo(
                        methodOn(UserRestController.class).findUserById(repair.getClient().getUser().getId())).withRel("client"));

        // link to repairer if one is assigned to this repair
        if (repair.getRepairer() != null)
        {
            repairResource.add(
                    linkTo(
                            methodOn(UserRestController.class).findUserById(repair.getRepairer().getUser().getId())).withRel("repairer"));
        }

        // links to bids of this repair if any
        List<Bid> bids = repair.getBids();
        bids.forEach(bid -> repairResource.add(linkTo(methodOn(BidRestController.class).getBidById(null, bid.getId())).withRel("bids")));

        return repairResource;
    }
}
