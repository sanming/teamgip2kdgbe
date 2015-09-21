/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kdg.repaircafe.backend.dom.repairs;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

/**
 * @author wouter
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("FixedBid")
public class FixedBid extends Bid implements Serializable
{
    public FixedBid()
    {
    }

    /**
     * Bid with fixed total price.     If the customer creates a fixed price
     * repair only this type of offer can be made.
     *
     * @param price
     */
    public FixedBid(double price)
    {
        super(price);
    }

    @Override
    public String getType()
    {
        return "Fixed Bid";
    }

}
