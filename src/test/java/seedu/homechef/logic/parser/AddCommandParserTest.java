package seedu.homechef.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.BANK_NAME_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.CUSTOMER_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.CUSTOMER_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.FOOD_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.FOOD_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_CUSTOMER_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_FOOD_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_PAYMENT_METHOD_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.PAYMENT_METHOD_DESC_BANK;
import static seedu.homechef.logic.commands.CommandTestUtil.PAYMENT_METHOD_DESC_CARD;
import static seedu.homechef.logic.commands.CommandTestUtil.PAYMENT_METHOD_DESC_CASH;
import static seedu.homechef.logic.commands.CommandTestUtil.PAYMENT_METHOD_DESC_EWALLET;
import static seedu.homechef.logic.commands.CommandTestUtil.PAYMENT_METHOD_DESC_PAYNOW;
import static seedu.homechef.logic.commands.CommandTestUtil.PAYMENT_REF_DESC_BANK;
import static seedu.homechef.logic.commands.CommandTestUtil.PAYMENT_REF_DESC_CARD;
import static seedu.homechef.logic.commands.CommandTestUtil.PAYMENT_REF_DESC_PAYNOW;
import static seedu.homechef.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.homechef.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.homechef.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.homechef.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_BANK_NAME;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_CUSTOMER_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_CUSTOMER_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_FOOD_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PAYMENT_REF_BANK;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PAYMENT_REF_CARD;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PAYMENT_REF_PAYNOW;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_WALLET_ACCOUNT;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_WALLET_PROVIDER;
import static seedu.homechef.logic.commands.CommandTestUtil.WALLET_ACCOUNT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.WALLET_PROVIDER_DESC;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_BANK_NAME_NOT_VALID;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_INVALID_PAYMENT_METHOD;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_METHOD_REQUIRED_FOR_DETAILS;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_REF_REQUIRED_FOR;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_UNEXPECTED_FIELDS_FOR_CASH;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_WALLET_PROVIDER_NOT_VALID;
import static seedu.homechef.testutil.TypicalOrders.AMY;
import static seedu.homechef.testutil.TypicalOrders.BOB;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.AddCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Quantity;
import seedu.homechef.model.tag.DietTag;
import seedu.homechef.testutil.OrderBuilder;

public class AddCommandParserTest {
    // Placeholder price the parser inserts; real price is resolved at command execution from the menu.
    private static final String PLACEHOLDER_PRICE = "0.01";

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Order expectedOrder = new OrderBuilder(BOB).withTags(VALID_TAG_FRIEND).withPrice(PLACEHOLDER_PRICE).build();

        // EP: whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedOrder));

        // EP: multiple tags - all accepted
        Order expectedOrderMultipleTags = new OrderBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .withPrice(PLACEHOLDER_PRICE).build();
        assertParseSuccess(parser,
                FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedOrderMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedOrderString = FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + TAG_DESC_FRIEND;

        // EP: multiple customers
        assertParseFailure(parser, CUSTOMER_DESC_AMY + validExpectedOrderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CUSTOMER));

        // EP: multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedOrderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // EP: multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedOrderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // EP: multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedOrderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // EP: multiple fields repeated
        assertParseFailure(parser,
                validExpectedOrderString + PHONE_DESC_AMY + EMAIL_DESC_AMY + CUSTOMER_DESC_AMY
                        + ADDRESS_DESC_AMY
                        + validExpectedOrderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FOOD, PREFIX_CUSTOMER, PREFIX_ADDRESS, PREFIX_DATE,
                        PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // EP: invalid customer
        assertParseFailure(parser, INVALID_CUSTOMER_DESC + validExpectedOrderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CUSTOMER));

