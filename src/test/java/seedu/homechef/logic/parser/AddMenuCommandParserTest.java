package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.AddMenuCommand;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.menu.MenuItem;

public class AddMenuCommandParserTest {

    private static final String VALID_NAME = "Chicken Rice";
    private static final String VALID_PRICE = "5.50";
    private static final String VALID_AVAILABILITY = "yes";
    private static final String VALID_UNAVAILABLE = "no";
    private static final String INVALID_NAME = "Chicken Rice&";
    private static final String INVALID_PRICE = "05.50";
    private static final String INVALID_AVAILABILITY = "maybe";

    private static final String NAME_DESC = " " + PREFIX_FOOD + VALID_NAME;
    private static final String PRICE_DESC = " " + PREFIX_PRICE + VALID_PRICE;
    private static final String AVAILABILITY_DESC = " " + PREFIX_AVAILABILITY + VALID_AVAILABILITY;
    private static final String UNAVAILABLE_DESC = " " + PREFIX_AVAILABILITY + VALID_UNAVAILABLE;
    private static final String INVALID_NAME_DESC = " " + PREFIX_FOOD + INVALID_NAME;
    private static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + INVALID_PRICE;
    private static final String INVALID_AVAILABILITY_DESC = " " + PREFIX_AVAILABILITY + INVALID_AVAILABILITY;
    private static final String PREAMBLE_NON_EMPTY = "preamble";

    private final AddMenuCommandParser parser = new AddMenuCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // EP: all required fields present with a valid explicit availability value.
        MenuItem expectedMenuItem = new MenuItem(new Food(VALID_NAME), new Price(VALID_PRICE), Availability.YES);
        assertParseSuccess(parser, NAME_DESC + PRICE_DESC + AVAILABILITY_DESC, new AddMenuCommand(expectedMenuItem));
    }

    @Test
    public void parse_optionalAvailabilityMissing_defaultsToTrue() {
        // EP: optional availability omitted, so parser should fall back to the default available state.
        MenuItem expectedMenuItem = new MenuItem(new Food(VALID_NAME), new Price(VALID_PRICE), Availability.YES);
        assertParseSuccess(parser, NAME_DESC + PRICE_DESC, new AddMenuCommand(expectedMenuItem));
    }

    @Test
    public void parse_validFalseAvailability_success() {
        // EP: valid explicit unavailable state.
        MenuItem expectedMenuItem = new MenuItem(new Food(VALID_NAME), new Price(VALID_PRICE), Availability.NO);
        assertParseSuccess(parser, NAME_DESC + PRICE_DESC + UNAVAILABLE_DESC, new AddMenuCommand(expectedMenuItem));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMenuCommand.MESSAGE_USAGE);

        // EP: missing prefixed menu name.
        assertParseFailure(parser, VALID_NAME + PRICE_DESC, expectedMessage);
        // EP: missing prefixed price.
        assertParseFailure(parser, NAME_DESC + VALID_PRICE, expectedMessage);
    }

    @Test
    public void parse_invalidMenuName_failureShowsFieldSpecificMessage() {
        // EP: invalid menu name containing a disallowed character.
        assertParseFailure(parser, INVALID_NAME_DESC + PRICE_DESC, Food.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPrice_failureShowsFieldSpecificMessage() {
        // BVA: leading-zero decimal price rejected by the menu price validator.
        assertParseFailure(parser, NAME_DESC + INVALID_PRICE_DESC, Price.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidAvailability_failureShowsFieldSpecificMessage() {
        // EP: availability accepts only enum names, so arbitrary text should fail with the field-specific message.
        assertParseFailure(parser, NAME_DESC + PRICE_DESC + INVALID_AVAILABILITY_DESC,
                ParserUtil.MESSAGE_INVALID_AVAILABILITY);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // EP: repeated non-repeatable field should be rejected before value parsing.
        assertParseFailure(parser, NAME_DESC + PRICE_DESC + NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FOOD));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        // EP: add-menu does not allow any non-empty preamble before prefixed arguments.
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC + PRICE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMenuCommand.MESSAGE_USAGE));
    }
}
