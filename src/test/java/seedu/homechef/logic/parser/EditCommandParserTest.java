package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.BANK_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.CASH_NO_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.CASH_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.CUSTOMER_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_BANK_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_CASH_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_CUSTOMER_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_PAYNOW_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.PAYNOW_PAYMENT_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.homechef.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_CUSTOMER_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PAYMENT_BANK;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PAYMENT_PAYNOW;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_BANK_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CASH_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYNOW_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_BANK_PAYMENT_REQUIRED;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_CASH_PAYMENT_REQUIRED;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_MULTIPLE_PAYMENT_PREFIXES;
import static seedu.homechef.logic.parser.ParserUtil.MESSAGE_PAYNOW_PAYMENT_REQUIRED;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.EditCommand;
import seedu.homechef.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.BankPayment;
import seedu.homechef.model.order.CashPayment;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.DietTag;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.PayNowPayment;
import seedu.homechef.model.order.Phone;
import seedu.homechef.testutil.EditOrderDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private final EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, VALID_CUSTOMER_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-5" + CUSTOMER_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0" + CUSTOMER_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_CUSTOMER_DESC, Customer.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, DietTag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = INDEX_SECOND_ORDER.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + CUSTOMER_DESC_AMY + TAG_DESC_FRIEND;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withCustomer(VALID_CUSTOMER_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, userInput, new EditCommand(INDEX_SECOND_ORDER, descriptor));
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_ORDER;
        assertParseSuccess(parser, targetIndex.getOneBased() + CUSTOMER_DESC_AMY,
                new EditCommand(targetIndex,
                        new EditOrderDescriptorBuilder().withCustomer(VALID_CUSTOMER_AMY).build()));
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }

    @Test
    public void parse_repeatedQuantity_failure() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_QUANTITY + "10"
                + " " + PREFIX_QUANTITY + "434";
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));
    }

    @Test
    public void parse_repeatedPaymentPrefixes_failure() {
        Index targetIndex = INDEX_FIRST_ORDER;
        assertParseFailure(parser, targetIndex.getOneBased() + BANK_PAYMENT_DESC + " " + PREFIX_BANK_PAYMENT + "OCBC-123",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BANK_PAYMENT));
        assertParseFailure(parser, targetIndex.getOneBased() + PAYNOW_PAYMENT_DESC + " " + PREFIX_PAYNOW_PAYMENT + "+65 90001111",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PAYNOW_PAYMENT));
        assertParseFailure(parser, targetIndex.getOneBased() + CASH_PAYMENT_DESC + CASH_NO_PAYMENT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CASH_PAYMENT));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_ORDER;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withTags().build();
        assertParseSuccess(parser, userInput, new EditCommand(targetIndex, descriptor));
    }

    @Test
    public void parse_payment_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        assertParseSuccess(parser, targetIndex.getOneBased() + CASH_PAYMENT_DESC,
                new EditCommand(targetIndex,
                        new EditOrderDescriptorBuilder().withPaymentInfo(new CashPayment()).build()));

        assertParseSuccess(parser, targetIndex.getOneBased() + CASH_NO_PAYMENT_DESC,
                new EditCommand(targetIndex,
                        new EditOrderDescriptorBuilder().clearPaymentInfo().build()));

        assertParseSuccess(parser, targetIndex.getOneBased() + PAYNOW_PAYMENT_DESC,
                new EditCommand(targetIndex,
                        new EditOrderDescriptorBuilder().withPaymentInfo(
                                new PayNowPayment(VALID_PAYMENT_PAYNOW)).build()));

        assertParseSuccess(parser, targetIndex.getOneBased() + BANK_PAYMENT_DESC,
                new EditCommand(targetIndex,
                        new EditOrderDescriptorBuilder().withPaymentInfo(
                                new BankPayment(VALID_PAYMENT_BANK)).build()));
    }

    @Test
    public void parse_payment_failure() {
        assertParseFailure(parser, "1" + INVALID_CASH_PAYMENT_DESC, MESSAGE_CASH_PAYMENT_REQUIRED);
        assertParseFailure(parser, "1" + INVALID_PAYNOW_PAYMENT_DESC, MESSAGE_PAYNOW_PAYMENT_REQUIRED);
        assertParseFailure(parser, "1" + INVALID_BANK_PAYMENT_DESC, MESSAGE_BANK_PAYMENT_REQUIRED);
        assertParseFailure(parser, "1" + BANK_PAYMENT_DESC + PAYNOW_PAYMENT_DESC, MESSAGE_MULTIPLE_PAYMENT_PREFIXES);
        assertParseFailure(parser, "1" + CASH_PAYMENT_DESC + BANK_PAYMENT_DESC, MESSAGE_MULTIPLE_PAYMENT_PREFIXES);
        assertParseFailure(parser, "1" + CASH_PAYMENT_DESC + PAYNOW_PAYMENT_DESC, MESSAGE_MULTIPLE_PAYMENT_PREFIXES);
    }

    @Test
    public void parse_editPayNowWithInternalSpaces_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_PAYNOW_PAYMENT + "  +65   99999999  ";
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(new PayNowPayment("+65 99999999"))
                .build();
        assertParseSuccess(parser, userInput, new EditCommand(targetIndex, descriptor));
    }
}

