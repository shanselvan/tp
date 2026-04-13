package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.logic.Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;
import static seedu.homechef.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.DietTag;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentStatus;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Quantity;

/**
 * Marks an order identified using its displayed index from the HomeChef as In Progress.
 */
public class MarkInProgressCommand extends Command {

    public static final String COMMAND_WORD = "inprogress";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the order identified by the index number used in the displayed order list as in progress.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_IN_PROGRESS_ORDER_SUCCESS = "Marked order as in progress: %1$s";
    public static final String MESSAGE_ALREADY_IN_PROGRESS = "Order is already marked as in progress.";

    private final Index targetIndex;

    public MarkInProgressCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToMarkInProgress = lastShownList.get(targetIndex.getZeroBased());
        if (orderToMarkInProgress.getCompletionStatus() == CompletionStatus.IN_PROGRESS) {
            throw new CommandException(MESSAGE_ALREADY_IN_PROGRESS);
        }
        Order incompleteOrder = createInProgressOrder(orderToMarkInProgress);

        model.setOrder(orderToMarkInProgress, incompleteOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(String.format(
                MESSAGE_IN_PROGRESS_ORDER_SUCCESS, Messages.format(incompleteOrder)));
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToMarkInProgress}
     * marking {@code CompletionStatus} in progress.
     */
    private static Order createInProgressOrder(Order orderToMarkInProgress) {
        assert orderToMarkInProgress != null;

        Food food = orderToMarkInProgress.getFood();
        Customer customer = orderToMarkInProgress.getCustomer();
        Phone phone = orderToMarkInProgress.getPhone();
        Email email = orderToMarkInProgress.getEmail();
        Address address = orderToMarkInProgress.getAddress();
        Date date = orderToMarkInProgress.getDate();
        CompletionStatus updatedCompletionStatus = CompletionStatus.IN_PROGRESS;
        PaymentStatus paymentStatus = orderToMarkInProgress.getPaymentStatus();
        Set<DietTag> dietTags = orderToMarkInProgress.getTags();
        Quantity quantity = orderToMarkInProgress.getQuantity();
        Price price = orderToMarkInProgress.getPrice();
        Optional<PaymentInfo> paymentInfo = orderToMarkInProgress.getPaymentInfo();

        return new Order(food, customer, phone,
                email, address, date, updatedCompletionStatus, paymentStatus, dietTags, quantity, price, paymentInfo);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkInProgressCommand)) {
            return false;
        }

        MarkInProgressCommand otherMarkInProgressCommand = (MarkInProgressCommand) other;
        return targetIndex.equals(otherMarkInProgressCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
