package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.homechef.model.Model;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Order;

/**
 * Lists all orders in Homechef to the user, ordered by fulfillment date.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all orders";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists orders, optionally filtered by fulfillment date.\n"
            + "Format: " + COMMAND_WORD + " [f/FOOD] [c/CUSTOMER] [d/DATE] [p/PHONE]\n"
            + "DATE must be in the format dd-MM-yyyy.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " d/18-10-2026\n"
            + "Example: " + COMMAND_WORD + " c/alice\n"
            + "Example: " + COMMAND_WORD + " f/cake\n"
            + "Example: " + COMMAND_WORD + " p/1234\n"
            + "Example: " + COMMAND_WORD + " d/16-04-2003 c/alice f/cake p/1234";

    private final ListFilterDescriptor descriptor;

    /**
     * Creates a ListCommand that lists all orders without filtering.
     */
    public ListCommand() {
        this(new ListFilterDescriptor());
    }

    /**
     * Creates a ListCommand that filters orders by the given date.
     *
     * @param targetDate the date to filter orders by
     */
    public ListCommand(Date targetDate) {
        requireNonNull(targetDate);
        ListFilterDescriptor d = new ListFilterDescriptor();
        d.setDate(targetDate);
        this.descriptor = d;
    }

    /**
     * Creates a ListCommand that filters orders based on the given descriptor.
     */
    public ListCommand(ListFilterDescriptor descriptor) {
        requireNonNull(descriptor);
        this.descriptor = descriptor;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Order> predicate = PREDICATE_SHOW_ALL_ORDERS;

        if (descriptor.getDate().isPresent()) {
            Date date = descriptor.getDate().get();
            predicate = predicate.and(order -> order.getDate().equals(date));
        }

        if (descriptor.getCustomerQuery().isPresent()) {
            String query = descriptor.getCustomerQuery().get().toLowerCase();
            predicate = predicate.and(order ->
                    order.getCustomer().toString().toLowerCase().contains(query));
        }

        if (descriptor.getFoodQuery().isPresent()) {
            String query = descriptor.getFoodQuery().get().toLowerCase();
            predicate = predicate.and(order ->
                    order.getFood().toString().toLowerCase().contains(query));
        }

        if (descriptor.getPhoneQuery().isPresent()) {
            String query = descriptor.getPhoneQuery().get().toLowerCase();
            predicate = predicate.and(order ->
                    order.getPhone().toString().toLowerCase().contains(query));
        }

        model.updateFilteredOrderList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherListCommand = (ListCommand) other;
        return Objects.equals(descriptor, otherListCommand.descriptor);
    }

    /**
     * Stores the details to filter the order list with. Each non-empty field value will be used
     * as a filter criterion.
     */
    public static class ListFilterDescriptor {
        private Date date;
        private String customerQuery;
        private String foodQuery;
        private String phoneQuery;

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public Optional<String> getCustomerQuery() {
            return Optional.ofNullable(customerQuery);
        }

        public Optional<String> getFoodQuery() {
            return Optional.ofNullable(foodQuery);
        }

        public Optional<String> getPhoneQuery() {
            return Optional.ofNullable(phoneQuery);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public void setCustomerQuery(String customerQuery) {
            this.customerQuery = customerQuery;
        }

        public void setFoodQuery(String foodQuery) {
            this.foodQuery = foodQuery;
        }

        public void setPhoneQuery(String phoneQuery) {
            this.phoneQuery = phoneQuery;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof ListFilterDescriptor)) {
                return false;
            }

            ListFilterDescriptor otherDescriptor = (ListFilterDescriptor) other;
            return Objects.equals(date, otherDescriptor.date)
                    && Objects.equals(customerQuery, otherDescriptor.customerQuery)
                    && Objects.equals(foodQuery, otherDescriptor.foodQuery)
                    && Objects.equals(phoneQuery, otherDescriptor.phoneQuery);
        }
    }
}
