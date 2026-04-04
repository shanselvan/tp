package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PRICE;

import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.menu.MenuItem;

/**
 * Adds a menu item to the menu.
 */
public class AddMenuCommand extends Command {

    public static final String COMMAND_WORD = "add-menu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to the menu. "
            + "Parameters: "
            + PREFIX_FOOD + "NAME "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_AVAILABILITY + "AVAILABILITY] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_FOOD + "Chicken Rice "
            + PREFIX_PRICE + "5.50 "
            + PREFIX_AVAILABILITY + "true";

    public static final String MESSAGE_SUCCESS = "New menu item added: %1$s $%2$s";
    public static final String MESSAGE_DUPLICATE_MENU_ITEM =
            "A item with this name already exists in the menu";

    private final MenuItem toAdd;

    /**
     * Creates an AddMenuCommand to add the specified {@code MenuItem}.
     */
    public AddMenuCommand(MenuItem menuItem) {
        requireNonNull(menuItem);
        toAdd = menuItem;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMenuItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MENU_ITEM);
        }

        model.addMenuItem(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                toAdd.getFood(), toAdd.getPrice().value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddMenuCommand)) {
            return false;
        }
        AddMenuCommand otherCommand = (AddMenuCommand) other;
        return toAdd.equals(otherCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", toAdd).toString();
    }
}
