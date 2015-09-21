package be.kdg.repaircafe.backend.persistence.criteria.users;

import be.kdg.repaircafe.backend.dom.users.User;
import be.kdg.repaircafe.backend.persistence.criteria.CriteriaSpecification;
import be.kdg.repaircafe.backend.persistence.criteria.SearchCriterium;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification extends CriteriaSpecification implements Specification<User>
{
    /*http://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html*/
    public UserSpecification(SearchCriterium criterium)
    {
        super(criterium);
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
    {
        return super.toPredicate(root, criteriaQuery, criteriaBuilder);
    }
}
