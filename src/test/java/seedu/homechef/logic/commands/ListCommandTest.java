package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.homechef.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.homechef.logic.Messages;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.menu.MenuBook;
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
        model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getHomeChef(), new MenuBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        // EP: no filters provided
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        // EP: existing filtered state reset back to show-all
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withDate_filtersList() {
        Date target = new Date("26-03-2026");

        expectedModel.updateFilteredOrderList(order -> order.getDate().equals(target));
        assertCommandSuccess(new ListCommand(target), model,
                getFilteredFeedback(expectedModel.getFilteredOrderList().size()), expectedModel);

        List<Order> shown = model.getFilteredOrderList();
        // EP: date-only filter
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
                        && order.getFood().toString().toLowerCase(Locale.ROOT).contains("cake"));

        // EP: multiple filters combined with logical AND
        assertCommandSuccess(new ListCommand(d), model,
                getFilteredFeedback(expectedModel.getFilteredOrderList().size()), expectedModel);
    }

    @Test
    public void execute_withCustomerQuery_filtersListCaseInsensitively() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setCustomerQuery("alice");

        expectedModel.updateFilteredOrderList(order ->
                order.getCustomer().toString().toLowerCase(Locale.ROOT).contains("alice"));

        assertCommandSuccess(new ListCommand(d), model,
                getFilteredFeedback(expectedModel.getFilteredOrderList().size()), expectedModel);
        // EP: customer substring filter is case-insensitive
        assertTrue(model.getFilteredOrderList().stream()
                .allMatch(order -> order.getCustomer().toString().toLowerCase(Locale.ROOT).contains("alice")));
    }

    @Test
    public void execute_withFoodQuery_filtersListCaseInsensitively() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setFoodQuery("cake");

        expectedModel.updateFilteredOrderList(order ->
                order.getFood().toString().toLowerCase(Locale.ROOT).contains("cake"));

        assertCommandSuccess(new ListCommand(d), model,
                getFilteredFeedback(expectedModel.getFilteredOrderList().size()), expectedModel);
        // EP: food substring filter is case-insensitive
        assertTrue(model.getFilteredOrderList().stream()
                .allMatch(order -> order.getFood().toString().toLowerCase(Locale.ROOT).contains("cake")));
    }

    @Test
    public void execute_withPhoneQuery_filtersList() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setPhoneQuery("9435");

        expectedModel.updateFilteredOrderList(order ->
                order.getPhone().toString().toLowerCase(Locale.ROOT).contains("9435"));

        assertCommandSuccess(new ListCommand(d), model,
                getFilteredFeedback(expectedModel.getFilteredOrderList().size()), expectedModel);
        // EP: phone substring filter
        assertTrue(model.getFilteredOrderList().stream()
                .allMatch(order -> order.getPhone().toString().contains("9435")));
    }

    @Test
    public void execute_withCompletionStatusFilter_filtersList() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setCompletionStatus(CompletionStatus.COMPLETED);

        expectedModel.updateFilteredOrderList(order ->
                order.getCompletionStatus() == CompletionStatus.COMPLETED);

        // EP: completion-status-only filter
        assertCommandSuccess(new ListCommand(d), model,
                getFilteredFeedback(expectedModel.getFilteredOrderList().size()), expectedModel);
    }

    @Test
    public void execute_withPaymentStatusFilter_filtersList() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setPaymentStatus(PaymentStatus.PAID);

        expectedModel.updateFilteredOrderList(order ->
                order.getPaymentStatus() == PaymentStatus.PAID);

        // EP: payment-status-only filter
        assertCommandSuccess(new ListCommand(d), model,
                getFilteredFeedback(expectedModel.getFilteredOrderList().size()), expectedModel);
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

        // EP: status filters combined without text/date filters
        assertCommandSuccess(new ListCommand(d), model,
                getFilteredFeedback(expectedModel.getFilteredOrderList().size()), expectedModel);
    }

    private String getFilteredFeedback(int count) {
        return Messages.getOrdersListedOverviewMessage(count);
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

        // EP: same object -> returns true
        assertEquals(new ListCommand(desc1), new ListCommand(desc1));
        // EP: same descriptor values -> returns true
        assertEquals(new ListCommand(desc1), new ListCommand(desc1Copy));
        // EP: different descriptor values -> returns false
        assertNotEquals(new ListCommand(desc1), new ListCommand(desc2));
        // EP: null -> returns false
        assertNotEquals(new ListCommand(desc1), null);
        // EP: different types -> returns false
        assertNotEquals(new ListCommand(desc1), 1);
    }

    @Test
    public void listFilterDescriptor_equals() {
        ListCommand.ListFilterDescriptor descriptor = new ListCommand.ListFilterDescriptor();
        descriptor.setDate(new Date("16-04-2003"));
        descriptor.setCustomerQuery("alice");
        descriptor.setFoodQuery("cake");
        descriptor.setPhoneQuery("1234");
        descriptor.setCompletionStatus(CompletionStatus.COMPLETED);
        descriptor.setPaymentStatus(PaymentStatus.PAID);

        ListCommand.ListFilterDescriptor sameDescriptor = new ListCommand.ListFilterDescriptor();
        sameDescriptor.setDate(new Date("16-04-2003"));
        sameDescriptor.setCustomerQuery("alice");
        sameDescriptor.setFoodQuery("cake");
        sameDescriptor.setPhoneQuery("1234");
        sameDescriptor.setCompletionStatus(CompletionStatus.COMPLETED);
        sameDescriptor.setPaymentStatus(PaymentStatus.PAID);

        ListCommand.ListFilterDescriptor differentDescriptor = new ListCommand.ListFilterDescriptor();
        differentDescriptor.setPaymentStatus(PaymentStatus.UNPAID);

        // EP: same object -> returns true
        assertTrue(descriptor.equals(descriptor));
        // EP: same field values across all filter slots -> returns true
        assertTrue(descriptor.equals(sameDescriptor));
        // EP: null -> returns false
        assertFalse(descriptor.equals(null));
        // EP: different types -> returns false
        assertFalse(descriptor.equals(1));
        // EP: at least one differing field -> returns false
        assertFalse(descriptor.equals(differentDescriptor));
    }
}
