package be.kdg.repaircafe.backend.persistence.criteria;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SpecificationsBuilder<T>
{
    private List<Specification<T>> specifications = new ArrayList<>();

    public SpecificationsBuilder<T> with(List<SearchCriterium> searchCriteria, List<Class> classes)
    {
        try
        {
            for (Class clazz : classes)
            {
                for (SearchCriterium searchCriterium : searchCriteria)
                {
                    Constructor constructor = clazz.getConstructor(SearchCriterium.class);
                    Specification<T> specification = (Specification<T>) constructor.newInstance(searchCriterium);
                    this.specifications.add(specification);
                }
            }
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e)
        {
            return null;
        }
        return this;
    }

    /* http://www.baeldung.com/rest-api-search-language-spring-data-specifications */
    public Specification<T> build()
    {
        if (specifications.size() == 0)
        {
            return null;
        }

        List<Specification<T>> specs = new ArrayList<>();

        for (Specification<T> spec : specifications)
        {
            specs.add(spec);
        }

        Specification<T> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++)
        {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }
}
