package be.kdg.repaircafe.frontend.controllers.rest;

import be.kdg.repaircafe.frontend.config.RootContextConfig;
import be.kdg.repaircafe.frontend.config.WebContextConfig;
import be.kdg.repaircafe.frontend.controllers.TestData;
import org.hamcrest.Matchers;
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

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootContextConfig.class, WebContextConfig.class})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class UserControllerTest
{
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestData testData;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllUsers() throws Exception
    {
        mockMvc.perform(get("/api/users")).
                andDo(print());
    }

    @Test
    public void testCreateUser() throws Exception
    {
        mockMvc.perform(post("/api/users").
                content(testData.getUserResource().toString()).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$.username", is("wouter.deketelaere@kdg.be"))).
                andDo(print());
    }

    @Test
    public void testFindUserById() throws Exception
    {
        mockMvc.perform(get("/api/users/1")).
                andExpect(jsonPath("$.username", is("clarence.ho@gmail.com"))).
                //andExpect(jsonPath("$.links[*].href",hasItem(endsWith("/api/users/1")))).
                        andDo(print());
    }

    @Test
    public void testUpdateUserById() throws Exception
    {
        JSONObject personResource = testData.getPersonResource();

        mockMvc.perform(put("/api/users/1")
                .content(personResource.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.personResource.firstname", is("Wouter")))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testDeleteUserById() throws Exception
    {
        mockMvc.perform(delete("/api/users/1").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(print());
    }

    @Test
    public void testUpdatePassword() throws Exception
    {
        MvcResult mvcResult = mockMvc.perform(post("/api/users").
                content(testData.getUserResource().toString()).
                contentType(MediaType.APPLICATION_JSON)).
                andReturn();

        JSONObject createdUser = new JSONObject(mvcResult.getResponse().getContentAsString());

        createdUser.put("oldPassword", "flappie");
        createdUser.put("password", "$eCret");
        String userId = String.valueOf(createdUser.getInt("userId"));

        mockMvc.perform(put("/api/users/".concat(userId).concat("/password")).
                content(createdUser.toString()).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated());
    }

    @Test
    public void testIncorrectUpdatePassword() throws Exception
    {
        MvcResult mvcResult = mockMvc.perform(post("/api/users").
                content(testData.getUserResource().toString()).
                contentType(MediaType.APPLICATION_JSON)).
                andReturn();

        JSONObject createdUser = new JSONObject(mvcResult.getResponse().getContentAsString());

        createdUser.put("oldPassword", "flappie");
        createdUser.put("password", "flappie");
        String userId = String.valueOf(createdUser.getInt("userId"));

        mockMvc.perform(put("/api/users/".concat(userId).concat("/password")).
                content(createdUser.toString()).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest()).
                andDo(print());
    }

    @Test
    public void testGetRepairsByUser() throws Exception
    {
        String userId = "1";
        mockMvc.perform(get("/api/users/" + userId + "/repairs")).
                andExpect(jsonPath("$").isArray()).
                andDo(print());
    }

    @Test
    @WithUserDetails("clarence.ho@gmail.com")
    public void testCreateRepair() throws Exception
    {
        String userId = "1";
        mockMvc.perform(post("/api/users/" + userId + "/repairs").
                content(testData.getRepairResource().toString()).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$.repairId", Matchers.is(not(empty())))).
                andDo(print());
    }
}
