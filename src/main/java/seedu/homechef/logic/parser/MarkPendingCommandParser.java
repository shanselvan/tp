package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.MarkPendingCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkPendingCommand object
 */
public class MarkPendingCommandParser implements Parser<MarkPendingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkPendingCommand
     * and returns a MarkPendingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkPendingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkPendingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPendingCommand.MESSAGE_USAGE), pe);
        }
    }

}
