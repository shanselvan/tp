package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.commands.ListCommand;
import seedu.homechef.model.order.Date;

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
    public void parse_validCustomer_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setCustomerQuery("alice");
        assertParseSuccess(parser, " c/alice", new ListCommand(d));
    }

    @Test
    public void parse_validFood_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setFoodQuery("cake");
        assertParseSuccess(parser, " f/cake", new ListCommand(d));
    }

    @Test
    public void parse_validMultipleFilters_success() {
        ListCommand.ListFilterDescriptor d = new ListCommand.ListFilterDescriptor();
        d.setDate(new Date("16-04-2003"));
        d.setCustomerQuery("alice");
        d.setFoodQuery("cake");
        assertParseSuccess(parser, " d/16-04-2003 c/alice f/cake", new ListCommand(d));
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

}
