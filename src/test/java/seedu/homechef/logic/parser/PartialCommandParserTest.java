package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.commands.PartialCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the PartialCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the PartialCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class PartialCommandParserTest {

    private PartialCommandParser parser = new PartialCommandParser();

    @Test
    public void parse_validArgs_returnsPartialCommand() {
        assertParseSuccess(parser, "1", new PartialCommand(INDEX_FIRST_ORDER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "w",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PartialCommand.MESSAGE_USAGE));
    }
}
