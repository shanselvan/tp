package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentStatus;

/**
 * Marks an order as partially paid in HomeChef.
 */
public class PartialCommand extends Command {
    public static final String COMMAND_WORD = "partial";

    public static final String MESSAGE_MARK_PARTIAL_SUCCESS = "Marked order as partially paid: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks as partially paid the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public PartialCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToEdit = lastShownList.get(targetIndex.getZeroBased());
        PaymentStatus partialStatus = PaymentStatus.PARTIAL;

        Order editedOrder = new Order(
                orderToEdit.getFood(),
                orderToEdit.getCustomer(),
                orderToEdit.getPhone(),
                orderToEdit.getEmail(),
                orderToEdit.getAddress(),
                orderToEdit.getDate(),
                orderToEdit.getCompletionStatus(),
                partialStatus,
                orderToEdit.getTags()
        );

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);

        return new CommandResult(generateSuccessMessage(editedOrder));
    }

    private String generateSuccessMessage(Order order) {
        return String.format(MESSAGE_MARK_PARTIAL_SUCCESS, Messages.format(order));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PartialCommand)) {
            return false;
        }
        PartialCommand otherCommand = (PartialCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
