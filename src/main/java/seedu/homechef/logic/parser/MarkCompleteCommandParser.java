package seedu.homechef.logic.parser;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.MarkCompleteCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkCompleteCommand object
 */
public class MarkCompleteCommandParser implements Parser<MarkCompleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCompleteCommand
     * and returns a MarkCompleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCompleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkCompleteCommand(index);
        } catch (ParseException pe) {
            throw ParserUtil.handleIndexParseException(pe, MarkCompleteCommand.MESSAGE_USAGE);
        }
    }

}
