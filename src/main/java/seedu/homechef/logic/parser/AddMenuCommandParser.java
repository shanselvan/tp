package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.stream.Stream;

import seedu.homechef.logic.commands.AddMenuCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.menu.MenuItem;

/**
 * Parses input arguments and creates a new AddMenuCommand object.
 */
public class AddMenuCommandParser implements Parser<AddMenuCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMenuCommand
     * and returns an AddMenuCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMenuCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FOOD, PREFIX_PRICE, PREFIX_AVAILABILITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_FOOD, PREFIX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMenuCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FOOD, PREFIX_PRICE, PREFIX_AVAILABILITY);

        Food name = ParserUtil.parseFood(argMultimap.getValue(PREFIX_FOOD).get());
        Price price = ParserUtil.parseMenuPrice(argMultimap.getValue(PREFIX_PRICE).get());
        Availability availability = Availability.YES;
        if (argMultimap.getValue(PREFIX_AVAILABILITY).isPresent()) {
            availability = ParserUtil.parseAvailability(argMultimap.getValue(PREFIX_AVAILABILITY).get());
        }

        return new AddMenuCommand(new MenuItem(name, price, availability));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
