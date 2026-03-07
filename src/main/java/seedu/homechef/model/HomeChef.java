package seedu.homechef.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.UniqueOrderList;

/**
 * Wraps all data at the homechef level
 * Duplicates are not allowed (by .isSameOrder comparison)
 */
public class HomeChef implements ReadOnlyHomeChef {

    private final UniqueOrderList orders;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        orders = new UniqueOrderList();
    }

    public HomeChef() {}

    /**
     * Creates an HomeChef using the Orders in the {@code toBeCopied}
     */
    public HomeChef(ReadOnlyHomeChef toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the order list with {@code orders}.
     * {@code orders} must not contain duplicate orders.
     */
    public void setOrders(List<Order> orders) {
        this.orders.setOrders(orders);
    }

    /**
     * Resets the existing data of this {@code HomeChef} with {@code newData}.
     */
    public void resetData(ReadOnlyHomeChef newData) {
        requireNonNull(newData);

        setOrders(newData.getOrderList());
    }

    //// order-level operations

    /**
     * Returns true if a order with the same identity as {@code order} exists in the HomeChef.
     */
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orders.contains(order);
    }

    /**
     * Adds a order to the HomeChef.
     * The order must not already exist in the HomeChef.
     */
    public void addOrder(Order p) {
        orders.add(p);
    }

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the HomeChef.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the HomeChef.
     */
    public void setOrder(Order target, Order editedOrder) {
        requireNonNull(editedOrder);

        orders.setOrder(target, editedOrder);
    }

    /**
     * Removes {@code key} from this {@code HomeChef}.
     * {@code key} must exist in the HomeChef.
     */
    public void removeOrder(Order key) {
        orders.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("orders", orders)
                .toString();
    }

    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HomeChef)) {
            return false;
        }

        HomeChef otherHomeChef = (HomeChef) other;
        return orders.equals(otherHomeChef.orders);
    }

    @Override
    public int hashCode() {
        return orders.hashCode();
    }
}
