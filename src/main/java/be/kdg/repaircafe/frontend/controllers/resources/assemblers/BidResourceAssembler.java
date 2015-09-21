package be.kdg.repaircafe.frontend.controllers.resources.assemblers;

import be.kdg.repaircafe.backend.dom.repairs.Bid;
import be.kdg.repaircafe.frontend.controllers.resources.repairs.BidResource;
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
 * Implementation of HATEOAS ResourceAssembler to facilitate creation
 * of BidResource.  Uses Orika Mapperfactory to map Bid to BidResource?
 * Adds HATEOAS links to Resource.
 */
@Component
public class BidResourceAssembler extends ResourceAssemblerSupport<Bid, BidResource>
{
    private Logger logger = Logger.getLogger(UserResourceAssembler.class);

    @Autowired
    private MapperFacade mapper;

    public BidResourceAssembler()
    {
        super(BidRestController.class, BidResource.class);
    }

    @Override
    public List<BidResource> toResources(Iterable<? extends Bid> entities)
    {
        return StreamSupport
                .stream(entities.spliterator(), false)
                .map(bid -> toResource(bid))
                .collect(Collectors.toList());
    }

    @Override
    public BidResource toResource(Bid bid)
    {
        // map Entity class to Resource class using ModelMapper Framework
        BidResource bidResource = mapper.map(bid, BidResource.class);

        // add HATEOAS stuff to mapped Resource
        bidResource.add(linkTo(methodOn(BidRestController.class).getBidById(null, bid.getId())).withSelfRel());

        // add link to repair for which this is a bid
        bidResource.add(linkTo(methodOn(RepairRestController.class).getRepairByRepairId(bid.getRepair().getId())).withRel("repair"));

        // add link to repairer who made this bid
        bidResource.add(linkTo(methodOn(UserRestController.class).findUserById(bid.getRepairer().getId())).withRel("repairer"));

        return bidResource;
    }
}
