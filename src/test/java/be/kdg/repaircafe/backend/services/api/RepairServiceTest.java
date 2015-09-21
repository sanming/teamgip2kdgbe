package be.kdg.repaircafe.backend.services.api;

import be.kdg.repaircafe.backend.config.BackendContextConfig;
import be.kdg.repaircafe.backend.dom.repairs.Item;
import be.kdg.repaircafe.backend.dom.repairs.Repair;
import be.kdg.repaircafe.backend.dom.repairs.RepairDetails;
import be.kdg.repaircafe.backend.persistence.criteria.SearchCriterium;
import be.kdg.repaircafe.backend.persistence.criteria.SearchOperation;
import be.kdg.repaircafe.backend.services.exceptions.RepairServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BackendContextConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class RepairServiceTest
{
    @Autowired
    private RepairService repairService;

    @Test
    public void testFindRepairByID() throws Exception
    {
        Repair repair = repairService.findRepairById(1);
        assertThat(repair, is(notNullValue()));
    }

    @Test
    public void testFindRepairsByUserId() throws Exception
    {
        List<Repair> repairs = repairService.findRepairsByUserId(1);
        assertThat(repairs, is(not(empty())));
    }

    @Test
    public void testFindRepairsByCriteria() throws Exception
    {
        String criterium1 = "Elektrisch";
        String criterium2 = "Siemens";
        List<SearchCriterium> searchCriteriums = new ArrayList<>();
        searchCriteriums.add(new SearchCriterium("defect", SearchOperation.EQUALITY, criterium1));
        searchCriteriums.add(new SearchCriterium("brand", SearchOperation.EQUALITY, criterium2));

        List<Repair> repairs = repairService.findRepairsByCriteria(searchCriteriums);
        repairs.forEach(r -> assertThat(r.getDetails().getDefect(), equalToIgnoringCase(criterium1)));
        repairs.forEach(r -> assertThat(r.getItem().getBrand(), equalToIgnoringCase(criterium2)));
    }

    @Test
    public void testAddRepair() throws Exception
    {
        Item item = new Item("Leica Disto", "Leico", "Lasermeter");
        RepairDetails repairDetails = new RepairDetails("Elektrisch", "Laserstraal werkt niet meer", RepairDetails.PriceModel.FIXED, LocalDateTime.now().plusWeeks(1));
        Repair repair = new Repair(item, repairDetails);
        Repair r = repairService.saveRepair(1, repair);
        Repair persistedRepair = repairService.findRepairById(r.getId());
        assertThat(persistedRepair.getId(), equalTo(r.getId()));
    }

    @Test
    public void testUpdateRepair() throws Exception
    {
        String evaluation = "Well done";
        Repair repair = repairService.findRepairById(1);
        RepairDetails rd = repair.getDetails();
        rd.setEvaluation(evaluation);
        Repair persistedRepair = repairService.updateRepair(repair);
        assertThat(persistedRepair.getDetails().getEvaluation(), equalTo(evaluation));
    }

    @Test(expected = RepairServiceException.class)
    public void testDeleteRepair() throws Exception
    {
        repairService.deleteRepair(1);
        repairService.findRepairById(1);
    }

    @Test
    public void testUpdateRepairStatus() throws Exception
    {

    }
}