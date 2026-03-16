package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_BANK_NAME;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYMENT_METHOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYMENT_REF;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_WALLET_PROVIDER;
import static seedu.homechef.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.Model;
import seedu.homechef.model.order.NameContainsKeywordsPredicate;
import seedu.homechef.model.order.Order;
import seedu.homechef.testutil.EditOrderDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_FOOD_AMY = "Birthday Cake";
    public static final String VALID_FOOD_BOB = "Wedding Cake";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_DATE_AMY = "10-10-2026";
    public static final String VALID_DATE_BOB = "03-10-2026";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String FOOD_DESC_AMY = " " + PREFIX_FOOD + VALID_FOOD_AMY;
    public static final String FOOD_DESC_BOB = " " + PREFIX_FOOD + VALID_FOOD_BOB;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
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
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "99-10-2029"; // fake date not allowed in dates
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    // Payment info test constants
    public static final String VALID_PAYMENT_METHOD_CASH = "CASH";
    public static final String VALID_PAYMENT_METHOD_PAYNOW = "PAYNOW";
    public static final String VALID_PAYMENT_METHOD_BANK = "BANK";
    public static final String VALID_PAYMENT_METHOD_CARD = "CARD";
    public static final String VALID_PAYMENT_METHOD_EWALLET = "EWALLET";
    public static final String VALID_PAYMENT_REF_PAYNOW = "+65 91234567";
    public static final String VALID_PAYMENT_REF_BANK = "REF20240101";
    public static final String VALID_BANK_NAME = "DBS";
    public static final String VALID_PAYMENT_REF_CARD = "4321";
    public static final String VALID_WALLET_PROVIDER = "GrabPay";
    public static final String VALID_WALLET_ACCOUNT = "user@grab.com";

    public static final String PAYMENT_METHOD_DESC_CASH =
            " " + PREFIX_PAYMENT_METHOD + VALID_PAYMENT_METHOD_CASH;
    public static final String PAYMENT_METHOD_DESC_PAYNOW =
            " " + PREFIX_PAYMENT_METHOD + VALID_PAYMENT_METHOD_PAYNOW;
    public static final String PAYMENT_REF_DESC_PAYNOW =
            " " + PREFIX_PAYMENT_REF + VALID_PAYMENT_REF_PAYNOW;
    public static final String PAYMENT_METHOD_DESC_BANK =
            " " + PREFIX_PAYMENT_METHOD + VALID_PAYMENT_METHOD_BANK;
    public static final String PAYMENT_REF_DESC_BANK =
            " " + PREFIX_PAYMENT_REF + VALID_PAYMENT_REF_BANK;
    public static final String BANK_NAME_DESC =
            " " + PREFIX_BANK_NAME + VALID_BANK_NAME;
    public static final String PAYMENT_METHOD_DESC_CARD =
            " " + PREFIX_PAYMENT_METHOD + VALID_PAYMENT_METHOD_CARD;
    public static final String PAYMENT_REF_DESC_CARD =
            " " + PREFIX_PAYMENT_REF + VALID_PAYMENT_REF_CARD;
    public static final String PAYMENT_METHOD_DESC_EWALLET =
            " " + PREFIX_PAYMENT_METHOD + VALID_PAYMENT_METHOD_EWALLET;
    public static final String WALLET_PROVIDER_DESC =
            " " + PREFIX_WALLET_PROVIDER + VALID_WALLET_PROVIDER;
    public static final String WALLET_ACCOUNT_DESC =
            " " + PREFIX_PAYMENT_REF + VALID_WALLET_ACCOUNT;

    public static final String INVALID_PAYMENT_METHOD_DESC =
            " " + PREFIX_PAYMENT_METHOD + "CRYPTO";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditOrderDescriptor DESC_AMY;
    public static final EditCommand.EditOrderDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditOrderDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB)
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
        final String[] splitName = order.getName().fullName.split("\\s+");
        model.updateFilteredOrderList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredOrderList().size());
    }

}
