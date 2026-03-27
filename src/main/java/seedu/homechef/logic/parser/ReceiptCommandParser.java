package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.ReceiptCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ReceiptCommand}.
 */
public class ReceiptCommandParser implements Parser<ReceiptCommand> {

    @Override
    public ReceiptCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ReceiptCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReceiptCommand.MESSAGE_USAGE), pe);
        }
    }
}
