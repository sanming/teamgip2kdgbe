package be.kdg.repaircafe.backend.persistence.criteria.repairs;

import be.kdg.repaircafe.backend.dom.repairs.Repair;
import be.kdg.repaircafe.backend.dom.repairs.RepairDetails;
import be.kdg.repaircafe.backend.dom.repairs.Repair_;
import be.kdg.repaircafe.backend.persistence.criteria.CriteriaSpecification;
import be.kdg.repaircafe.backend.persistence.criteria.SearchCriterium;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class DetailsSpecification extends CriteriaSpecification implements Specification<Repair>
{
    /*http://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html*/
    public DetailsSpecification(SearchCriterium criterium)
    {
        super(criterium);
    }

    @Override
    public Predicate toPredicate(Root<Repair> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
    {
        Join<Repair, RepairDetails> detailsJoin = root.join(Repair_.details);
        return super.toPredicate(detailsJoin, criteriaQuery, criteriaBuilder);
    }
}
