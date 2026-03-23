package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentStatus;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Price;
import seedu.homechef.model.tag.DietTag;

/**
 * Marks an order identified using it's displayed index from the HomeChef as completed.
 */
public class MarkCompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the order identified by the index number used in the displayed order list as complete.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_ORDER_SUCCESS = "Marked Order as Complete: %1$s";

    private final Index targetIndex;

    public MarkCompleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToMarkComplete = lastShownList.get(targetIndex.getZeroBased());
        Order completedOrder = createCompletedOrder(orderToMarkComplete);

        model.setOrder(orderToMarkComplete, completedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(String.format(MESSAGE_COMPLETE_ORDER_SUCCESS, Messages.format(completedOrder)));
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToMarkComplete}
     * marking {@code CompletionStatus} to completed.
     */
    private static Order createCompletedOrder(Order orderToMarkComplete) {
        assert orderToMarkComplete != null;

        Food food = orderToMarkComplete.getFood();
        Customer customer = orderToMarkComplete.getCustomer();
        Phone phone = orderToMarkComplete.getPhone();
        Email email = orderToMarkComplete.getEmail();
        Address address = orderToMarkComplete.getAddress();
        Date date = orderToMarkComplete.getDate();
        CompletionStatus updatedCompletionStatus = CompletionStatus.COMPLETED;
        PaymentStatus paymentStatus = orderToMarkComplete.getPaymentStatus();
        Set<DietTag> dietTags = orderToMarkComplete.getTags();
        Price price = orderToMarkComplete.getPrice();
        Optional<PaymentInfo> paymentInfo = orderToMarkComplete.getPaymentInfo();

        return new Order(food, customer, phone,
                email, address, date, updatedCompletionStatus, paymentStatus, dietTags, price, paymentInfo);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCompleteCommand)) {
            return false;
        }

        MarkCompleteCommand otherMarkCompleteCommand = (MarkCompleteCommand) other;
        return targetIndex.equals(otherMarkCompleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
