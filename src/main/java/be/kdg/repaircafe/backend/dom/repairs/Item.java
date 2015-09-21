/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kdg.repaircafe.backend.dom.repairs;

import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Item that needs repairing.
 *
 * @author wouter
 */
@Entity
@Table(name = "Item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries(
        {
                @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
                @NamedQuery(name = "Item.findByItemid", query = "SELECT i FROM Item i WHERE i.itemId = :itemid"),
                @NamedQuery(name = "Item.findByProductname", query = "SELECT i FROM Item i WHERE i.productName = :productname"),
                @NamedQuery(name = "Item.findByBrand", query = "SELECT i FROM Item i WHERE i.brand = :brand"),
                @NamedQuery(name = "Item.findByCategory", query = "SELECT i FROM Item i WHERE i.category = :category")
        })
public class Item implements Serializable, Identifiable<Integer>
{
    @Column(name = "ItemId", nullable = false)
    @Id
    @GeneratedValue
    private Integer itemId;

    @Column(name = "ProductName", nullable = true, length = 255)
    private String productName;

    @Column(name = "Brand", nullable = true, length = 255)
    private String brand;

    @Column(name = "Category", nullable = true, length = 255)
    private String category;

    public Item()
    {
        this("", "", "");
    }

    /**
     * A thing that needs fixing. Characterized by it's
     * product name, brand and category
     *
     * @param productName
     * @param brand
     * @param category
     */
    public Item(String productName, String brand, String category)
    {
        this.productName = productName;
        this.brand = brand;
        this.category = category;
    }

    /**
     * Get product name of item
     *
     * @return product name
     */
    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * Get brand of item
     *
     * @return item brand name
     */
    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    /**
     * Get category of item
     *
     * @return item category
     */
    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public Integer getId()
    {
        return itemId;
    }
}
