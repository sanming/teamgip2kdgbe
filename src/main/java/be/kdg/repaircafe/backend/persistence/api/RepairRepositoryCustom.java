package be.kdg.repaircafe.backend.persistence.api;

import be.kdg.repaircafe.backend.dom.repairs.Repair;
import be.kdg.repaircafe.backend.dom.users.roles.Client;

import java.util.List;

/**
 * Extra interface to support customization of Spring's Data interfaces
 */
public interface RepairRepositoryCustom
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/

    List<Repair> getRepairsByClient(Client client);
}
