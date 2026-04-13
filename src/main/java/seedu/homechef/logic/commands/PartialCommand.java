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
 * Marks an order as partially paid in HomeChef.
 */
public class PartialCommand extends Command {
    public static final String COMMAND_WORD = "partial";

    public static final String MESSAGE_MARK_PARTIAL_SUCCESS = "Marked order as partially paid: %1$s";
    public static final String MESSAGE_ALREADY_PARTIAL = "Order is already marked as partially paid.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks as partially paid the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
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
            throw new CommandException(MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToMarkPartial = lastShownList.get(targetIndex.getZeroBased());
        if (orderToMarkPartial.getPaymentStatus().isPartial()) {
            throw new CommandException(MESSAGE_ALREADY_PARTIAL);
        }
        Order partialOrder = createPartialOrder(orderToMarkPartial);

        model.setOrder(orderToMarkPartial, partialOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);

        return new CommandResult(generateSuccessMessage(partialOrder));
    }

    /**
     * Creates and returns an {@code Order} with the details of {@code orderToMarkPartial}
     * marking {@code PaymentStatus} as partial.
     */
    private static Order createPartialOrder(Order orderToMarkPartial) {
        assert orderToMarkPartial != null;

        Food food = orderToMarkPartial.getFood();
        Customer customer = orderToMarkPartial.getCustomer();
        Phone phone = orderToMarkPartial.getPhone();
        Email email = orderToMarkPartial.getEmail();
        Address address = orderToMarkPartial.getAddress();
        Date date = orderToMarkPartial.getDate();
        CompletionStatus completionStatus = orderToMarkPartial.getCompletionStatus();
        PaymentStatus updatedPaymentStatus = PaymentStatus.PARTIAL;
        Set<DietTag> dietTags = orderToMarkPartial.getTags();
        Quantity quantity = orderToMarkPartial.getQuantity();
        Price price = orderToMarkPartial.getPrice();
        Optional<PaymentInfo> paymentInfo = orderToMarkPartial.getPaymentInfo();

        return new Order(food, customer, phone, email, address, date,
                completionStatus, updatedPaymentStatus, dietTags, quantity, price, paymentInfo);
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
