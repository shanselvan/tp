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
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentStatus;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHomeChef(), new UserPrefs());
        expectedModel = new ModelManager(model.getHomeChef(), new UserPrefs());
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
    public void execute_withMultipleFilters_filtersList() {
        Date target = new Date("26-03-2026");

        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setDate(target);
        d.setFoodQuery("Cake");

        expectedModel.updateFilteredOrderList(order ->
                order.getDate().equals(target)
                        && order.getFood().toString().toLowerCase().contains("cake"));

        assertCommandSuccess(new ListCommand(d), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withCompletionStatusFilter_filtersList() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setCompletionStatus(CompletionStatus.COMPLETED);

        expectedModel.updateFilteredOrderList(order ->
                order.getCompletionStatus() == CompletionStatus.COMPLETED);

        assertCommandSuccess(new ListCommand(d), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withPaymentStatusFilter_filtersList() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setPaymentStatus(PaymentStatus.PAID);

        expectedModel.updateFilteredOrderList(order ->
                order.getPaymentStatus() == PaymentStatus.PAID);

        assertCommandSuccess(new ListCommand(d), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withCompletionAndPaymentFilters_filtersList() {
        CompletionStatus completed = CompletionStatus.COMPLETED;
        PaymentStatus unpaid = PaymentStatus.UNPAID;

        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setCompletionStatus(completed);
        d.setPaymentStatus(unpaid);

        expectedModel.updateFilteredOrderList(order ->
                order.getCompletionStatus().equals(completed)
                        && order.getPaymentStatus().equals(unpaid));

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
