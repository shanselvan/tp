package seedu.homechef.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.order.Order;

/**
 * An Immutable HomeChef that is serializable to JSON format.
 */
@JsonRootName(value = "homechef")
class JsonSerializableHomeChef {

    public static final String MESSAGE_DUPLICATE_ORDER = "Orders list contains duplicate order(s).";
    public static final String MESSAGE_MISSING_ORDERS_LIST = "Missing required field: orders";
    public static final String MESSAGE_INVALID_ORDER_ELEMENT = "Orders list contains invalid order entries.";

    private final List<JsonAdaptedOrder> orders = new ArrayList<>();
    private final boolean isOrdersFieldMissing;

    /**
     * Constructs a {@code JsonSerializableHomeChef} with the given orders.
     */
    @JsonCreator
    public JsonSerializableHomeChef(@JsonProperty("orders") List<JsonAdaptedOrder> orders) {
        this.isOrdersFieldMissing = (orders == null);
        if (orders != null) {
            this.orders.addAll(orders);
        }
    }

    /**
     * Converts a given {@code ReadOnlyHomeChef} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableHomeChef}.
     */
    public JsonSerializableHomeChef(ReadOnlyHomeChef source) {
        isOrdersFieldMissing = false;
        orders.addAll(source.getOrderList().stream().map(JsonAdaptedOrder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this HomeChef into the model's {@code HomeChef} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public HomeChef toModelType() throws IllegalValueException {
        if (isOrdersFieldMissing) {
            throw new IllegalValueException(MESSAGE_MISSING_ORDERS_LIST);
        }
        HomeChef homeChef = new HomeChef();
        for (JsonAdaptedOrder jsonAdaptedOrder : orders) {
            if (jsonAdaptedOrder == null) {
                throw new IllegalValueException(MESSAGE_INVALID_ORDER_ELEMENT);
            }
            Order order = jsonAdaptedOrder.toModelType();
            if (homeChef.hasOrder(order)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ORDER);
            }
            homeChef.addOrder(order);
        }
        return homeChef;
    }

}
