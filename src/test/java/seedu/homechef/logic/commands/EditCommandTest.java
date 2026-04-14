package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.Messages.MESSAGE_DUPLICATE_ORDER;
import static seedu.homechef.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_CUSTOMER_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_MENU_BIRTHDAY_PRICE;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.homechef.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.order.CashPayment;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PayNowPayment;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.testutil.EditOrderDescriptorBuilder;
import seedu.homechef.testutil.OrderBuilder;
import seedu.homechef.testutil.TypicalMenuItems;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(
            getTypicalHomeChef(), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Order orderToEdit = model.getFilteredOrderList().get(0);
        Order editedOrder = new OrderBuilder(orderToEdit)
                .withFood("Birthday Cake")
                .withCustomer("Amy Bee")
                .withPhone("85355255")
                .withEmail("amy@gmail.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withDate("10-03-2099")
                .withPrice(VALID_MENU_BIRTHDAY_PRICE) // price derived from menu; "Birthday Cake" costs "20.00"
                .build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withFood("Birthday Cake")
                .withCustomer("Amy Bee")
                .withPhone("85355255")
                .withEmail("amy@gmail.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withDate("10-03-2099")
                .build();

        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder));

        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToEdit, editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList.withCustomer(VALID_CUSTOMER_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withCustomer(VALID_CUSTOMER_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder));

        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.setOrder(lastOrder, editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, new EditOrderDescriptor());
        Order editedOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder));

        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder(orderInFilteredList).withCustomer(VALID_CUSTOMER_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER,
                new EditOrderDescriptorBuilder().withCustomer(VALID_CUSTOMER_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder));

        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateOrderUnfilteredList_failure() {
        Order firstOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(firstOrder).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ORDER, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_duplicateOrderFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        // edit order in filtered list into a duplicate in HomeChef
        Order orderInList = model.getHomeChef().getOrderList().get(INDEX_SECOND_ORDER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER,
                new EditOrderDescriptorBuilder(orderInList).build());

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withCustomer(VALID_CUSTOMER_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of HomeChef
     */
    @Test
    public void execute_invalidOrderIndexFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of HomeChef list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHomeChef().getOrderList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditOrderDescriptorBuilder().withCustomer(VALID_CUSTOMER_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ORDER, DESC_AMY);

        // EP: same values -> returns true
        EditOrderDescriptor copyDescriptor = new EditOrderDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ORDER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // EP: same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // EP: null -> returns false
        assertFalse(standardCommand.equals(null));

        // EP: different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // EP: different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ORDER, DESC_AMY)));

        // EP: different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ORDER, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();
        EditCommand editCommand = new EditCommand(index, editOrderDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editOrderDescriptor="
                + editOrderDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

    @Test
    public void execute_paymentInfoSet_success() {
        Model model = new ModelManager(
                getTypicalHomeChef(), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        PaymentInfo payNow = new PayNowPayment("+65 91234567");

        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(payNow).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, descriptor);

        Order expectedOrder = new OrderBuilder(orderToEdit).withPaymentInfo(payNow).build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(expectedOrder));

        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToEdit, expectedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_paymentInfoTypeSwitch_success() {
        Model model = new ModelManager(
                getTypicalHomeChef(), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        PaymentInfo payNow = new PayNowPayment("+65 91234567");
        Order orderWithPayNow = new OrderBuilder(orderToEdit).withPaymentInfo(payNow).build();
        model.setOrder(orderToEdit, orderWithPayNow);

        PaymentInfo cash = new CashPayment();
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(cash).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, descriptor);

        Order expectedOrder = new OrderBuilder(orderWithPayNow).withPaymentInfo(cash).build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(expectedOrder));
        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.setOrder(orderWithPayNow, expectedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        Order result = expectedModel.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        assertTrue(result.getPaymentInfo().get() instanceof CashPayment);
    }

    @Test
    public void editDescriptor_paymentInfoOnly_isAnyFieldEdited() {
        PaymentInfo cash = new CashPayment();
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(cash).build();
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    public void execute_paymentInfoCleared_success() {
        Model model = new ModelManager(
                getTypicalHomeChef(), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        PaymentInfo payNow = new PayNowPayment("+65 91234567");
        Order orderWithPayNow = new OrderBuilder(orderToEdit).withPaymentInfo(payNow).build();
        model.setOrder(orderToEdit, orderWithPayNow);

        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .clearPaymentInfo().build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, descriptor);

        Order expectedOrder = new Order(orderWithPayNow.getFood(), orderWithPayNow.getCustomer(),
                orderWithPayNow.getPhone(), orderWithPayNow.getEmail(), orderWithPayNow.getAddress(),
                orderWithPayNow.getDate(), orderWithPayNow.getCompletionStatus(), orderWithPayNow.getPaymentStatus(),
                orderWithPayNow.getTags(), orderWithPayNow.getQuantity(), orderWithPayNow.getPrice(),
                Optional.empty());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(expectedOrder));
        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.setOrder(orderWithPayNow, expectedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        assertTrue(expectedModel.getFilteredOrderList()
                .get(INDEX_FIRST_ORDER.getZeroBased()).getPaymentInfo().isEmpty());
    }

    @Test
    public void execute_foodByMenuIndex_resolvesToCorrectItem() {
        // BENSON (index 2) has "Sourdough Bread"; editing food to "1" should resolve to "Birthday Cake"
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_SECOND_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder(orderToEdit)
                .withFood("Birthday Cake")
                .withPrice(VALID_MENU_BIRTHDAY_PRICE)
                .build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withFood("1").build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ORDER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder));

        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToEdit, editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_dateEditedToPast_includesWarning() {
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        String yesterday = LocalDate.now().minusDays(1).format(seedu.homechef.model.order.Date.FORMATTER);

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withDate(yesterday).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, descriptor);

        Order expectedOrder = new OrderBuilder(orderToEdit).withDate(yesterday).build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(expectedOrder))
                + EditCommand.MESSAGE_PAST_DATE_WARNING;

        Model expectedModel = new ModelManager(
                new HomeChef(model.getHomeChef()), TypicalMenuItems.getTypicalMenuBook(), new UserPrefs());
        expectedModel.setOrder(orderToEdit, expectedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

}
