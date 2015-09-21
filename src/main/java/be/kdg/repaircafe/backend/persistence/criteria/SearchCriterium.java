package be.kdg.repaircafe.backend.persistence.criteria;

public class SearchCriterium
{
    /* http://www.baeldung.com/rest-api-search-language-spring-data-specifications */
    private String key;
    private SearchOperation operation;
    private Object value;

    public SearchCriterium()
    {
    }

    public SearchCriterium(final String key, final SearchOperation operation, final Object value)
    {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(final String key)
    {
        this.key = key;
    }

    public SearchOperation getOperation()
    {
        return operation;
    }

    public void setOperation(SearchOperation operation)
    {
        this.operation = operation;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(final Object value)
    {
        this.value = value;
    }
}
