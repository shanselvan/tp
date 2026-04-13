package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_BANK_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CASH_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYNOW_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_TAG;

import static seedu.homechef.logic.Messages.MESSAGE_DUPLICATE_ORDER;

import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.Quantity;

/**
 * Adds a order to the HomeChef.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a order to HomeChef. "
            + "Parameters: "
            + PREFIX_FOOD + "FOOD "
            + PREFIX_CUSTOMER + "CUSTOMER "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_BANK_PAYMENT + "BANK_DETAILS] "
            + "[" + PREFIX_PAYNOW_PAYMENT + "PAYNOW_CONTACT] "
            + "[" + PREFIX_CASH_PAYMENT + "YES_OR_NO] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FOOD + "Birthday Cake "
            + PREFIX_CUSTOMER + "John "
            + PREFIX_PHONE + "1234 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "NUS "
            + PREFIX_DATE + "20-10-2026 "
            + PREFIX_TAG + "dairyfree "
            + PREFIX_BANK_PAYMENT + "DBS-123456";

    public static final String MESSAGE_SUCCESS = "New order added: %1$s";
    public static final String MESSAGE_PAST_DATE_WARNING =
            " Warning: this order date is in the past and will be marked as overdue.";

    private final Order toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Order}
     */
    public AddCommand(Order order) {
        requireNonNull(order);
        toAdd = order;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String targetFoodName = toAdd.getFood().toString();
        MenuItem matchingItem = resolveAvailableMenuItem(model.getMenuBook(), targetFoodName);

        String canonicalName = matchingItem.getFood().toString();
        Price unitPrice = new Price(matchingItem.getPrice().toString());
        Quantity quantity = toAdd.getQuantity();
        Price totalPrice = unitPrice.multiply(quantity);
        Order orderToAdd = new Order(new Food(canonicalName), toAdd.getCustomer(), toAdd.getPhone(),
                toAdd.getEmail(), toAdd.getAddress(), toAdd.getDate(),
                toAdd.getCompletionStatus(), toAdd.getPaymentStatus(),
                toAdd.getTags(), quantity, totalPrice, toAdd.getPaymentInfo());

        if (model.hasOrder(orderToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        model.addOrder(orderToAdd);
        String feedback = String.format(MESSAGE_SUCCESS, Messages.format(orderToAdd));
        if (orderToAdd.getDate().isBeforeToday()) {
            feedback += MESSAGE_PAST_DATE_WARNING;
        }
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
