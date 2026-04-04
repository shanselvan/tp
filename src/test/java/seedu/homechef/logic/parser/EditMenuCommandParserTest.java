package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.EditMenuCommand;
import seedu.homechef.logic.commands.EditMenuCommand.EditMenuDescriptor;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.menu.Price;

public class EditMenuCommandParserTest {

    private static final String VALID_NAME = "Chicken Rice";
    private static final String VALID_PRICE = "5.50";
    private static final String VALID_AVAILABILITY = "false";
    private static final String INVALID_NAME = "Chicken Rice&";
    private static final String INVALID_PRICE = "05.50";
    private static final String INVALID_AVAILABILITY = "yes";

    private static final String INDEX_FIRST = "1";
    private static final String NAME_DESC = " " + PREFIX_FOOD + VALID_NAME;
    private static final String PRICE_DESC = " " + PREFIX_PRICE + VALID_PRICE;
    private static final String AVAILABILITY_DESC = " " + PREFIX_AVAILABILITY + VALID_AVAILABILITY;
    private static final String INVALID_NAME_DESC = " " + PREFIX_FOOD + INVALID_NAME;
    private static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + INVALID_PRICE;
    private static final String INVALID_AVAILABILITY_DESC = " " + PREFIX_AVAILABILITY + INVALID_AVAILABILITY;

    private final EditMenuCommandParser parser = new EditMenuCommandParser();

    @Test
    public void parse_validName_success() {
        // EP: valid index with a valid menu name edit.
        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setName(new Food(VALID_NAME));
        assertParseSuccess(parser, INDEX_FIRST + NAME_DESC, new EditMenuCommand(INDEX_FIRST_ORDER, descriptor));
    }

    @Test
    public void parse_validPriceAndAvailability_success() {
        // EP: valid index with valid price and availability edits.
        EditMenuDescriptor descriptor = new EditMenuDescriptor();
        descriptor.setPrice(new Price(VALID_PRICE));
        descriptor.setAvailable(false);
        assertParseSuccess(parser, INDEX_FIRST + PRICE_DESC + AVAILABILITY_DESC,
                new EditMenuCommand(INDEX_FIRST_ORDER, descriptor));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // BVA: index must be a non-zero unsigned integer.
        assertParseFailure(parser, "0" + NAME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMenuCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noFieldsSpecified_failure() {
        // EP: valid index but no editable fields provided.
        assertParseFailure(parser, INDEX_FIRST, EditMenuCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidMenuName_failureShowsFieldSpecificMessage() {
        // EP: invalid menu name containing a disallowed character.
        assertParseFailure(parser, INDEX_FIRST + INVALID_NAME_DESC, Food.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPrice_failureShowsFieldSpecificMessage() {
        // BVA: leading-zero decimal price rejected by the menu price validator.
        assertParseFailure(parser, INDEX_FIRST + INVALID_PRICE_DESC, Price.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidAvailability_failureShowsFieldSpecificMessage() {
        // EP: availability accepts only true/false, so arbitrary text should fail with the field-specific message.
        assertParseFailure(parser, INDEX_FIRST + INVALID_AVAILABILITY_DESC,
                ParserUtil.MESSAGE_INVALID_AVAILABILITY);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // EP: repeated non-repeatable field should be rejected before value parsing.
        assertParseFailure(parser, INDEX_FIRST + NAME_DESC + NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FOOD));
    }
}
