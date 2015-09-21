package be.kdg.repaircafe.frontend.controllers.rest;

import be.kdg.repaircafe.frontend.config.RootContextConfig;
import be.kdg.repaircafe.frontend.config.WebContextConfig;
import be.kdg.repaircafe.frontend.controllers.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootContextConfig.class, WebContextConfig.class})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class RepairControllerTest
{
    @Autowired
    WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private TestData testData;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetRepairs() throws Exception
    {
        mockMvc.perform(get("/api/repairs"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetRepairsByCriteria() throws Exception
    {
        /* http://www.baeldung.com/rest-api-search-language-spring-data-specifications */
        String criteria = "defect:Elektrisch,brand:Siemens";
        mockMvc.perform(
                get("/api/repairs")
                        .param("search", criteria))
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    public void testGetRepairByRepairId() throws Exception
    {
        String repairId = "1";
        mockMvc.perform(get("/api/repairs/" + repairId))
                .andExpect(jsonPath("$.repairId", is(not(empty()))));

    }

    @Test
    @WithUserDetails("clarence.ho@gmail.com")
    public void testUpdateRepairByRepairId() throws Exception
    {
        // security credentials are used to fetch userid.
        String repairId = "1";

        MvcResult result = mockMvc.perform(get("/api/repairs/" + repairId)).
                andExpect(jsonPath("$.repairId", is(not(empty())))).
                andReturn();

        String content = result.getResponse().getContentAsString();
        content = content.replace("Vaatwasser", "Drogers");

        mockMvc.perform(put("/api/repairs/" + repairId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.itemResource.category", equalToIgnoringCase("Drogers")))
                .andDo(print());
    }


    @Test
    @WithUserDetails("clarence.ho@gmail.com")
    public void testDeleteRepairByRepairId() throws Exception
    {
        String repairId = "1";  // Clarence Ho owns repair no. 1

        mockMvc.perform(delete("/api/repairs/" + repairId))
                .andExpect(status().isOk());

    }

    /**
     * This tests that a client can view his/her bids for a particular repair
     * Other clients shouldn't be able to have access to these bids
     *
     * @throws Exception
     */
    @Test
    @WithUserDetails("clarence.ho@gmail.com")
    public void testGetBids() throws Exception
    {
        String repairId = "1";  // user clarence owns this repair

        mockMvc.perform(get("/api/repairs/" + repairId + "/bids"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails("scott.tiger@live.com")
    public void testGetBidsWithWrongUser() throws Exception
    {
        String repairId = "1";  // user scott doesn't own this repair

        mockMvc.perform(get("/api/repairs/" + repairId + "/bids"))
                .andExpect(status().isBadRequest());
    }

    /**
     * This tests that a repairer can view bids for a particular repair
     *
     * @throws Exception
     */
    @Test
    @WithUserDetails("peter.jackson@hotmail.com")
    public void testGetBidsWithRepairer() throws Exception
    {
        String repairId = "1";  // user peter is a repairer

        mockMvc.perform(get("/api/repairs/" + repairId + "/bids"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBidsWithNoUser() throws Exception
    {
        String repairId = "1";  // user clarence owns this repair

        mockMvc.perform(get("/api/repairs/" + repairId + "/bids"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    @WithUserDetails("peter.jackson@hotmail.com")
    public void testPlaceBidOnRepair() throws Exception
    {
        // security credentials are used to fetch userid.

        mockMvc.perform(post("/api/repairs/1/bids")
                .content(testData.getBidResource().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bidId", is(not(empty()))))
                .andDo(print());
    }

}