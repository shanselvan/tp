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
 * Marks an order as paid for in the HomeChef.
 */
public class PaidCommand extends Command {

    public static final String COMMAND_WORD = "paid";

    public static final String MESSAGE_MARK_PAID_SUCCESS = "Marked order as paid: %1$s";
    public static final String MESSAGE_ALREADY_PAID = "Order is already marked as paid.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks as paid the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index targetIndex;

    public PaidCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToMarkPaid = lastShownList.get(targetIndex.getZeroBased());
        if (orderToMarkPaid.getPaymentStatus().isPaid()) {
            throw new CommandException(MESSAGE_ALREADY_PAID);
        }
        Order paidOrder = createPaidOrder(orderToMarkPaid);

        model.setOrder(orderToMarkPaid, paidOrder);
        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);

        return new CommandResult(generateSuccessMessage(paidOrder));
    }

    /**
     * Creates and returns an {@code Order} with the details of {@code orderToMarkPaid}
     * marking {@code PaymentStatus} as paid.
     */
    private static Order createPaidOrder(Order orderToMarkPaid) {
        assert orderToMarkPaid != null;

        Food food = orderToMarkPaid.getFood();
        Customer customer = orderToMarkPaid.getCustomer();
        Phone phone = orderToMarkPaid.getPhone();
        Email email = orderToMarkPaid.getEmail();
        Address address = orderToMarkPaid.getAddress();
        Date date = orderToMarkPaid.getDate();
        CompletionStatus completionStatus = orderToMarkPaid.getCompletionStatus();
        PaymentStatus updatedPaymentStatus = PaymentStatus.PAID;
        Set<DietTag> dietTags = orderToMarkPaid.getTags();
        Quantity quantity = orderToMarkPaid.getQuantity();
        Price price = orderToMarkPaid.getPrice();
        Optional<PaymentInfo> paymentInfo = orderToMarkPaid.getPaymentInfo();

        return new Order(food, customer, phone, email, address, date,
                completionStatus, updatedPaymentStatus, dietTags, quantity, price, paymentInfo);
    }

    /**
     * Generates a command execution success message when an order is marked as paid.
     */
    private String generateSuccessMessage(Order order) {
        return String.format(MESSAGE_MARK_PAID_SUCCESS, Messages.format(order));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PaidCommand)) {
            return false;
        }

        PaidCommand otherPaidCommand = (PaidCommand) other;
        return targetIndex.equals(otherPaidCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
