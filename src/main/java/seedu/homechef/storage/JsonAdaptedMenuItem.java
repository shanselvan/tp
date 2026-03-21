package seedu.homechef.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.MenuItemName;
import seedu.homechef.model.menu.Price;

/**
 * Jackson-friendly version of {@link MenuItem}.
 */
class JsonAdaptedMenuItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "MenuItem's %s field is missing!";

    private final String name;
    private final String price;
    private final Boolean available;

    /**
     * Constructs a {@code JsonAdaptedMenuItem} with the given menu item details.
     */
    @JsonCreator
    public JsonAdaptedMenuItem(@JsonProperty("name") String name,
                               @JsonProperty("price") String price,
                               @JsonProperty("available") Boolean available) {
        this.name = name;
        this.price = price;
        this.available = available;
    }

    /**
     * Converts a given {@code MenuItem} into this class for Jackson use.
     */
    public JsonAdaptedMenuItem(MenuItem source) {
        name = source.getName().fullName;
        price = source.getPrice().value;
        available = source.isAvailable();
    }

    /**
     * Converts this Jackson-friendly adapted menu item object into the model's {@code MenuItem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted menu item.
     */
    public MenuItem toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MenuItemName.class.getSimpleName()));
        }
        if (!MenuItemName.isValidMenuItemName(name)) {
            throw new IllegalValueException(MenuItemName.MESSAGE_CONSTRAINTS);
        }

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }

        boolean isAvailable = available == null ? true : available;
        return new MenuItem(new MenuItemName(name), new Price(price), isAvailable);
    }
}
