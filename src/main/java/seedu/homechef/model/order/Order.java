package seedu.homechef.model.order;

import static seedu.homechef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.model.tag.DietTag;

/**
 * Represents a Order in the HomeChef.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {

    // Identity fields
    private final Food food;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Date date;
    private final Set<DietTag> dietTags = new HashSet<>();
    private final Optional<PaymentInfo> paymentInfo;

    /**
     * Every field must be present and not null.
     */
    public Order(Food food, Name name, Phone phone, Email email, Address address, Date date, Set<DietTag> dietTags) {
        this(food, name, phone, email, address, date, dietTags, Optional.empty());
    }

    /**
     * Every field must be present and not null. {@code paymentInfo} may be empty.
     */
    public Order(Food food, Name name, Phone phone, Email email, Address address, Date date,
                 Set<DietTag> dietTags, Optional<PaymentInfo> paymentInfo) {
        requireAllNonNull(food, name, phone, email, address, date, dietTags, paymentInfo);
        this.food = food;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.date = date;
        this.dietTags.addAll(dietTags);
        this.paymentInfo = paymentInfo;
    }

    public Food getFood() {
        return food;
    }

    public Name getName() {
        return name;
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
     * Returns true if both orders have the same name, food and date.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getName().equals(getName())
                && otherOrder.getFood().equals(getFood())
                && otherOrder.getDate().equals(getDate());
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
                && name.equals(otherOrder.name)
                && phone.equals(otherOrder.phone)
                && email.equals(otherOrder.email)
                && address.equals(otherOrder.address)
                && date.equals(otherOrder.date)
                && dietTags.equals(otherOrder.dietTags)
                && paymentInfo.equals(otherOrder.paymentInfo);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(food, name, phone, email, address, date, dietTags, paymentInfo);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("food", food)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("date", date)
                .add("dietTags", dietTags)
                .add("paymentInfo", paymentInfo)
                .toString();
    }

}
