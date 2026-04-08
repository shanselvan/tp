package seedu.homechef.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.menu.MenuItem;

/**
 * Jackson-friendly version of {@link MenuItem}.
 */
class JsonAdaptedMenuItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "MenuItem's %s field is missing!";

    private final String name;
    private final String price;
    private final String availability;

    /**
     * Constructs a {@code JsonAdaptedMenuItem} with the given menu item details.
     */
    @JsonCreator
    public JsonAdaptedMenuItem(@JsonProperty("name") String name,
                               @JsonProperty("price") String price,
                               @JsonProperty("availability") String availability) {
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    /**
     * Converts a given {@code MenuItem} into this class for Jackson use.
     */
    public JsonAdaptedMenuItem(MenuItem source) {
        name = source.getFood().toString();
        price = source.getPrice().toString();
        availability = source.getAvailability().toInputValue();
    }

    /**
     * Converts this Jackson-friendly adapted menu item object into the model's {@code MenuItem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted menu item.
     */
    public MenuItem toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Food.class.getSimpleName()));
        }
        if (!Food.isValidFood(name)) {
            throw new IllegalValueException(Food.MESSAGE_CONSTRAINTS);
        }

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }

        if (availability == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Availability.class.getSimpleName()));
        }

        final Availability modelAvailability;
        try {
            modelAvailability = Availability.fromString(availability);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid availability value: " + availability);
        }

        return new MenuItem(new Food(name), new Price(price), modelAvailability);
    }
}
