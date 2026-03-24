package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.PartialCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PartialCommand object
 */
public class PartialCommandParser implements Parser<PartialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PartialCommand
     * and returns a PartialCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public PartialCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PartialCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PartialCommand.MESSAGE_USAGE), pe);
        }
    }
}
