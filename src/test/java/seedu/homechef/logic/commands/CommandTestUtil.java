package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_BANK_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CASH_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYNOW_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.homechef.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.Model;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentStatus;
import seedu.homechef.testutil.EditOrderDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_FOOD_AMY = "Birthday Cake";
    public static final String VALID_FOOD_BOB = "Wedding Cake";
    public static final String VALID_CUSTOMER_AMY = "Amy Bee";
    public static final String VALID_CUSTOMER_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_DATE_AMY = "10-10-2026";
    public static final String VALID_DATE_BOB = "03-10-2026";
    public static final String VALID_PRICE_AMY = "38.20";
    public static final String VALID_PRICE_BOB = "85.80";
    public static final String VALID_COMPLETION_STATUS_IN_PROGRESS = CompletionStatus.IN_PROGRESS.toString();
    public static final String VALID_COMPLETION_STATUS_COMPLETE = CompletionStatus.COMPLETED.toString();
    public static final String VALID_COMPLETION_STATUS_PENDING = CompletionStatus.PENDING.toString();
    public static final String VALID_PAYMENT_STATUS_PAID = PaymentStatus.PAID.toString();
    public static final String VALID_PAYMENT_STATUS_UNPAID = PaymentStatus.UNPAID.toString();
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String FOOD_DESC_AMY = " " + PREFIX_FOOD + VALID_FOOD_AMY;
    public static final String FOOD_DESC_BOB = " " + PREFIX_FOOD + VALID_FOOD_BOB;
    public static final String CUSTOMER_DESC_AMY = " " + PREFIX_CUSTOMER + VALID_CUSTOMER_AMY;
    public static final String CUSTOMER_DESC_BOB = " " + PREFIX_CUSTOMER + VALID_CUSTOMER_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_FOOD_DESC = " " + PREFIX_FOOD + "Bread&"; // '&' not allowed in names
    public static final String INVALID_CUSTOMER_DESC = " " + PREFIX_CUSTOMER + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "99-10-2029"; // fake date not allowed in dates
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_COMPLETION_STATUS = "Invalid status";
    public static final String INVALID_PAYMENT_STATUS = "Invalid status";

    // Payment info test constants
    public static final String VALID_PAYMENT_PAYNOW = "+65 91234567";
    public static final String VALID_PAYMENT_BANK = "DBS-REF-001";

    public static final String CASH_PAYMENT_DESC =
            " " + PREFIX_CASH_PAYMENT + "yes";
    public static final String CASH_NO_PAYMENT_DESC =
            " " + PREFIX_CASH_PAYMENT + "no";
    public static final String PAYNOW_PAYMENT_DESC =
            " " + PREFIX_PAYNOW_PAYMENT + VALID_PAYMENT_PAYNOW;
    public static final String BANK_PAYMENT_DESC =
            " " + PREFIX_BANK_PAYMENT + VALID_PAYMENT_BANK;
    public static final String INVALID_PAYNOW_PAYMENT_DESC =
            " " + PREFIX_PAYNOW_PAYMENT;
    public static final String INVALID_BANK_PAYMENT_DESC =
            " " + PREFIX_BANK_PAYMENT;
    public static final String INVALID_CASH_PAYMENT_DESC =
            " " + PREFIX_CASH_PAYMENT;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditOrderDescriptor DESC_AMY;
    public static final EditCommand.EditOrderDescriptor DESC_BOB;

    // Menu test constants
    public static final String VALID_MENU_BIRTHDAY_PRICE = "20.00";
    public static final String VALID_MENU_BREAD_PRICE = "9.80";
    public static final String VALID_MENU_CUPCAKES_PRICE = "35.70";
    public static final String VALID_MENU_COOKIES_PRICE = "8.50";
    public static final String VALID_MENU_THREETIER_PRICE = "150.00";
    public static final String VALID_MENU_MUFFIN_PRICE = "12.60";
    public static final String VALID_MENU_DOUGHNUT_PRICE = "18";
    public static final String VALID_MENU_WEDDING_PRICE = "80.00";
    public static final String VALID_MENU_CHICKEN_PRICE = "5.50";

    static {
        DESC_AMY = new EditOrderDescriptorBuilder().withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditOrderDescriptorBuilder().withCustomer(VALID_CUSTOMER_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the HomeChef, filtered order list and selected order in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        HomeChef expectedHomeChef = new HomeChef(actualModel.getHomeChef());
        List<Order> expectedFilteredList = new ArrayList<>(actualModel.getFilteredOrderList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedHomeChef, actualModel.getHomeChef());
        assertEquals(expectedFilteredList, actualModel.getFilteredOrderList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the order at the given {@code targetIndex} in the
     * {@code model}'s HomeChef.
     */
    public static void showOrderAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredOrderList().size());

        Order order = model.getFilteredOrderList().get(targetIndex.getZeroBased());
        final String firstName = order.getCustomer().toString().split("\\s+")[0].toLowerCase();
        model.updateFilteredOrderList(currentOrder ->
                currentOrder.getCustomer().toString().toLowerCase().contains(firstName));

        assertEquals(1, model.getFilteredOrderList().size());
    }

}
