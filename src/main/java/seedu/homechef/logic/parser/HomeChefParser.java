package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.homechef.commons.core.LogsCenter;
import seedu.homechef.logic.commands.AddCommand;
import seedu.homechef.logic.commands.AddMenuCommand;
import seedu.homechef.logic.commands.ClearCommand;
import seedu.homechef.logic.commands.Command;
import seedu.homechef.logic.commands.DeleteCommand;
import seedu.homechef.logic.commands.DeleteMenuCommand;
import seedu.homechef.logic.commands.EditCommand;
import seedu.homechef.logic.commands.EditMenuCommand;
import seedu.homechef.logic.commands.ExitCommand;
import seedu.homechef.logic.commands.FindCommand;
import seedu.homechef.logic.commands.HelpCommand;
import seedu.homechef.logic.commands.ListCommand;
import seedu.homechef.logic.commands.MarkCompleteCommand;
import seedu.homechef.logic.commands.MarkInProgressCommand;
import seedu.homechef.logic.commands.PaidCommand;
import seedu.homechef.logic.commands.UnpaidCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class HomeChefParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(HomeChefParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case MarkCompleteCommand.COMMAND_WORD:
            return new MarkCompleteCommandParser().parse(arguments);

        case MarkInProgressCommand.COMMAND_WORD:
            return new MarkInProgressCommandParser().parse(arguments);

        case PaidCommand.COMMAND_WORD:
            return new PaidCommandParser().parse(arguments);

        case UnpaidCommand.COMMAND_WORD:
            return new UnpaidCommandParser().parse(arguments);

        case AddMenuCommand.COMMAND_WORD:
            return new AddMenuCommandParser().parse(arguments);

        case EditMenuCommand.COMMAND_WORD:
            return new EditMenuCommandParser().parse(arguments);

        case DeleteMenuCommand.COMMAND_WORD:
            return new DeleteMenuCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
