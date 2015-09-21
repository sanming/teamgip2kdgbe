package be.kdg.repaircafe.frontend.controllers.rest;

import be.kdg.repaircafe.frontend.config.RootContextConfig;
import be.kdg.repaircafe.frontend.config.WebContextConfig;
import org.json.JSONObject;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootContextConfig.class, WebContextConfig.class})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class BidControllerTest
{
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithUserDetails("clarence.ho@gmail.com")
    public void testGetBidById() throws Exception
    {
        mockMvc.perform(get("/api/bids/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails("clarence.ho@gmail.com")
    public void testUpdateBidById() throws Exception
    {
        MvcResult mvcResult = mockMvc.perform(get("/api/bids/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONObject bidResource = new JSONObject(mvcResult.getResponse().getContentAsString());
        bidResource.put("accepted", true);

        mockMvc.perform(put("/api/bids/1")
                .content(bidResource.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accepted", is(true)))
                .andDo(print());
    }

    @Test
    @WithUserDetails("clarence.ho@gmail.com")
    public void testDeleteBidById() throws Exception
    {
        mockMvc.perform(delete("/api/bids/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAcceptBidById() throws Exception
    {
        // TODO: write tests
    }

    @Test
    public void testClearBidById() throws Exception
    {
        // TODO: write tests
    }
}