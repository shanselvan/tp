package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.BANK_NAME_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_PAYMENT_METHOD_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.homechef.logic.commands.CommandTestUtil.NAME_DESC_AMY;
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
import static seedu.homechef.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.homechef.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_BANK_NAME;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_NAME_AMY;
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
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.EditCommand;
import seedu.homechef.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Name;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.tag.DietTag;
import seedu.homechef.testutil.EditOrderDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, DietTag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Order} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, DietTag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, DietTag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, DietTag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ORDER;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ORDER;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditOrderDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_ORDER;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_editPaymentCash_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + PAYMENT_METHOD_DESC_CASH;
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(new PaymentInfo(PaymentType.CASH, null, null, null, null, null, null))
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_editPaymentPayNow_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + PAYMENT_METHOD_DESC_PAYNOW + PAYMENT_REF_DESC_PAYNOW;
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(new PaymentInfo(PaymentType.PAYNOW,
                        VALID_PAYMENT_REF_PAYNOW, null, null, null, null, null))
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_editPaymentBank_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased()
                + PAYMENT_METHOD_DESC_BANK + BANK_NAME_DESC + PAYMENT_REF_DESC_BANK;
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(new PaymentInfo(PaymentType.BANK,
                        null, VALID_BANK_NAME, VALID_PAYMENT_REF_BANK, null, null, null))
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_editPaymentCard_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + PAYMENT_METHOD_DESC_CARD + PAYMENT_REF_DESC_CARD;
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(new PaymentInfo(PaymentType.CARD,
                        null, null, null, VALID_PAYMENT_REF_CARD, null, null))
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_editPaymentEWallet_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased()
                + PAYMENT_METHOD_DESC_EWALLET + WALLET_PROVIDER_DESC + WALLET_ACCOUNT_DESC;
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPaymentInfo(new PaymentInfo(PaymentType.EWALLET,
                        null, null, null, null, VALID_WALLET_PROVIDER, VALID_WALLET_ACCOUNT))
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidPaymentMethod_failure() {
        String userInput = "1" + INVALID_PAYMENT_METHOD_DESC;
        assertParseFailure(parser, userInput,
                "Invalid payment method: CRYPTO. Valid types: CASH, PAYNOW, BANK, CARD, EWALLET.");
    }

    @Test
    public void parse_bankNameWithPayNow_failure() {
        String userInput = "1" + PAYMENT_METHOD_DESC_PAYNOW + PAYMENT_REF_DESC_PAYNOW + BANK_NAME_DESC;
        assertParseFailure(parser, userInput, "b/ only valid for BANK payment type.");
    }

    @Test
    public void parse_walletProviderWithBank_failure() {
        String userInput = "1" + PAYMENT_METHOD_DESC_BANK + PAYMENT_REF_DESC_BANK + BANK_NAME_DESC
                + WALLET_PROVIDER_DESC;
        assertParseFailure(parser, userInput, "w/ only valid for EWALLET payment type.");
    }
}
