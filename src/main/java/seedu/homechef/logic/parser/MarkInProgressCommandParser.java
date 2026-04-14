package seedu.homechef.logic.parser;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.MarkInProgressCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkInProgressCommand object
 */
public class MarkInProgressCommandParser implements Parser<MarkInProgressCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkInProgressCommand
     * and returns a MarkInProgressCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkInProgressCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkInProgressCommand(index);
        } catch (ParseException pe) {
            throw ParserUtil.handleIndexParseException(pe, MarkInProgressCommand.MESSAGE_USAGE);
        }
    }

}
