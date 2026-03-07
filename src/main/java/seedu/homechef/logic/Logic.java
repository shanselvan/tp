package seedu.homechef.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.logic.commands.CommandResult;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.order.Order;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the HomeChef.
     *
     * @see seedu.homechef.model.Model#getHomeChef()
     */
    ReadOnlyHomeChef getHomeChef();

    /** Returns an unmodifiable view of the filtered list of orders */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Returns the user prefs' HomeChef file path.
     */
    Path getHomeChefFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
