package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.UnpaidCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnpaidCommand object
 */
public class UnpaidCommandParser implements Parser<UnpaidCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnpaidCommand
     * and returns an UnpaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnpaidCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnpaidCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpaidCommand.MESSAGE_USAGE), pe);
        }
    }
}
