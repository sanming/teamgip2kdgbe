/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kdg.repaircafe.backend.dom.repairs;

import be.kdg.repaircafe.backend.dom.users.roles.Client;
import be.kdg.repaircafe.backend.dom.users.roles.Repairer;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A repairer can bid on a repair. Depending on the choice of the customer the
 * bid will take the form of fixed bid or an per hour bid. Only one bid can be
 * submitted on a particular repair.
 * <p/>
 * Once a bid is accepted by the Client no more bid can be submitted.
 *
 * @author wouter
 * @see Client
 * @see Repairer
 * @see be.kdg.repaircafe.backend.dom.repairs.Repair
 */
@Entity
@Table(name = "Bid")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Bid")
@NamedQueries(
        {
                @NamedQuery(name = "Bid.findAll", query = "SELECT b FROM Bid b"),
                @NamedQuery(name = "Bid.findByBidid", query = "SELECT b FROM Bid b WHERE b.bidId = :bidid"),
                @NamedQuery(name = "Bid.findByTimestamp", query = "SELECT b FROM Bid b WHERE b.timestamp = :timestamp"),
                @NamedQuery(name = "Bid.findByPrice", query = "SELECT b FROM Bid b WHERE b.price = :price"),
                @NamedQuery(name = "Bid.findByComment", query = "SELECT b FROM Bid b WHERE b.comment = :comment"),
                @NamedQuery(name = "Bid.findByAccepted", query = "SELECT b FROM Bid b WHERE b.accepted = :accepted"),
                @NamedQuery(name = "Bid.findByEligible", query = "SELECT b FROM Bid b WHERE b.eligible = :eligible")
        })
public abstract class Bid implements Comparable<Bid>, Serializable, Identifiable<Integer>
{
    @Column(name = "BidId", nullable = false)
    @Id
    @GeneratedValue
    private Integer bidId;

    @ManyToOne(targetEntity = Repair.class)
    @JoinColumn(name = "MyRepairId")
    private Repair repair;

    @ManyToOne(targetEntity = Repairer.class)
    @JoinColumn(name = "MyRepairerId")
    private Repairer repairer;

    @Column(name = "Price", nullable = false, length = 10)
    private double price;

    @Column(name = "Comment", nullable = true, length = 255)
    private String comment;

    @Column(name = "Accepted", nullable = false)
    private boolean accepted = false;

    @Column(name = "Eligible", nullable = false)
    private boolean eligible = true;

    @Column(name = "Timestamp")
    //@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    //@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private LocalDateTime timestamp;

    public Bid()
    {
    }

    public Bid(final double price)
    {
        this.price = price;
        timestamp = LocalDateTime.now();
    }

    public Integer getId()
    {
        return bidId;
    }

    /**
     * Return bid price.
     * <p/>
     * This is either a fixed price or per hour price.
     *
     * @return bid price
     */
    public double getPrice()
    {
        return price;
    }

    public void setPrice(final double price)
    {
        this.price = price;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(final String comment)
    {
        this.comment = comment;
    }

    public Repairer getRepairer()
    {
        return repairer;
    }

    /**
     * Set repairer that placed this bid.
     *
     * @param repairer
     */
    public void setRepairer(final Repairer repairer)
    {
        this.repairer = repairer;
    }

    public boolean isAccepted()
    {
        return accepted;
    }

    /**
     * Mark bid as accepted
     *
     * @param value
     */
    public void setAccepted(final boolean value)
    {
        this.accepted = value;
    }

    /**
     * Is this bid still in the running
     *
     * @return bid eligibility
     */
    public boolean isEligible()
    {
        return eligible;
    }

    /**
     * Set eligibility of bid
     *
     * @param eligible
     */
    public void setEligible(final boolean eligible)
    {
        this.eligible = eligible;
    }

    public Repair getRepair()
    {
        return repair;
    }

    /**
     * Associate with repair
     *
     * @param repair
     */
    public void setRepair(final Repair repair)
    {
        this.repair = repair;
    }

    public Integer getBidId()
    {
        return bidId;
    }

    public void setBidId(final Integer bidId)
    {
        this.bidId = bidId;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(final Bid o)
    {
        return (int) ((int) price - o.price);
    }

    /**
     * Returns bid type: Fixed or Per Hour
     *
     * @return bid type
     */
    public String getType()
    {
        return "Bid";
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 23 * hash + this.bidId;
        return hash;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Bid other = (Bid) obj;
        return this.bidId.equals(other.bidId);
    }

    @Override
    public String toString()
    {
        return "Bid{" + timestamp.toString() + ", accepted=" + accepted + ", by user=" + repairer + ", for price=" + price + '}';
    }
}
