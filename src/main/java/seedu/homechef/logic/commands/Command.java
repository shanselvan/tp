package seedu.homechef.logic.commands;

import static seedu.homechef.logic.Messages.MESSAGE_MENU_ITEM_AMBIGUOUS;
import static seedu.homechef.logic.Messages.MESSAGE_MENU_ITEM_NOT_FOUND;
import static seedu.homechef.logic.Messages.MESSAGE_MENU_ITEM_UNAVAILABLE;

import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.ReadOnlyMenuBook;
import seedu.homechef.model.menu.exceptions.AmbiguousMenuItemException;
import seedu.homechef.model.menu.exceptions.MenuItemNotFoundException;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Resolves {@code foodName} to a unique available {@code MenuItem} from {@code menuBook}.
     *
     * @param menuBook the menu to search
     * @param foodName the food name as entered by the user
     * @throws CommandException if no item matches, the input is ambiguous, or the item is unavailable
     */
    protected static MenuItem resolveAvailableMenuItem(ReadOnlyMenuBook menuBook, String foodName)
            throws CommandException {
        MenuItem item;
        try {
            item = menuBook.resolveMenuItem(foodName);
        } catch (MenuItemNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_MENU_ITEM_NOT_FOUND, foodName));
        } catch (AmbiguousMenuItemException e) {
            throw new CommandException(
                    String.format(MESSAGE_MENU_ITEM_AMBIGUOUS, foodName, e.getMatchingNames()));
        }
        if (item.getAvailability() == Availability.NO) {
            throw new CommandException(
                    String.format(MESSAGE_MENU_ITEM_UNAVAILABLE, item.getFood().toString()));
        }
        return item;
    }

}
