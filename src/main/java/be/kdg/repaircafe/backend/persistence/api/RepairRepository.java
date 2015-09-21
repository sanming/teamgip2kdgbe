/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kdg.repaircafe.backend.persistence.api;

import be.kdg.repaircafe.backend.dom.repairs.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wouter
 */
public interface RepairRepository
        extends JpaRepository<Repair, Integer>, JpaSpecificationExecutor<Repair>, RepairRepositoryCustom
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/
    @Query(value = "SELECT distinct r.item.category FROM Repair r")
    List<String> getAllCategories();

    @Query(value = "select distinct r.details.defect from Repair r")
    List<String> getAllDefects();

    @Query(value = "select distinct r.item.brand from Repair r")
    List<String> getAllBrands();
}
