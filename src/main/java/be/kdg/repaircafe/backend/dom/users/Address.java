package be.kdg.repaircafe.backend.dom.users;

import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Holds address for all types of users.
 * Can be used to better match Client and Repairer.
 *
 * @author wouter
 */
@Entity

@Table(name = "Address")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries(
        {
                @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
                @NamedQuery(name = "Address.findByAddressid", query = "SELECT a FROM Address a WHERE a.addressid = :addressid"),
                @NamedQuery(name = "Address.findByStreet", query = "SELECT a FROM Address a WHERE a.street = :street"),
                @NamedQuery(name = "Address.findByNr", query = "SELECT a FROM Address a WHERE a.nr = :nr"),
                @NamedQuery(name = "Address.findByZip", query = "SELECT a FROM Address a WHERE a.zip = :zip"),
                @NamedQuery(name = "Address.findByCity", query = "SELECT a FROM Address a WHERE a.city = :city")
        })
public class Address implements Serializable, Identifiable<Integer>
{
    @Column(name = "AddressId", nullable = false)
    @Id
    @GeneratedValue
    private Integer addressid;

    @Column(name = "Street", nullable = true, length = 255)
    private String street;

    @Column(name = "Nr", nullable = true, length = 255)
    private String nr;

    @Column(name = "Zip", nullable = true, length = 255)
    private String zip;

    @Column(name = "City", nullable = true, length = 255)
    private String city;

    public Address()
    {
    }

    public Address(String street, String nr, String zip, String city)
    {
        this.street = street;
        this.nr = nr;
        this.zip = zip;
        this.city = city;
    }

    public Integer getId()
    {
        return addressid;
    }

    public String getStreet()
    {
        return this.street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getNr()
    {
        return this.nr;
    }

    public void setNr(String nr)
    {
        this.nr = nr;
    }

    public String getZip()
    {
        return this.zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getCity()
    {
        return this.city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Integer getAddressid()
    {
        return addressid;
    }

    public void setAddressid(Integer addressid)
    {
        this.addressid = addressid;
    }

    @Override
    public int hashCode()
    {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (nr != null ? nr.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (nr != null ? !nr.equals(address.nr) : address.nr != null) return false;
        if (zip != null ? !zip.equals(address.zip) : address.zip != null) return false;
        return !(city != null ? !city.equals(address.city) : address.city != null);

    }
}