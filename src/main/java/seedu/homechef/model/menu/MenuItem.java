package seedu.homechef.model.menu;

import static seedu.homechef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;

/**
 * Represents a MenuItem in the menu.
 * Guarantees: details are present and not null; field values are validated and immutable.
 */
public class MenuItem {

    private final Food food;
    private final Price price;
    private final Availability availability;

    /**
     * Creates a {@code MenuItem} with the given food, price, and availability.
     * All fields must be present and not null.
     *
     * @param food         The name of the menu item.
     * @param price        The price of the menu item.
     * @param availability The availability status of the menu item.
     */
    public MenuItem(Food food, Price price, Availability availability) {
        requireAllNonNull(food, price, availability);
        this.food = food;
        this.price = price;
        this.availability = availability;
    }

    public Food getFood() {
        return food;
    }

    public Price getPrice() {
        return price;
    }

    public Availability getAvailability() {
        return availability;
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
                && availability == otherItem.availability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(food, price, availability);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", food)
                .add("price", price)
                .add("availability", availability)
                .toString();
    }
}
