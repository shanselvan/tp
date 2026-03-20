package seedu.homechef.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.commands.AddCommand;
import seedu.homechef.logic.commands.ClearCommand;
import seedu.homechef.logic.commands.DeleteCommand;
import seedu.homechef.logic.commands.EditCommand;
import seedu.homechef.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.homechef.logic.commands.ExitCommand;
import seedu.homechef.logic.commands.FindCommand;
import seedu.homechef.logic.commands.HelpCommand;
import seedu.homechef.logic.commands.ListCommand;
import seedu.homechef.logic.commands.MarkCompleteCommand;
import seedu.homechef.logic.commands.MarkInProgressCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.order.CustomerContainsKeywordsPredicate;
import seedu.homechef.model.order.Order;
import seedu.homechef.testutil.EditOrderDescriptorBuilder;
import seedu.homechef.testutil.OrderBuilder;
import seedu.homechef.testutil.OrderUtil;

public class HomeChefParserTest {

    private final HomeChefParser parser = new HomeChefParser();

    @Test
    public void parseCommand_add() throws Exception {
        Order order = new OrderBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(OrderUtil.getAddCommand(order));
        assertEquals(new AddCommand(order), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_ORDER), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Order order = new OrderBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(order).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ORDER.getOneBased() + " " + OrderUtil.getEditOrderDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_ORDER, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new CustomerContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " d/01-01-2021") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " cs/in_progress") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " ps/unpaid") instanceof ListCommand);
    }

    @Test
    public void parseCommand_mark_complete() throws Exception {
        MarkCompleteCommand command = (MarkCompleteCommand) parser.parseCommand(
                MarkCompleteCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new MarkCompleteCommand(INDEX_FIRST_ORDER), command);
    }

    @Test
    public void parseCommand_mark_inProgress() throws Exception {
        MarkInProgressCommand command = (MarkInProgressCommand) parser.parseCommand(
                MarkInProgressCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new MarkInProgressCommand(INDEX_FIRST_ORDER), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

}
