package be.kdg.repaircafe.frontend.controllers.resources.users;

import org.hibernate.validator.constraints.NotEmpty;

public class AddressResource
{
    @NotEmpty
    private String street;
    @NotEmpty
    private String nr;
    @NotEmpty
    private String zip;
    @NotEmpty
    private String city;

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getNr()
    {
        return nr;
    }

    public void setNr(String nr)
    {
        this.nr = nr;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }
}
