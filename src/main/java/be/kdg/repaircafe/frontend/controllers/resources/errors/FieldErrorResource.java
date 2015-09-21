package be.kdg.repaircafe.frontend.controllers.resources.errors;

public class FieldErrorResource
{
    private final String field;

    private final String message;

    public FieldErrorResource(String field, String message)
    {
        this.field = field;
        this.message = message;
    }

    public String getField()
    {
        return field;
    }

    public String getMessage()
    {
        return message;
    }
}

