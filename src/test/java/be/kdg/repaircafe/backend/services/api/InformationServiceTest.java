package be.kdg.repaircafe.backend.services.api;

import be.kdg.repaircafe.backend.config.BackendContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BackendContextConfig.class)
public class InformationServiceTest
{
    @Autowired
    private InformationService informationService;

    @Test
    public void testGetAllCategories() throws Exception
    {
        assertThat(informationService.getAllCategories(), is(not(empty())));
    }

    @Test
    public void testGetAllBrands() throws Exception
    {
        assertThat(informationService.getAllBrands(), is(not(empty())));
    }

    @Test
    public void testGetAllDefects() throws Exception
    {
        assertThat(informationService.getAllDefects(), is(not(empty())));
    }
}