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
 * Marks an order identified using its displayed index from the HomeChef as pending.
 */
public class MarkPendingCommand extends Command {

    public static final String COMMAND_WORD = "pending";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the order identified by the index number used in the displayed order list as pending.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PENDING_ORDER_SUCCESS = "Marked order as pending: %1$s";
    public static final String MESSAGE_ALREADY_PENDING = "Order is already marked as pending.";

    private final Index targetIndex;

    public MarkPendingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToMarkPending = lastShownList.get(targetIndex.getZeroBased());
        if (orderToMarkPending.getCompletionStatus() == CompletionStatus.PENDING) {
            throw new CommandException(MESSAGE_ALREADY_PENDING);
        }
        Order pendingOrder = createPendingOrder(orderToMarkPending);

        model.setOrder(orderToMarkPending, pendingOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(String.format(MESSAGE_PENDING_ORDER_SUCCESS, Messages.format(pendingOrder)));
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToMarkPending}
     * marking {@code CompletionStatus} to pending.
     */
    private static Order createPendingOrder(Order orderToMarkPending) {
        assert orderToMarkPending != null;

        Food food = orderToMarkPending.getFood();
        Customer customer = orderToMarkPending.getCustomer();
        Phone phone = orderToMarkPending.getPhone();
        Email email = orderToMarkPending.getEmail();
        Address address = orderToMarkPending.getAddress();
        Date date = orderToMarkPending.getDate();
        CompletionStatus updatedCompletionStatus = CompletionStatus.PENDING;
        PaymentStatus paymentStatus = orderToMarkPending.getPaymentStatus();
        Set<DietTag> dietTags = orderToMarkPending.getTags();
        Quantity quantity = orderToMarkPending.getQuantity();
        Price price = orderToMarkPending.getPrice();
        Optional<PaymentInfo> paymentInfo = orderToMarkPending.getPaymentInfo();

        return new Order(food, customer, phone,
                email, address, date, updatedCompletionStatus, paymentStatus, dietTags, quantity, price, paymentInfo);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkPendingCommand)) {
            return false;
        }

        MarkPendingCommand otherMarkPendingCommand = (MarkPendingCommand) other;
        return targetIndex.equals(otherMarkPendingCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
