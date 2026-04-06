package seedu.homechef.logic.commands;

import static seedu.homechef.logic.Messages.MESSAGE_MENU_ITEM_AMBIGUOUS;
import static seedu.homechef.logic.Messages.MESSAGE_MENU_ITEM_NOT_FOUND;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.ReadOnlyMenuBook;

/**
 * Resolves a food name string to a unique {@code MenuItem} from a menu.
 */
class MenuItemResolver {

    private MenuItemResolver() {}

    /**
     * Returns the unique {@code MenuItem} matching {@code foodName} from {@code menuBook}.
     *
     * <p>Resolution order:
     * <ol>
     *   <li>Exact case-insensitive match — used immediately if found.</li>
     *   <li>Substring match — used if exactly one item matches.</li>
     * </ol>
     *
     * @throws CommandException if no items match, or if more than one item matches by substring.
     */
    static MenuItem resolve(ReadOnlyMenuBook menuBook, String foodName) throws CommandException {
        Optional<MenuItem> exactMatch = menuBook.findExact(foodName);
        if (exactMatch.isPresent()) {
            return exactMatch.get();
        }

        List<MenuItem> substringMatches = menuBook.findBySubstring(foodName);
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
