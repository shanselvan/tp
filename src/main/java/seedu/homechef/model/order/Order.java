package seedu.homechef.model.order;

import static seedu.homechef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;

/**
 * Represents a Order in the HomeChef.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {

    // Identity fields
    private final Food food;
    private final Customer customer;
    private final Date date;

    // Data fields
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final CompletionStatus completionStatus;
    private final PaymentStatus paymentStatus;
    private final Set<DietTag> dietTags = new HashSet<>();
    private final Quantity quantity;
    private final Price price;
    private final Optional<PaymentInfo> paymentInfo;

    /**
     * Every field must be present and not null. Quantity defaults to 1.
     */
    public Order(Food food, Customer customer, Phone phone, Email email, Address address, Date date,
                 CompletionStatus completionStatus, PaymentStatus paymentStatus, Set<DietTag> dietTags, Price price) {
        this(food, customer, phone, email, address, date,
                completionStatus, paymentStatus, dietTags, new Quantity(1), price, Optional.empty());
    }

    /**
     * Every field must be present and not null. {@code paymentInfo} may be empty. Quantity defaults to 1.
     */
    public Order(Food food, Customer customer, Phone phone, Email email, Address address, Date date,
                 CompletionStatus completionStatus, PaymentStatus paymentStatus, Set<DietTag> dietTags,
                 Price price, Optional<PaymentInfo> paymentInfo) {
        this(food, customer, phone, email, address, date,
                completionStatus, paymentStatus, dietTags, new Quantity(1), price, paymentInfo);
    }

    /**
     * Every field must be present and not null. {@code quantity} specifies the number of items ordered.
     */
    public Order(Food food, Customer customer, Phone phone, Email email, Address address, Date date,
                 CompletionStatus completionStatus, PaymentStatus paymentStatus, Set<DietTag> dietTags,
                 Quantity quantity, Price price) {
        this(food, customer, phone, email, address, date,
                completionStatus, paymentStatus, dietTags, quantity, price, Optional.empty());
    }

    /**
     * Every field must be present and not null. {@code paymentInfo} may be empty.
     * {@code quantity} specifies the number of items ordered.
     */
    public Order(Food food, Customer customer, Phone phone, Email email, Address address, Date date,
                 CompletionStatus completionStatus, PaymentStatus paymentStatus, Set<DietTag> dietTags,
                 Quantity quantity, Price price, Optional<PaymentInfo> paymentInfo) {
        requireAllNonNull(food, customer, phone, email, address, date,
                completionStatus, paymentStatus, dietTags, quantity, price, paymentInfo);
        this.food = food;
        this.customer = customer;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.date = date;
        this.completionStatus = completionStatus;
        this.paymentStatus = paymentStatus;
        this.dietTags.addAll(dietTags);
        this.quantity = quantity;
        this.price = price;
        this.paymentInfo = paymentInfo;
    }

    public Food getFood() {
        return food;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Date getDate() {
        return date;
    }

    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Returns the {@code Price} for this order.
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Returns the payment info for this order, or empty if not set.
     */
    public Optional<PaymentInfo> getPaymentInfo() {
        return paymentInfo;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<DietTag> getTags() {
        return Collections.unmodifiableSet(dietTags);
    }

    /**
     * Returns the {@code Quantity} for this order.
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Returns true if both orders have the same name, food and date.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getFood().equals(getFood())
                && otherOrder.getCustomer().equals(getCustomer())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getEmail().equals(getEmail())
                && otherOrder.getAddress().equals(getAddress())
                && otherOrder.getDate().equals(getDate())
                && otherOrder.getTags().equals(getTags())
                && otherOrder.getPrice().equals(getPrice())
                && otherOrder.getQuantity().equals(getQuantity());
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     * This defines a stronger notion of equality between two orders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return food.equals(otherOrder.food)
                && customer.equals(otherOrder.customer)
                && phone.equals(otherOrder.phone)
                && email.equals(otherOrder.email)
                && address.equals(otherOrder.address)
                && date.equals(otherOrder.date)
                && completionStatus.equals(otherOrder.completionStatus)
                && paymentStatus.equals(otherOrder.paymentStatus)
                && dietTags.equals(otherOrder.dietTags)
                && price.equals(otherOrder.price)
                && paymentInfo.equals(otherOrder.paymentInfo);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(food, customer, phone, email, address, date, completionStatus,
                paymentStatus, dietTags, price, paymentInfo);
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
                .add("price", price)
                .add("completionStatus", completionStatus)
                .add("paymentStatus", paymentStatus)
                .add("dietTags", dietTags)
                .add("paymentInfo", paymentInfo)
                .toString();
    }

}
