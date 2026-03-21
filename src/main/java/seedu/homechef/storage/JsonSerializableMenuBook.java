package seedu.homechef.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.ReadOnlyMenuBook;

/**
 * An Immutable MenuBook that is serializable to JSON format.
 */
@JsonRootName(value = "menubook")
class JsonSerializableMenuBook {

    public static final String MESSAGE_DUPLICATE_MENU_ITEM =
            "Menu items list contains duplicate menu item(s).";

    private final List<JsonAdaptedMenuItem> menuItems = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMenuBook} with the given menu items.
     */
    @JsonCreator
    public JsonSerializableMenuBook(@JsonProperty("menuItems") List<JsonAdaptedMenuItem> menuItems) {
        this.menuItems.addAll(menuItems);
    }

    /**
     * Converts a given {@code ReadOnlyMenuBook} into this class for Jackson use.
     */
    public JsonSerializableMenuBook(ReadOnlyMenuBook source) {
        menuItems.addAll(source.getMenuItemList().stream()
                .map(JsonAdaptedMenuItem::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this menu book into the model's {@code MenuBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MenuBook toModelType() throws IllegalValueException {
        MenuBook menuBook = new MenuBook();
        for (JsonAdaptedMenuItem jsonAdaptedMenuItem : menuItems) {
            MenuItem menuItem = jsonAdaptedMenuItem.toModelType();
            if (menuBook.hasMenuItem(menuItem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MENU_ITEM);
            }
            menuBook.addMenuItem(menuItem);
        }
        return menuBook;
    }
}
