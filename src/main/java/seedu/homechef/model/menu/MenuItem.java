package seedu.homechef.model.menu;

import static seedu.homechef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.model.common.Food;

/**
 * Represents a MenuItem in the menu.
 * Guarantees: details are present and not null; field values are validated and immutable.
 */
public class MenuItem {

    private final Food food;
    private final Price price;
    private final boolean available;

    /**
     * All fields must be present and not null.
     */
    public MenuItem(Food food, Price price, boolean available) {
        requireAllNonNull(food, price);
        this.food = food;
        this.price = price;
        this.available = available;
    }

    public Food getFood() {
        return food;
    }

    public Price getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    /**
     * Returns true if both menu items have the same name (case-insensitive).
     * This defines a weaker notion of equality between two menu items.
     */
    public boolean isSameMenuItem(MenuItem other) {
        if (other == this) {
            return true;
        }
        return other != null
                && other.getFood().toString().equalsIgnoreCase(food.toString());
    }

    /**
     * Returns true if both menu items have the same name, price, and availability.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MenuItem)) {
            return false;
        }
        MenuItem otherItem = (MenuItem) other;
        return food.equals(otherItem.food)
                && price.equals(otherItem.price)
                && available == otherItem.available;
    }

    @Override
    public int hashCode() {
        return Objects.hash(food, price, available);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", food)
                .add("price", price)
                .add("available", available)
                .toString();
    }
}
