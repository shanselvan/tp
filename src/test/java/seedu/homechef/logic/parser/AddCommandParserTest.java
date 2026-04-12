package seedu.homechef.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.BANK_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.CASH_NO_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.CASH_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.CUSTOMER_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.CUSTOMER_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.FOOD_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.FOOD_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_BANK_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_CASH_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_CUSTOMER_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_FOOD_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_PAYNOW_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.PAYNOW_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.homechef.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.homechef.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.homechef.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_CUSTOMER_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_CUSTOMER_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_FOOD_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PAYMENT_BANK;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PAYMENT_PAYNOW;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_BANK_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CASH_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYNOW_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_BANK_PAYMENT_REQUIRED;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_CASH_PAYMENT_REQUIRED;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_MULTIPLE_PAYMENT_PREFIXES;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_PAYNOW_PAYMENT_REQUIRED;
import static seedu.homechef.testutil.TypicalOrders.AMY;
import static seedu.homechef.testutil.TypicalOrders.BOB;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.AddCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.BankPayment;
import seedu.homechef.model.order.CashPayment;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.DietTag;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PayNowPayment;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Quantity;
import seedu.homechef.testutil.OrderBuilder;

public class AddCommandParserTest {
    private static final String PLACEHOLDER_PRICE = "0.01";
    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Order expectedOrder = new OrderBuilder(BOB).withTags(VALID_TAG_FRIEND).withPrice(PLACEHOLDER_PRICE).build();
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedOrderString = FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + TAG_DESC_FRIEND;
        assertParseFailure(parser, CUSTOMER_DESC_AMY + validExpectedOrderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CUSTOMER));
    }

    @Test
    public void parse_repeatedQuantity_failure() {
        String base = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY;
        assertParseFailure(parser, base + " " + PREFIX_QUANTITY + "2" + " " + PREFIX_QUANTITY + "3",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));
    }

    @Test
    public void parse_repeatedPaymentPrefixes_failure() {
        String base = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY;
        assertParseFailure(parser, base + BANK_PAYMENT_DESC + " " + PREFIX_BANK_PAYMENT + "UOB-REF-002",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BANK_PAYMENT));
        assertParseFailure(parser, base + PAYNOW_PAYMENT_DESC + " " + PREFIX_PAYNOW_PAYMENT + "+65 99887766",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PAYNOW_PAYMENT));
        assertParseFailure(parser, base + CASH_PAYMENT_DESC + CASH_NO_PAYMENT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CASH_PAYMENT));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Order expectedOrder = new OrderBuilder(AMY).withTags().withPrice(PLACEHOLDER_PRICE).build();
        assertParseSuccess(parser, FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_CUSTOMER_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_FOOD_DESC + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Food.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, FOOD_DESC_BOB + INVALID_CUSTOMER_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Customer.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, FOOD_DESC_BOB + CUSTOMER_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + DATE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DATE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, DietTag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withCashPayment_success() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + CASH_PAYMENT_DESC;
        Order expectedOrder = new OrderBuilder().withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withDate(VALID_DATE_AMY).withPrice(PLACEHOLDER_PRICE).withPaymentInfo(new CashPayment()).build();
        assertParseSuccess(parser, userInput, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_withCashNo_success() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + CASH_NO_PAYMENT_DESC;
        Order expectedOrder = new OrderBuilder().withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withDate(VALID_DATE_AMY).withPrice(PLACEHOLDER_PRICE).build();
        assertParseSuccess(parser, userInput, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_withPayNowPayment_success() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + PAYNOW_PAYMENT_DESC;
        Order expectedOrder = new OrderBuilder().withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withDate(VALID_DATE_AMY).withPrice(PLACEHOLDER_PRICE)
                .withPaymentInfo(new PayNowPayment(VALID_PAYMENT_PAYNOW)).build();
        assertParseSuccess(parser, userInput, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_withBankPayment_success() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + BANK_PAYMENT_DESC;
        Order expectedOrder = new OrderBuilder().withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withDate(VALID_DATE_AMY).withPrice(PLACEHOLDER_PRICE)
                .withPaymentInfo(new BankPayment(VALID_PAYMENT_BANK)).build();
        assertParseSuccess(parser, userInput, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_paymentValidation_failures() {
        String base = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY;
        assertParseFailure(parser, base + INVALID_CASH_PAYMENT_DESC, MESSAGE_CASH_PAYMENT_REQUIRED);
        assertParseFailure(parser, base + INVALID_PAYNOW_PAYMENT_DESC, MESSAGE_PAYNOW_PAYMENT_REQUIRED);
        assertParseFailure(parser, base + INVALID_BANK_PAYMENT_DESC, MESSAGE_BANK_PAYMENT_REQUIRED);
        assertParseFailure(parser, base + " cash/accepted", MESSAGE_CASH_PAYMENT_REQUIRED);
        assertParseFailure(parser, base + BANK_PAYMENT_DESC + PAYNOW_PAYMENT_DESC, MESSAGE_MULTIPLE_PAYMENT_PREFIXES);
    }

    @Test
    public void parse_withQuantity_parsedQuantityIsThree() throws ParseException {
        AddCommand command = parser.parse(FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + " " + PREFIX_QUANTITY + "3");
        assertEquals(new Quantity(3), extractOrder(command).getQuantity());
    }

    @Test
    public void parse_withoutQuantity_parsedQuantityDefaultsToOne() throws ParseException {
        AddCommand command = parser.parse(FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY);
        assertEquals(new Quantity(1), extractOrder(command).getQuantity());
    }

    @Test
    public void parse_invalidCalendarDate_failure() {
        assertParseFailure(parser, FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + " d/31-02-2026" + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_customerAndFoodWithApostropheAndSlash_success() throws ParseException {
        String foodWithPunctuation = "Chef's Fish/Chips & Rice @ Home";
        String customerWithPunctuation = "李雷 @ Home";
        AddCommand command = parser.parse(" " + PREFIX_FOOD + foodWithPunctuation
                + " " + PREFIX_CUSTOMER + customerWithPunctuation
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY);

        Order parsedOrder = extractOrder(command);
        assertEquals(new Food(foodWithPunctuation), parsedOrder.getFood());
        assertEquals(new Customer(customerWithPunctuation), parsedOrder.getCustomer());
    }

    private static Order extractOrder(AddCommand command) {
        try {
            java.lang.reflect.Field field = AddCommand.class.getDeclaredField("toAdd");
            field.setAccessible(true);
            return (Order) field.get(command);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Could not access AddCommand.toAdd via reflection", e);
        }
    }
}
