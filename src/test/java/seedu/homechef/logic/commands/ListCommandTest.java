package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.homechef.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Order;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getHomeChef(), new MenuBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withDate_filtersList() {
        Date target = new Date("26-03-2026");

        expectedModel.updateFilteredOrderList(order -> order.getDate().equals(target));
        assertCommandSuccess(new ListCommand(target), model, ListCommand.MESSAGE_SUCCESS, expectedModel);

        List<Order> shown = model.getFilteredOrderList();
        assertEquals(true, shown.stream().allMatch(o -> o.getDate().equals(target)));
    }

    @Test
    public void execute_withCustomerFilter_filtersList() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setCustomerQuery("Alice");

        expectedModel.updateFilteredOrderList(order ->
                order.getCustomer().fullName.toLowerCase().contains("alice"));

        assertCommandSuccess(new ListCommand(d), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withFoodFilter_filtersList() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setFoodQuery("Cake");

        expectedModel.updateFilteredOrderList(order ->
                order.getFood().foodName.toLowerCase().contains("cake"));

        assertCommandSuccess(new ListCommand(d), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withMultipleFilters_filtersList() {
        Date target = new Date("26-03-2026");

        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setDate(target);
        d.setFoodQuery("Cake");

        expectedModel.updateFilteredOrderList(order ->
                order.getDate().equals(target)
                        && order.getFood().foodName.toLowerCase().contains("cake"));

        assertCommandSuccess(new ListCommand(d), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withPhoneFilter_filtersList() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setPhoneQuery("9435");

        expectedModel.updateFilteredOrderList(order ->
                order.getPhone().toString().toLowerCase().contains("9435"));

        assertCommandSuccess(new ListCommand(d), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }


    @Test
    public void equals() {
        Date d1 = new Date("16-04-2003");

        ListCommand.ListFilterDescriptor desc1 = new ListCommand.ListFilterDescriptor();
        desc1.setDate(d1);
        desc1.setCustomerQuery("alice");

        ListCommand.ListFilterDescriptor desc1Copy = new ListCommand.ListFilterDescriptor();
        desc1Copy.setDate(new Date("16-04-2003"));
        desc1Copy.setCustomerQuery("alice");

        ListCommand.ListFilterDescriptor desc2 = new ListCommand.ListFilterDescriptor();
        desc2.setFoodQuery("cake");

        assertEquals(new ListCommand(desc1), new ListCommand(desc1Copy));
        org.junit.jupiter.api.Assertions.assertNotEquals(new ListCommand(desc1), new ListCommand(desc2));
        org.junit.jupiter.api.Assertions.assertNotEquals(new ListCommand(desc1), null);
    }
}
