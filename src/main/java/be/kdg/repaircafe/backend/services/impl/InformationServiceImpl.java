package be.kdg.repaircafe.backend.services.impl;

import be.kdg.repaircafe.backend.persistence.api.RepairRepository;
import be.kdg.repaircafe.backend.services.api.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("informationService")
@Transactional
public class InformationServiceImpl implements InformationService
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-service-layer/

    private final RepairRepository repairRepository;

    @Autowired
    public InformationServiceImpl(RepairRepository repairRepository)
    {
        this.repairRepository = repairRepository;
    }

    /**
     * Return all known item categories
     *
     * @return List of categories
     */
    @Override
    public List<String> getAllCategories()
    {
        return repairRepository.getAllCategories();
    }

    /**
     * Return all known item brands
     *
     * @return List of brands
     */
    @Override
    public List<String> getAllBrands()
    {
        return repairRepository.getAllBrands();
    }

    /**
     * Return all known defects
     *
     * @return List of all known defects to the system
     */
    @Override
    public List<String> getAllDefects()
    {
        return repairRepository.getAllDefects();
    }

}
