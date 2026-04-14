package seedu.homechef.logic.parser;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.DeleteMenuCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMenuCommand object.
 */
public class DeleteMenuCommandParser implements Parser<DeleteMenuCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMenuCommand
     * and returns a DeleteMenuCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMenuCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMenuCommand(index);
        } catch (ParseException pe) {
            throw ParserUtil.handleIndexParseException(pe, DeleteMenuCommand.MESSAGE_USAGE);
        }
    }
}
