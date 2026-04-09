package seedu.homechef.model.menu.exceptions;

/**
 * Signals that a food name matched multiple menu items by substring with no exact match.
 */
public class AmbiguousMenuItemException extends RuntimeException {

    private final String matchingNames;

    /**
     * @param matchingNames comma-separated list of the names that matched
     */
    public AmbiguousMenuItemException(String matchingNames) {
        super("Ambiguous menu item match");
        this.matchingNames = matchingNames;
    }

    /**
     * Returns a comma-separated list of the menu item names that matched the ambiguous input.
     */
    public String getMatchingNames() {
        return matchingNames;
    }
}
