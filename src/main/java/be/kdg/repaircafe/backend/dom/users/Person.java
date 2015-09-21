package be.kdg.repaircafe.backend.dom.users;

import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries(
        {
                @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
                @NamedQuery(name = "Person.findByPersonid", query = "SELECT p FROM Person p WHERE p.personid = :personid"),
                @NamedQuery(name = "Person.findByFirstname", query = "SELECT p FROM Person p WHERE p.firstname = :firstname"),
                @NamedQuery(name = "Person.findByLastname", query = "SELECT p FROM Person p WHERE p.lastname = :lastname")
        })
public class Person implements Serializable, Identifiable<Integer>
{
    @Column(name = "PersonId", nullable = false)
    @Id
    @GeneratedValue
    private Integer personid;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumns({@JoinColumn(name = "AddressId")})
    private Address address;

    @Column(name = "Firstname", nullable = true, length = 255)
    private String firstname;

    @Column(name = "Lastname", nullable = true, length = 255)
    private String lastname;


    public Person()
    {
        this.address = new Address();
    }

    public Person(String firstname, String lastname, Address address)
    {
        this.address = address;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getId()
    {
        return personid;
    }

    /**
     * Return person's first name
     *
     * @return firstname
     */
    public String getFirstname()
    {
        return this.firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    /**
     * Return person's last name
     *
     * @return lastname
     */
    public String getLastname()
    {
        return this.lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    /**
     * Return person's address
     *
     * @return address
     */
    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Integer getPersonid()
    {
        return personid;
    }

    public void setPersonid(Integer personid)
    {
        this.personid = personid;
    }

    @Override
    public int hashCode()
    {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (address != null ? !address.equals(person.address) : person.address != null) return false;
        if (firstname != null ? !firstname.equals(person.firstname) : person.firstname != null) return false;
        return !(lastname != null ? !lastname.equals(person.lastname) : person.lastname != null);

    }
}