        // EP: invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedOrderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // EP: invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedOrderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // EP: invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedOrderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // EP: invalid customer
        assertParseFailure(parser, validExpectedOrderString + INVALID_CUSTOMER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CUSTOMER));

        // EP: invalid email
        assertParseFailure(parser, validExpectedOrderString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // EP: invalid phone
        assertParseFailure(parser, validExpectedOrderString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // EP: invalid address
        assertParseFailure(parser, validExpectedOrderString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Order expectedOrder = new OrderBuilder(AMY).withTags().withPrice(PLACEHOLDER_PRICE).build();
        assertParseSuccess(parser,
                FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + DATE_DESC_AMY,
                new AddCommand(expectedOrder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // EP: missing customer prefix
        assertParseFailure(parser, VALID_CUSTOMER_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // EP: missing phone prefix
        assertParseFailure(parser, CUSTOMER_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // EP: missing email prefix
        assertParseFailure(parser, CUSTOMER_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // EP: missing address prefix
        assertParseFailure(parser, CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_AMY,
                expectedMessage);

        // EP: missing food prefix
        assertParseFailure(parser, VALID_FOOD_AMY + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB, expectedMessage);

        // EP: missing date prefix
        assertParseFailure(parser, FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + VALID_DATE_AMY, expectedMessage);

        // EP: all prefixes missing
        assertParseFailure(parser, VALID_CUSTOMER_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_AMY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // EP: invalid food
        assertParseFailure(parser,
                INVALID_FOOD_DESC + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Food.MESSAGE_CONSTRAINTS);

        // EP: invalid customer
        assertParseFailure(parser,
                FOOD_DESC_BOB + INVALID_CUSTOMER_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Customer.MESSAGE_CONSTRAINTS);

        // EP: invalid phone
        assertParseFailure(parser,
                FOOD_DESC_BOB + CUSTOMER_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS);

        // EP: invalid email
        assertParseFailure(parser,
                FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                        + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_CONSTRAINTS);

        // EP: invalid address
        assertParseFailure(parser,
                FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_CONSTRAINTS);

        // EP: invalid date
        assertParseFailure(parser,
                FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + INVALID_DATE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Date.MESSAGE_CONSTRAINTS);

        // EP: invalid tag
        assertParseFailure(parser,
                FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DATE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                DietTag.MESSAGE_CONSTRAINTS);

        // EP: two invalid values, only first invalid value reported
        assertParseFailure(parser,
                FOOD_DESC_BOB + INVALID_CUSTOMER_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + DATE_DESC_BOB,
                Customer.MESSAGE_CONSTRAINTS);

        // EP: non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + FOOD_DESC_BOB + CUSTOMER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + DATE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withCashPayment_success() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + PAYMENT_METHOD_DESC_CASH;
        Order expectedOrder = new OrderBuilder()
                .withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDate(VALID_DATE_AMY)
                .withPrice(PLACEHOLDER_PRICE)
                .withPaymentInfo(new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null))
                .build();
        assertParseSuccess(parser, userInput, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_withPayNowPayment_success() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + PAYMENT_METHOD_DESC_PAYNOW + PAYMENT_REF_DESC_PAYNOW;
        Order expectedOrder = new OrderBuilder()
                .withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDate(VALID_DATE_AMY)
                .withPrice(PLACEHOLDER_PRICE)
                .withPaymentInfo(new PaymentInfo(PaymentType.PAYNOW,
                        VALID_PAYMENT_REF_PAYNOW, null, null, null, null, null))
                .build();
        assertParseSuccess(parser, userInput, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_withBankPayment_success() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + PAYMENT_METHOD_DESC_BANK + BANK_NAME_DESC + PAYMENT_REF_DESC_BANK;
        Order expectedOrder = new OrderBuilder()
                .withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDate(VALID_DATE_AMY)
                .withPrice(PLACEHOLDER_PRICE)
                .withPaymentInfo(new PaymentInfo(PaymentType.BANK,
                        null, VALID_BANK_NAME, VALID_PAYMENT_REF_BANK, null, null, null))
                .build();
        assertParseSuccess(parser, userInput, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_withoutPayment_success() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY;
        Order expectedOrder = new OrderBuilder()
                .withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDate(VALID_DATE_AMY)
                .withPrice(PLACEHOLDER_PRICE).build();
        assertParseSuccess(parser, userInput, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_withCardPayment_success() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + PAYMENT_METHOD_DESC_CARD + PAYMENT_REF_DESC_CARD;
        Order expectedOrder = new OrderBuilder()
                .withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDate(VALID_DATE_AMY)
                .withPrice(PLACEHOLDER_PRICE)
                .withPaymentInfo(new PaymentInfo(PaymentType.CARD,
                        null, null, null, VALID_PAYMENT_REF_CARD, null, null))
                .build();
        assertParseSuccess(parser, userInput, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_withEWalletPayment_success() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + PAYMENT_METHOD_DESC_EWALLET + WALLET_PROVIDER_DESC + WALLET_ACCOUNT_DESC;
        Order expectedOrder = new OrderBuilder()
                .withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withDate(VALID_DATE_AMY)
                .withPrice(PLACEHOLDER_PRICE)
                .withPaymentInfo(new PaymentInfo(PaymentType.EWALLET,
                        null, null, null, null, VALID_WALLET_PROVIDER, VALID_WALLET_ACCOUNT))
                .build();
        assertParseSuccess(parser, userInput, new AddCommand(expectedOrder));
    }

    @Test
    public void parse_invalidPaymentMethod_failure() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + INVALID_PAYMENT_METHOD_DESC;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_PAYMENT_METHOD, "CRYPTO"));
    }

    @Test
    public void parse_payNowMissingRef_failure() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + PAYMENT_METHOD_DESC_PAYNOW;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_REF_REQUIRED_FOR, "PAYNOW"));
    }

    @Test
    public void parse_refWithoutMethod_failure() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + PAYMENT_REF_DESC_PAYNOW;
        assertParseFailure(parser, userInput,
                MESSAGE_METHOD_REQUIRED_FOR_DETAILS);
    }

    @Test
    public void parse_bankNameWithPayNow_failure() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + PAYMENT_METHOD_DESC_PAYNOW + PAYMENT_REF_DESC_PAYNOW + BANK_NAME_DESC;
        assertParseFailure(parser, userInput, MESSAGE_BANK_NAME_NOT_VALID);
    }

    @Test
    public void parse_walletProviderWithBank_failure() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + PAYMENT_METHOD_DESC_BANK + PAYMENT_REF_DESC_BANK + BANK_NAME_DESC + WALLET_PROVIDER_DESC;
        assertParseFailure(parser, userInput, MESSAGE_WALLET_PROVIDER_NOT_VALID);
    }

    @Test
    public void parse_cashWithRef_failure() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + PAYMENT_METHOD_DESC_CASH + PAYMENT_REF_DESC_PAYNOW;
        assertParseFailure(parser, userInput,
                MESSAGE_UNEXPECTED_FIELDS_FOR_CASH);
    }

    @Test
    public void parse_withQuantity_parsedQuantityIsThree() throws ParseException {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + " " + PREFIX_QUANTITY + "3";
        AddCommand command = parser.parse(userInput);
        Order parsed = extractOrder(command);
        assertEquals(new Quantity(3), parsed.getQuantity());
    }

    @Test
    public void parse_withoutQuantity_parsedQuantityDefaultsToOne() throws ParseException {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY;
        AddCommand command = parser.parse(userInput);
        Order parsed = extractOrder(command);
        assertEquals(new Quantity(1), parsed.getQuantity());
    }

    @Test
    public void parse_quantityZero_throwsParseException() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + " " + PREFIX_QUANTITY + "0";
        assertParseFailure(parser, userInput, Quantity.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_quantityNonNumeric_throwsParseException() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + " " + PREFIX_QUANTITY + "abc";
        assertParseFailure(parser, userInput, Quantity.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_quantityAboveMax_throwsParseException() {
        String userInput = FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + " " + PREFIX_QUANTITY + "1000";
        assertParseFailure(parser, userInput, Quantity.MESSAGE_CONSTRAINTS);
    }

    /**
     * Extracts the {@code Order} held inside an {@code AddCommand} using reflection.
     * This avoids exposing a test-only getter in production code.
     */
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
