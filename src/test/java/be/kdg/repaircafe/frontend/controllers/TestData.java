package be.kdg.repaircafe.frontend.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class TestData
{
    private JSONObject
            userResource = new JSONObject(),
            personResource = new JSONObject(),
            addressResource = new JSONObject(),
            repairResource = new JSONObject(),
            itemResource = new JSONObject(),
            repairDetailsResource = new JSONObject(),
            bidResource = new JSONObject();

    @PostConstruct
    public void init()
    {
        userResource.put("username", "wouter.deketelaere@kdg.be");
        userResource.put("password", "flappie");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(0, "ROLE_CLIENT");
        jsonArray.put(1, "ROLE_REPAIRER");
        userResource.put("roleTypes", jsonArray);

        personResource.put("firstname", "Wouter");
        personResource.put("lastname", "Deketelaere");

        addressResource.put("street", "Lentelaan");
        addressResource.put("nr", "4");
        addressResource.put("zip", "2800");
        addressResource.put("city", "Mechelen");

        personResource.put("addressResource", addressResource);
        userResource.put("personResource", personResource);


        itemResource.put("productName", "Disto D3");
        itemResource.put("brand", "Leica");
        itemResource.put("category", "Lasermeters");
        repairDetailsResource.put("defect", "Elektrisch");
        repairDetailsResource.put("description", "Geen laserstraal meer");
        repairDetailsResource.put("priceModel", "FIXED");
        repairDetailsResource.put("dueDate", LocalDateTime.now().plusWeeks(2).toString());

        repairResource.put("itemResource", itemResource);
        repairResource.put("repairDetailsResource", repairDetailsResource);

        bidResource.put("price", 200);
    }

    public JSONObject getUserResource()
    {
        return userResource;
    }

    public JSONObject getPersonResource()
    {
        return personResource;
    }

    public JSONObject getAddressResource()
    {
        return addressResource;
    }

    public JSONObject getRepairResource()
    {
        return repairResource;
    }

    public JSONObject getItemResource()
    {
        return itemResource;
    }

    public JSONObject getRepairDetailsResource()
    {
        return repairDetailsResource;
    }

    public JSONObject getBidResource()
    {
        return bidResource;
    }
}
