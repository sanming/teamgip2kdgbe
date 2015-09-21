package be.kdg.repaircafe.backend.persistence.criteria.users;

import be.kdg.repaircafe.backend.dom.users.*;
import be.kdg.repaircafe.backend.persistence.criteria.CriteriaSpecification;
import be.kdg.repaircafe.backend.persistence.criteria.SearchCriterium;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class PersonAddressSpecification extends CriteriaSpecification implements Specification<User>
{
    /*http://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html*/
    public PersonAddressSpecification(SearchCriterium criterium)
    {
        super(criterium);
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
    {
        Join<Person, Address> personJoin = root.join(User_.person).join(Person_.address);
        return super.toPredicate(personJoin, criteriaQuery, criteriaBuilder);
    }
}
