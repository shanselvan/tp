package seedu.homechef.logic.commands;

import static seedu.homechef.logic.Messages.MESSAGE_MENU_ITEM_AMBIGUOUS;
import static seedu.homechef.logic.Messages.MESSAGE_MENU_ITEM_NOT_FOUND;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.menu.MenuItem;

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
     * Returns the unique {@code MenuItem} matching {@code foodName} from {@code menuItems}.
     * Exact case-insensitive match takes priority and bypasses ambiguity checking entirely;
     * falls back to substring match only when no exact match exists.
     *
     * @param menuItems the list of menu items to search
     * @param foodName the food name as entered by the user (case-insensitive)
     * @throws CommandException if no items match, or if the input matches multiple items
     *         by substring and no exact match exists
     */
    public static MenuItem resolveMenuItem(List<MenuItem> menuItems, String foodName)
            throws CommandException {
        Optional<MenuItem> exactMatch = menuItems.stream()
                .filter(item -> item.getFood().toString().equalsIgnoreCase(foodName))
                .findFirst();
        if (exactMatch.isPresent()) {
            return exactMatch.get();
        }

        List<MenuItem> substringMatches = menuItems.stream()
                .filter(item -> item.getFood().nameContains(foodName))
                .collect(Collectors.toList());
        if (substringMatches.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_MENU_ITEM_NOT_FOUND, foodName));
        }
        if (substringMatches.size() > 1) {
            String matchingNames = substringMatches.stream()
                    .map(item -> item.getFood().toString())
                    .collect(Collectors.joining(", "));
            throw new CommandException(String.format(MESSAGE_MENU_ITEM_AMBIGUOUS, foodName, matchingNames));
        }
        return substringMatches.get(0);
    }

}
