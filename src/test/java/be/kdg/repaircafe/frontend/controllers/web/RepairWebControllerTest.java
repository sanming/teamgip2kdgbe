package be.kdg.repaircafe.frontend.controllers.web;

import be.kdg.repaircafe.frontend.config.RootContextConfig;
import be.kdg.repaircafe.frontend.config.WebContextConfig;
import be.kdg.repaircafe.frontend.controllers.TestData;
import be.kdg.repaircafe.frontend.controllers.resources.repairs.RepairResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootContextConfig.class, WebContextConfig.class})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class RepairWebControllerTest
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
    public void testShowRepairsForUser() throws Exception
    {

    }

    @Test
    public void testShowNewRepair() throws Exception
    {

    }

    @Test
    @WithUserDetails("clarence.ho@gmail.com")
    public void testSaveRepair() throws Exception
    {
        RepairResource repairResource = new RepairResource();
        repairResource.getItemResource().setProductName("Leico");
        repairResource.getRepairDetailsResource().setDescription("Werkt niet meer");

        String productName = repairResource.getItemResource().getProductName();
        String description = repairResource.getRepairDetailsResource().getDescription();

        mockMvc.perform(post("/saverepair.do")
                .param("itemResource.productName", productName)
                .param("repairDetailsResource.description", description))
                .andExpect(status().isOk())
                .andExpect(view().name("repairdetail"))
                .andExpect(forwardedUrl("/WEB-INF/pages/repairdetail.jsp"))
                .andExpect(model().attribute("repairResource", hasProperty("repairId", is(not(empty())))))
                .andExpect(model().attribute("repairResource", hasProperty("itemResource", hasProperty("productName", is(productName)))))
                .andExpect(model().attribute("repairResource", hasProperty("repairDetailsResource", hasProperty("description", is(description))))
                );
    }
}