package be.kdg.repaircafe.backend.persistence.api;

import be.kdg.repaircafe.backend.dom.repairs.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface to trigger proxy creation of CRUD Repository
 */
public interface BidRepository extends JpaRepository<Bid, Integer>
{
    // See http://projects.spring.io/spring-data/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/
}
