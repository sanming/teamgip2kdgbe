package be.kdg.repaircafe.backend.persistence.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 * Created by wouter on 9/8/15.
 */
public class CriteriaSpecification
{
    protected SearchCriterium criterium;

    public CriteriaSpecification(SearchCriterium criterium)
    {
        this.criterium = criterium;
    }

    public <T> Predicate toPredicate(Path<T> path, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
    {
        try
        {
            switch (criterium.getOperation())
            {
                case GREATER_THAN:
                    return criteriaBuilder.greaterThanOrEqualTo(path.get(criterium.getKey()), criterium.getValue().toString());
                case LESS_THAN:
                    return criteriaBuilder.lessThanOrEqualTo(path.get(criterium.getKey()), criterium.getValue().toString());
                case EQUALITY:
                    return criteriaBuilder.equal(path.get(criterium.getKey()), criterium.getValue());
                case LIKE:
                    if (path.get(criterium.getKey()).getJavaType() == String.class)
                        return criteriaBuilder.like(path.get(criterium.getKey()), "%" + criterium.getValue() + "%");
                default:
                    return null;
            }

        }
        catch (IllegalArgumentException e)
        {
            // indicates that the criterium was not found in this join.
        }
        return null;
    }
}
