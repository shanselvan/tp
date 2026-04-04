package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PRICE;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.EditMenuCommand;
import seedu.homechef.logic.commands.EditMenuCommand.EditMenuDescriptor;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditMenuCommand object.
 */
public class EditMenuCommandParser implements Parser<EditMenuCommand> {

    /**
     * Parses the given {@code String} of arguments and returns an EditMenuCommand object.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditMenuCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FOOD, PREFIX_PRICE, PREFIX_AVAILABILITY);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditMenuCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FOOD, PREFIX_PRICE, PREFIX_AVAILABILITY);

        EditMenuDescriptor editMenuDescriptor = new EditMenuDescriptor();

        if (argMultimap.getValue(PREFIX_FOOD).isPresent()) {
            editMenuDescriptor.setName(ParserUtil.parseFood(argMultimap.getValue(PREFIX_FOOD).get()));
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editMenuDescriptor.setPrice(ParserUtil.parseMenuPrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }
        if (argMultimap.getValue(PREFIX_AVAILABILITY).isPresent()) {
            editMenuDescriptor.setAvailable(
                    ParserUtil.parseAvailability(argMultimap.getValue(PREFIX_AVAILABILITY).get()));
        }

        if (!editMenuDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMenuCommand.MESSAGE_NOT_EDITED);
        }

        return new EditMenuCommand(index, editMenuDescriptor);
    }
}
