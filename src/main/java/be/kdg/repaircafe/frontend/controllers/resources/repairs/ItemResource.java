package be.kdg.repaircafe.frontend.controllers.resources.repairs;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemResource
{
    @NotEmpty
    private String productName;
    @NotEmpty
    private String brand;
    @NotEmpty
    private String category;

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }
}
