package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_BANK_NAME;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYMENT_METHOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYMENT_REF;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_WALLET_PROVIDER;
import static seedu.homechef.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.CollectionUtil;
import seedu.homechef.commons.util.StringUtil;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.menu.MenuItem;
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
import seedu.homechef.model.tag.DietTag;

/**
 * Edits the details of an existing order in the HomeChef.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the order identified "
            + "by the index number used in the displayed order list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_FOOD + "FOOD] "
            + "[" + PREFIX_CUSTOMER + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_PAYMENT_METHOD + "PAYMENT_METHOD] "
            + "[" + PREFIX_PAYMENT_REF + "PAYMENT_REF] "
            + "[" + PREFIX_BANK_NAME + "BANK_NAME] "
            + "[" + PREFIX_WALLET_PROVIDER + "WALLET_PROVIDER] "
            + "\nExample: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Order: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists in the HomeChef.";

    private final Index index;
    private final EditOrderDescriptor editOrderDescriptor;

    /**
     * @param index               of the order in the filtered order list to edit
     * @param editOrderDescriptor details to edit the order with
     */
    public EditCommand(Index index, EditOrderDescriptor editOrderDescriptor) {
        requireNonNull(index);
        requireNonNull(editOrderDescriptor);

        this.index = index;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToEdit = lastShownList.get(index.getZeroBased());
        Order editedOrder = createEditedOrder(orderToEdit, editOrderDescriptor);

        if (!orderToEdit.isSameOrder(editedOrder) && model.hasOrder(editedOrder)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        if (editOrderDescriptor.getFood().isPresent()) {
            String newFoodName = editedOrder.getFood().foodName;
            Optional<MenuItem> matchingItem = model.getMenuBook().getMenuItemList().stream()
                    .filter(item -> item.getName().fullName.equalsIgnoreCase(newFoodName))
                    .findFirst();

            if (matchingItem.isPresent()) {
                if (!matchingItem.get().isAvailable()) {
                    throw new CommandException(String.format(
                            "'%s' is currently unavailable. Check the menu panel on the right for available items.",
                            newFoodName));
                }
            } else {
                List<String> menuNames = model.getMenuBook().getMenuItemList().stream()
                        .map(item -> item.getName().fullName)
                        .collect(Collectors.toList());
                Optional<String> suggestion = StringUtil.findClosestMatch(newFoodName, menuNames, 2);
                if (suggestion.isPresent()) {
                    throw new CommandException(String.format(
                            "No menu item '%s'. Did you mean '%s'? "
                            + "Check the menu panel on the right for all items.",
                            newFoodName, suggestion.get()));
                } else {
                    throw new CommandException(String.format(
                            "No menu item '%s'. Use 'add-menu' to add it to the menu first.",
                            newFoodName));
                }
            }
        }

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(String.format(MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder)));
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit, EditOrderDescriptor editOrderDescriptor) {
        assert orderToEdit != null;

        Food updatedFood = editOrderDescriptor.getFood().orElse(orderToEdit.getFood());
        Customer updatedCustomer = editOrderDescriptor.getCustomer().orElse(orderToEdit.getCustomer());
        Phone updatedPhone = editOrderDescriptor.getPhone().orElse(orderToEdit.getPhone());
        Email updatedEmail = editOrderDescriptor.getEmail().orElse(orderToEdit.getEmail());
        Address updatedAddress = editOrderDescriptor.getAddress().orElse(orderToEdit.getAddress());
        Date updatedDate = editOrderDescriptor.getDate().orElse(orderToEdit.getDate());
        CompletionStatus updatedCompletionStatus = orderToEdit.getCompletionStatus();
        PaymentStatus updatedPaymentStatus = orderToEdit.getPaymentStatus();
        Set<DietTag> updatedDietTags = editOrderDescriptor.getTags().orElse(orderToEdit.getTags());
        Optional<PaymentInfo> updatedPaymentInfo = editOrderDescriptor.getPaymentInfo().isPresent()
                                                   ? editOrderDescriptor.getPaymentInfo()
                                                   : orderToEdit.getPaymentInfo();

        return new Order(updatedFood, updatedCustomer, updatedPhone, updatedEmail, updatedAddress, updatedDate,
                updatedCompletionStatus, updatedPaymentStatus, updatedDietTags, updatedPaymentInfo);
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
                && editOrderDescriptor.equals(otherEditCommand.editOrderDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editOrderDescriptor", editOrderDescriptor)
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
        private CompletionStatus completionStatus;
        private PaymentStatus paymentStatus;
        private Set<DietTag> dietTags;
        private PaymentInfo paymentInfo;

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
            setCompletionStatus(toCopy.completionStatus);
            setPaymentStatus(toCopy.paymentStatus);
            setTags(toCopy.dietTags);
            setPaymentInfo(toCopy.paymentInfo);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(food, customer, phone, email, address, date, dietTags, paymentInfo);
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

        public void setCompletionStatus(CompletionStatus completionStatus) {
            this.completionStatus = completionStatus;
        }

        public Optional<CompletionStatus> getCompletionStatus() {
            return Optional.ofNullable(completionStatus);
        }

        public void setPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public Optional<PaymentStatus> getPaymentStatus() {
            return Optional.ofNullable(paymentStatus);
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
        }

        /**
         * Returns the payment info to update the order with, or empty if not edited.
         */
        public Optional<PaymentInfo> getPaymentInfo() {
            return Optional.ofNullable(paymentInfo);
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
                    && Objects.equals(completionStatus, otherEditOrderDescriptor.completionStatus)
                    && Objects.equals(paymentStatus, otherEditOrderDescriptor.paymentStatus)
                    && Objects.equals(dietTags, otherEditOrderDescriptor.dietTags)
                    && Objects.equals(paymentInfo, otherEditOrderDescriptor.paymentInfo);
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
                    .add("completionStatus", completionStatus)
                    .add("paymentStatus", paymentStatus)
                    .add("dietTags", dietTags)
                    .add("paymentInfo", paymentInfo)
                    .toString();
        }
    }
}
