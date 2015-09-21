package be.kdg.repaircafe.backend.services.api;

import java.util.List;

public interface InformationService
{
    // General find operations
    List<String> getAllCategories();

    List<String> getAllBrands();

    List<String> getAllDefects();

}
