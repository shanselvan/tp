package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;

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
 * Marks an order as unpaid in HomeChef.
 */
public class UnpaidCommand extends Command {

    public static final String COMMAND_WORD = "unpaid";

    public static final String MESSAGE_MARK_UNPAID_SUCCESS = "Marked order as unpaid: %1$s";
    public static final String MESSAGE_ALREADY_UNPAID = "Order is already marked as unpaid.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks as unpaid the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index targetIndex;

    public UnpaidCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToMarkUnpaid = lastShownList.get(targetIndex.getZeroBased());
        if (orderToMarkUnpaid.getPaymentStatus() == PaymentStatus.UNPAID) {
            throw new CommandException(MESSAGE_ALREADY_UNPAID);
        }
        Order unpaidOrder = createUnpaidOrder(orderToMarkUnpaid);

        model.setOrder(orderToMarkUnpaid, unpaidOrder);
        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);

        return new CommandResult(generateSuccessMessage(unpaidOrder));
    }

    /**
     * Creates and returns an {@code Order} with the details of {@code orderToMarkUnpaid}
     * marking {@code PaymentStatus} as unpaid.
     */
    private static Order createUnpaidOrder(Order orderToMarkUnpaid) {
        assert orderToMarkUnpaid != null;

        Food food = orderToMarkUnpaid.getFood();
        Customer customer = orderToMarkUnpaid.getCustomer();
        Phone phone = orderToMarkUnpaid.getPhone();
        Email email = orderToMarkUnpaid.getEmail();
        Address address = orderToMarkUnpaid.getAddress();
        Date date = orderToMarkUnpaid.getDate();
        CompletionStatus completionStatus = orderToMarkUnpaid.getCompletionStatus();
        PaymentStatus updatedPaymentStatus = PaymentStatus.UNPAID;
        Set<DietTag> dietTags = orderToMarkUnpaid.getTags();
        Quantity quantity = orderToMarkUnpaid.getQuantity();
        Price price = orderToMarkUnpaid.getPrice();
        Optional<PaymentInfo> paymentInfo = orderToMarkUnpaid.getPaymentInfo();

        return new Order(food, customer, phone, email, address, date,
                completionStatus, updatedPaymentStatus, dietTags, quantity, price, paymentInfo);
    }

    /**
     * Generates a command execution success message when an order is marked as unpaid.
     */
    private String generateSuccessMessage(Order order) {
        return String.format(MESSAGE_MARK_UNPAID_SUCCESS, Messages.format(order));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnpaidCommand)) {
            return false;
        }
        UnpaidCommand otherCommand = (UnpaidCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
