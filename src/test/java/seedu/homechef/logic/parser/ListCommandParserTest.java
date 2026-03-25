package seedu.homechef.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.commands.ListCommand;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.PaymentStatus;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noDatePrefix_returnsDefaultListCommand() {
        assertParseSuccess(parser, "", new ListCommand());
    }

    @Test
    public void parse_validDate_success() {
        assertParseSuccess(parser, " d/16-04-2003", new ListCommand(new Date("16-04-2003")));
    }

    @Test
    public void parse_preambleWithDatePrefix_failure() {
        assertParseFailure(parser, " preamble d/16-04-2003",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyDateValue_failure() {
        // depending on your parser, this might be MESSAGE_USAGE instead; adjust if needed
        assertParseFailure(parser, " d/", Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDateValue_failure() {
        assertParseFailure(parser, " d/99-99-9999", Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validMultipleFilters_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setDate(new Date("16-04-2003"));
        d.setCustomerQuery("alice");
        d.setFoodQuery("cake");
        d.setPhoneQuery("9435");
        d.setCompletionStatus(CompletionStatus.IN_PROGRESS);
        d.setPaymentStatus(PaymentStatus.PAID);

        assertParseSuccess(parser,
                " d/16-04-2003 c/alice f/cake p/9435 cs/in progress ps/paid",
                new ListCommand(d));
    }

    @Test
    public void parse_emptyCustomer_failure() {
        assertParseFailure(parser, " c/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyFood_failure() {
        assertParseFailure(parser, " f/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCompletionStatus_failure() {
        // Now expecting IllegalArgumentException directly from fromString
        assertThrows(IllegalArgumentException.class, () -> CompletionStatus.fromString("zzz"));
    }

    @Test
    public void parse_validCompletionStatusPendingKeyword_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setCompletionStatus(CompletionStatus.PENDING);
        assertParseSuccess(parser, " cs/pending", new ListCommand(d));
    }

    @Test
    public void parse_validCompletionStatusExactString_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setCompletionStatus(CompletionStatus.IN_PROGRESS);
        assertParseSuccess(parser, " cs/In Progress", new ListCommand(d));
    }

    @Test
    public void parse_validCompletionStatusAlternativeKeyword_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setCompletionStatus(CompletionStatus.IN_PROGRESS);
        assertParseSuccess(parser, " cs/in progress", new ListCommand(d));
    }

    @Test
    public void parse_validCompletionStatusCompletedKeyword_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setCompletionStatus(CompletionStatus.COMPLETED);
        assertParseSuccess(parser, " cs/completed", new ListCommand(d));
    }

    @Test
    public void parse_validPaymentStatus_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setPaymentStatus(PaymentStatus.PAID);
        assertParseSuccess(parser, " ps/paid", new ListCommand(d));
    }

    @Test
    public void parse_validPaymentStatusUnpaid_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setPaymentStatus(PaymentStatus.UNPAID);
        assertParseSuccess(parser, " ps/unpaid", new ListCommand(d));
    }

    @Test
    public void parse_validPaymentStatusPartial_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setPaymentStatus(PaymentStatus.PARTIAL);
        assertParseSuccess(parser, " ps/partial", new ListCommand(d));
    }

    @Test
    public void parse_validPaymentStatusExactString_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setPaymentStatus(PaymentStatus.PAID);
        assertParseSuccess(parser, " ps/Paid", new ListCommand(d));
    }

}
