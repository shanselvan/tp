package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.order.CompletionStatus;

/**
 * Deletes a menu item from the menu using its displayed index.
 */
public class DeleteMenuCommand extends Command {

    public static final String COMMAND_WORD = "delete-menu";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the displayed menu list.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MENU_ITEM_SUCCESS = "Deleted menu item: %1$s";
    public static final String MESSAGE_HAS_ACTIVE_ORDERS =
            "Cannot delete menu item: there are active orders for \"%1$s\"."
            + " Delete those orders first before removing this menu item.";

    private final Index targetIndex;

    /**
     * Creates a DeleteMenuCommand to delete the menu item at {@code targetIndex}.
     *
     * @param targetIndex the one-based index of the menu item to delete
     */
    public DeleteMenuCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<MenuItem> lastShownList = model.getFilteredMenuItemList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX);
        }

        MenuItem menuItemToDelete = lastShownList.get(targetIndex.getZeroBased());

        boolean hasActiveOrders = model.getHomeChef().getOrderList().stream()
                .anyMatch(order -> order.getFood().toString()
                        .equalsIgnoreCase(menuItemToDelete.getFood().toString())
                        && (order.getCompletionStatus() != CompletionStatus.COMPLETED
                        || !order.getPaymentStatus().isPaid()));

        if (hasActiveOrders) {
            throw new CommandException(
                    String.format(MESSAGE_HAS_ACTIVE_ORDERS, menuItemToDelete.getFood()));
        }

        model.deleteMenuItem(menuItemToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MENU_ITEM_SUCCESS,
                menuItemToDelete.getFood()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteMenuCommand)) {
            return false;
        }
        DeleteMenuCommand otherCommand = (DeleteMenuCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("targetIndex", targetIndex).toString();
    }
}
