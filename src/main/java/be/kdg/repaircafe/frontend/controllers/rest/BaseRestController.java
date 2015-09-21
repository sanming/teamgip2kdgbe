package be.kdg.repaircafe.frontend.controllers.rest;

import be.kdg.repaircafe.backend.persistence.criteria.SearchCriterium;
import be.kdg.repaircafe.backend.persistence.criteria.SearchOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class for all Restcontrollers.
 * Implements parsing of String search query to criteria
 */
public class BaseRestController
{
    protected List<SearchCriterium> parseCriteria(String search)
    {
        List<SearchCriterium> searchCriteria = new ArrayList<>();
        if (search != null)
        {
            final String operationSetExper = String.join("|", SearchOperation.SIMPLE_OPERATION_SET);
            final Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
            final Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find())
            {
                searchCriteria.add(createSearchCriterium(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5)));
            }
        }
        return searchCriteria;
    }

    private SearchCriterium createSearchCriterium(final String key, final String operation, final Object value, final String prefix, final String suffix)
    {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null)
        {
            if (op == SearchOperation.EQUALITY) // the operation may be complex operation
            {
                final boolean startWithAsterisk = prefix.contains("*");
                final boolean endWithAsterisk = suffix.contains("*");

                if (startWithAsterisk && endWithAsterisk)
                {
                    op = SearchOperation.CONTAINS;
                }
                else if (startWithAsterisk)
                {
                    op = SearchOperation.ENDS_WITH;
                }
                else if (endWithAsterisk)
                {
                    op = SearchOperation.STARTS_WITH;
                }
            }
        }
        return new SearchCriterium(key, op, value);
    }
}
