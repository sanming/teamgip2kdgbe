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
 * Represents a bid in accordance with a per hour price model.
 *
 * @author wouter
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("PerhourBid")
public class PerhourBid extends Bid implements Serializable
{

    public PerhourBid()
    {
    }

    /**
     * Bid with per hour  price.    
     * If the customer creates a per hour priced repair
     * only this type of offer can be made.
     *
     * @param price
     */
    public PerhourBid(double price)
    {
        super(price);
    }

    @Override
    public String getType()
    {
        return "Per Hour";
    }

}
