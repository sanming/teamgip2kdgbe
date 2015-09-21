/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kdg.repaircafe.backend.persistence.impl;

import be.kdg.repaircafe.backend.dom.repairs.Repair;
import be.kdg.repaircafe.backend.dom.users.roles.Client;
import be.kdg.repaircafe.backend.persistence.api.RepairRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author wouter
 */
@Repository("repairRepository")
public class RepairRepositoryImpl implements RepairRepositoryCustom
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Repair> getRepairsByClient(Client client)
    {
        final TypedQuery<Repair> q = em.createNamedQuery("Repair.findRepairByClient", Repair.class);
        q.setParameter("client", client);
        return q.getResultList();
    }
}
