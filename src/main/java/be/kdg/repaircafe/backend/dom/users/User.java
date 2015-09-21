package be.kdg.repaircafe.backend.dom.users;

import be.kdg.repaircafe.backend.dom.users.roles.Role;
import be.kdg.repaircafe.backend.services.exceptions.UserServiceException;
import org.hibernate.annotations.Fetch;
import org.springframework.hateoas.Identifiable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Abstract class representing a user of the system. Every user is associated
 * with a Person. Every user has to have a unique username. This username takes
 * the form of an email address.
 *
 * @author wouter
 */
@Entity
@Table(name = "`User`")
@NamedQueries(
        {
                @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
                @NamedQuery(name = "User.findByUserid", query = "SELECT u FROM User u WHERE u.userId = :userid"),
                @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
                @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.encryptedPassword = :password")
        })
public class User implements Serializable, UserDetails, Identifiable<Integer>
{
    @Id
    @GeneratedValue
    @Column(name = "UserId", nullable = false)
    private Integer userId;

    @Column(name = "Username", nullable = true, length = 255)
    private String username = null;

    @Column(name = "Password", nullable = true, length = 255)
    private String encryptedPassword;

    @OneToOne(targetEntity = Person.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PersonId", nullable = false)
    private Person person;

    // https://en.wikibooks.org/wiki/Java_Persistence/OneToMany
    // simplest solution is to use bidirectional relationship
    @OneToMany(targetEntity = Role.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    // for some strange reason we need to add this, see: http://stackoverflow.com/questions/1995080/hibernate-criteria-returns-children-multiple-times-with-fetchtype-eager
    @Fetch(org.hibernate.annotations.FetchMode.SELECT)
    private List<Role> roles;

    public User()
    {
        this.person = new Person();
    }

    public User(Person person, String username, String encryptedPassword, List<Role> roles)
    {
        this.person = person;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.roles = roles;
    }

    public Integer getId()
    {
        return userId;
    }

    public void setId(Integer id)
    {
        this.userId = id;
    }

    /**
     * Return Person associated with this user
     *
     * @return Person object
     * @see(Person)
     */
    public Person getPerson()
    {
        return person;
    }

    public synchronized void setPerson(Person person)
    {
        this.person = person;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    @Override
    public int hashCode()
    {
        int result = person != null ? person.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (encryptedPassword != null ? encryptedPassword.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (person != null ? !person.equals(user.person) : user.person != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (encryptedPassword != null ? !encryptedPassword.equals(user.encryptedPassword) : user.encryptedPassword != null)
            return false;
        return roles == user.roles;

    }

    @Override
    public String toString()
    {
        return "User{" + userId + ", " + username + ", " + encryptedPassword + '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.addAll(role.getAuthorities()));
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return getEncryptedPassword();
    }

    /**
     * Get username for this user
     *
     * @return
     */
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    public String getEncryptedPassword()
    {
        return encryptedPassword;
    }

    /**
     * Change password for user
     *
     * @param encryptedPassword
     * @throws UserServiceException
     */
    public synchronized void setEncryptedPassword(String encryptedPassword)
    {
        this.encryptedPassword = encryptedPassword;
    }
}
