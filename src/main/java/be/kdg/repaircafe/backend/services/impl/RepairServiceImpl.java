/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kdg.repaircafe.backend.services.impl;

import be.kdg.repaircafe.backend.dom.repairs.Repair;
import be.kdg.repaircafe.backend.dom.repairs.RepairDetails;
import be.kdg.repaircafe.backend.dom.users.User;
import be.kdg.repaircafe.backend.dom.users.roles.Client;
import be.kdg.repaircafe.backend.dom.users.roles.Repairer;
import be.kdg.repaircafe.backend.dom.users.roles.Role;
import be.kdg.repaircafe.backend.persistence.api.RepairRepository;
import be.kdg.repaircafe.backend.persistence.api.UserRepository;
import be.kdg.repaircafe.backend.persistence.criteria.SearchCriterium;
import be.kdg.repaircafe.backend.persistence.criteria.SpecificationsBuilder;
import be.kdg.repaircafe.backend.persistence.criteria.repairs.DetailsSpecification;
import be.kdg.repaircafe.backend.persistence.criteria.repairs.ItemSpecification;
import be.kdg.repaircafe.backend.services.api.RepairService;
import be.kdg.repaircafe.backend.services.exceptions.RepairServiceException;
import be.kdg.repaircafe.backend.services.exceptions.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of RepairService.
 *
 * @author wouter
 */
@Service("repairService")
@Transactional
public class RepairServiceImpl implements RepairService, Serializable
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-service-layer/

    private final UserRepository userRepository;
    private final RepairRepository repairRepository;
    private final SpecificationsBuilder<Repair> specificationsBuilder;
    private final List<Class> specificationClasses = Arrays.asList(DetailsSpecification.class, ItemSpecification.class);

    @Autowired
    public RepairServiceImpl(UserRepository userRepository, RepairRepository repairRepository)
    {
        this.userRepository = userRepository;
        this.repairRepository = repairRepository;
        this.specificationsBuilder = new SpecificationsBuilder<>();
    }

    @Override
    public Repair findRepairById(Integer repairId) throws RepairServiceException
    {
        Repair repair = repairRepository.findOne(repairId);
        if (repair == null)
            throw new RepairServiceException("Repair not found");
        return repair;
    }

    public List<Repair> findAll()
    {
        List<Repair> repairs = repairRepository.findAll();
        return repairs;
    }

    /**
     * Retrieve all repairs submitted by a particular client. This includes open
     * and closed repairs.
     *
     * @param userId user id of a client
     * @return repairs held by this client
     */
    @Override
    public List<Repair> findRepairsByUserId(Integer userId) throws UserServiceException
    {
        User user = userRepository.findOne(userId);
        final Client client = Role.loadRole(user, Client.class);
        if (client == null)
            throw new UserServiceException("User has no submitted repairs");

        return repairRepository.getRepairsByClient(client);
    }

    /**
     * Retrieve all repairs open and closed.
     *
     * @return All repairs in the system
     * @throws RepairServiceException
     */
    @Override
    public List<Repair> findRepairsByCriteria(List<SearchCriterium> searchCriteria) throws RepairServiceException
    {
        specificationsBuilder.with(searchCriteria, specificationClasses);
        return repairRepository.findAll(specificationsBuilder.build());
    }

    /**
     * Sets up and saves a new Repair to the system. Sets up a bidirectional
     * association between a repair and a client.
     *
     * @param userId The userId of the user that submits the repair
     * @param repair The repair that needs to be filed.
     * @see Repair
     */
    @Override
    public Repair saveRepair(Integer userId, Repair repair) throws RepairServiceException
    {
        User user = userRepository.findOne(userId);
        if (user == null)
            throw new RepairServiceException("User not found");

        final Client client = Role.loadRole(user, Client.class);
        client.submitRepair(repair);
        repair.setClient(client);
        Repair r = repairRepository.save(repair);
        if (r == null)
            throw new RepairServiceException("Could not save repair");

        return r;
    }

    /**
     * Save a repair.
     * <p/>
     * This action will save all repair details
     *
     * @param repair instance of a repair to be saved
     */
    @Override
    public Repair updateRepair(Repair repair)
    {
        return repairRepository.save(repair);
    }

    /**
     * Removes a repair from the repository if no one is referencing the repair.
     * <p/>
     * Repairs can be referenced by clients or repairers. If no one references a
     * repair any longer it will be removed from the repository as well.
     *
     * @param repairId id of repair instance
     */
    @Override
    public void deleteRepair(Integer repairId) throws RepairServiceException
    {
        Repair repair = repairRepository.findOne(repairId);
        Client client = repair.getClient();
        Repairer repairer = repair.getRepairer();

        client.removeRepair(repair);

        // if repair was assigned to repairer also remove from repairers' list and all of the bids
        if (repairer != null)
            repairer.removeRepair(repair);

        repairRepository.delete(repair);
    }

    /**
     * Changes status of a repair to new status
     * <p/>
     * This method also checks whether the new status is legal status for this
     * repair.
     * <p/>
     * A repair can't have status Fixed when the repair was not previously
     * assigned to a repairer.
     *
     * @param repairId  Id of a repair
     * @param newStatus update for status of the repair
     * @throws RepairServiceException if illegal repair state is detected
     */
    @Override
    public void updateRepairStatus(Integer repairId, RepairDetails.Status newStatus)
    {
        try
        {
            Repair repair = repairRepository.findOne(repairId);
            if ((newStatus.equals(RepairDetails.Status.Fixed) || newStatus.equals(RepairDetails.Status.Irreparable)) && !repair.getDetails().isAssigned())
            {
                throw new IllegalStateException("Repair is not assigned");
            }

            repair.getDetails().setStatus(newStatus);
            repairRepository.save(repair);

        }
        catch (IllegalStateException e)
        {
            throw new RepairServiceException("Repair must be assigned to Repairer first", e);
        }
    }
}
