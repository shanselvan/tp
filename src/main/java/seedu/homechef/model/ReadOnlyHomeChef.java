package seedu.homechef.model;

import javafx.collections.ObservableList;
import seedu.homechef.model.order.Order;

/**
 * Unmodifiable view of an HomeChef
 */
public interface ReadOnlyHomeChef {

    /**
     * Returns an unmodifiable view of the orders list.
     * This list will not contain any duplicate orders.
     */
    ObservableList<Order> getOrderList();

}
