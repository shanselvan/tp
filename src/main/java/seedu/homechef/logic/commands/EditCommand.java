package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.logic.Messages.MESSAGE_DUPLICATE_ORDER;
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
import static seedu.homechef.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.CollectionUtil;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.MenuItem;
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
 * Edits the details of an existing order in the HomeChef.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the order identified "
            + "by the index number used in the displayed order list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a non-zero positive integer) "
            + "[" + PREFIX_FOOD + "FOOD] "
            + "[" + PREFIX_CUSTOMER + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_BANK_PAYMENT + "BANK_DETAILS] "
            + "[" + PREFIX_PAYNOW_PAYMENT + "PAYNOW_CONTACT] "
            + "[" + PREFIX_CASH_PAYMENT + "YES_OR_NO] "
            + "\nExample: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Order: %1$s";
    public static final String MESSAGE_NOT_EDITED =
            "At least one field to edit must be provided "
            + "(e.g. f/, c/, p/, e/, a/, d/, q/, t/, bank/, paynow/, or cash/).";
    public static final String MESSAGE_PAST_DATE_WARNING = AddCommand.MESSAGE_PAST_DATE_WARNING;

    private final Index index;
    private final EditOrderDescriptor descriptor;

    /**
     * @param index      of the order in the filtered order list to edit
     * @param descriptor details to edit the order with
     */
    public EditCommand(Index index, EditOrderDescriptor descriptor) {
        requireNonNull(index);
        requireNonNull(descriptor);

        this.index = index;
        this.descriptor = new EditOrderDescriptor(descriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToEdit = lastShownList.get(index.getZeroBased());
        Order editedOrder = createEditedOrder(orderToEdit, descriptor);

        if (descriptor.getFood().isPresent() || descriptor.getQuantity().isPresent()) {
            String targetFoodName = descriptor.getFood().orElse(orderToEdit.getFood()).toString();
            MenuItem matchingItem = resolveAvailableMenuItem(model.getMenuBook(), targetFoodName);
            String canonicalName = matchingItem.getFood().toString();
            Quantity newQuantity = editedOrder.getQuantity();
            Price totalPrice = new Price(matchingItem.getPrice().toString()).multiply(newQuantity);
            editedOrder = new Order(new Food(canonicalName), editedOrder.getCustomer(),
                    editedOrder.getPhone(), editedOrder.getEmail(), editedOrder.getAddress(),
                    editedOrder.getDate(), editedOrder.getCompletionStatus(),
                    editedOrder.getPaymentStatus(), editedOrder.getTags(),
                    newQuantity, totalPrice, editedOrder.getPaymentInfo());
        }

        if (!orderToEdit.isSameOrder(editedOrder) && model.hasOrder(editedOrder)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        String feedback = String.format(MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder));
        if (descriptor.getDate().isPresent() && editedOrder.getDate().isBeforeToday()) {
            feedback += MESSAGE_PAST_DATE_WARNING;
        }
        return new CommandResult(feedback);
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit, EditOrderDescriptor descriptor) {
        assert orderToEdit != null;

        Food updatedFood = descriptor.getFood().orElse(orderToEdit.getFood());
        Customer updatedCustomer = descriptor.getCustomer().orElse(orderToEdit.getCustomer());
        Phone updatedPhone = descriptor.getPhone().orElse(orderToEdit.getPhone());
        Email updatedEmail = descriptor.getEmail().orElse(orderToEdit.getEmail());
        Address updatedAddress = descriptor.getAddress().orElse(orderToEdit.getAddress());
        Date updatedDate = descriptor.getDate().orElse(orderToEdit.getDate());
        CompletionStatus updatedCompletionStatus = orderToEdit.getCompletionStatus();
        PaymentStatus updatedPaymentStatus = orderToEdit.getPaymentStatus();
        Quantity updatedQuantity = descriptor.getQuantity().orElse(orderToEdit.getQuantity());
        Price updatedPrice = orderToEdit.getPrice();
        Set<DietTag> updatedDietTags = descriptor.getTags().orElse(orderToEdit.getTags());
        Optional<PaymentInfo> updatedPaymentInfo = resolveUpdatedPaymentInfo(orderToEdit, descriptor);

        return new Order(updatedFood, updatedCustomer, updatedPhone, updatedEmail, updatedAddress, updatedDate,
                updatedCompletionStatus, updatedPaymentStatus, updatedDietTags,
                updatedQuantity, updatedPrice, updatedPaymentInfo);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && descriptor.equals(otherEditCommand.descriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editOrderDescriptor", descriptor)
                .toString();
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditOrderDescriptor {
        private Food food;
        private Customer customer;
        private Phone phone;
        private Email email;
        private Address address;
        private Date date;
        private Quantity quantity;
        private Set<DietTag> dietTags;
        private PaymentInfo paymentInfo;
        private boolean isPaymentInfoCleared;

        public EditOrderDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code dietTags} is used internally.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setFood(toCopy.food);
            setCustomer(toCopy.customer);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setDate(toCopy.date);
            setQuantity(toCopy.quantity);
            setTags(toCopy.dietTags);
            setPaymentInfo(toCopy.paymentInfo);
            if (toCopy.isPaymentInfoCleared) {
                clearPaymentInfo();
            }
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(food, customer, phone, email, address,
                    date, quantity, dietTags, paymentInfo) || isPaymentInfoCleared;
        }

        public void setFood(Food food) {
            this.food = food;
        }

        public Optional<Food> getFood() {
            return Optional.ofNullable(food);
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public Optional<Customer> getCustomer() {
            return Optional.ofNullable(customer);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        /**
         * Sets the quantity of items ordered.
         */
        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        /**
         * Returns the quantity if set, or empty if not edited.
         */
        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        /**
         * Sets {@code dietTags} to this object's {@code dietTags}.
         * A defensive copy of {@code dietTags} is used internally.
         */
        public void setTags(Set<DietTag> dietTags) {
            this.dietTags = (dietTags != null) ? new HashSet<>(dietTags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code dietTags} is null.
         */
        public Optional<Set<DietTag>> getTags() {
            return (dietTags != null) ? Optional.of(Collections.unmodifiableSet(dietTags)) : Optional.empty();
        }

        /**
         * Sets the payment info to update the order with.
         */
        public void setPaymentInfo(PaymentInfo paymentInfo) {
            this.paymentInfo = paymentInfo;
            this.isPaymentInfoCleared = false;
        }

        /**
         * Clears the payment info from the order.
         */
        public void clearPaymentInfo() {
            this.paymentInfo = null;
            this.isPaymentInfoCleared = true;
        }

        /**
         * Returns the payment info to update the order with, or empty if not edited.
         */
        public Optional<PaymentInfo> getPaymentInfo() {
            return Optional.ofNullable(paymentInfo);
        }

        /**
         * Returns true if payment info should be cleared from the order.
         */
        public boolean isPaymentInfoCleared() {
            return isPaymentInfoCleared;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            EditOrderDescriptor otherEditOrderDescriptor = (EditOrderDescriptor) other;
            return Objects.equals(food, otherEditOrderDescriptor.food)
                    && Objects.equals(customer, otherEditOrderDescriptor.customer)
                    && Objects.equals(phone, otherEditOrderDescriptor.phone)
                    && Objects.equals(email, otherEditOrderDescriptor.email)
                    && Objects.equals(address, otherEditOrderDescriptor.address)
                    && Objects.equals(date, otherEditOrderDescriptor.date)
                    && Objects.equals(quantity, otherEditOrderDescriptor.quantity)
                    && Objects.equals(dietTags, otherEditOrderDescriptor.dietTags)
                    && Objects.equals(paymentInfo, otherEditOrderDescriptor.paymentInfo)
                    && isPaymentInfoCleared == otherEditOrderDescriptor.isPaymentInfoCleared;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("food", food)
                    .add("customer", customer)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("date", date)
                    .add("quantity", quantity)
                    .add("dietTags", dietTags)
                    .add("paymentInfo", paymentInfo)
                    .add("isPaymentInfoCleared", isPaymentInfoCleared)
                    .toString();
        }
    }

    private static Optional<PaymentInfo> resolveUpdatedPaymentInfo(Order orderToEdit, EditOrderDescriptor descriptor) {
        if (descriptor.isPaymentInfoCleared()) {
            return Optional.empty();
        }
        return descriptor.getPaymentInfo().isPresent()
                ? descriptor.getPaymentInfo()
                : orderToEdit.getPaymentInfo();
    }
}
