package seedu.homechef.logic.parser;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.PaidCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PaidCommand object
 */
public class PaidCommandParser implements Parser<PaidCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PaidCommand
     * and returns a PaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PaidCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PaidCommand(index);
        } catch (ParseException pe) {
            throw ParserUtil.handleIndexParseException(pe, PaidCommand.MESSAGE_USAGE);
        }
    }

}
