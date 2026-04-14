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
    public static final String MESSAGE_MISSING_MENU_ITEMS_LIST = "Missing required field: menuItems";
    public static final String MESSAGE_INVALID_MENU_ITEM_ELEMENT =
            "Menu items list contains invalid menu item entries.";

    private final List<JsonAdaptedMenuItem> menuItems = new ArrayList<>();
    private final boolean isMenuItemsFieldMissing;

    /**
     * Constructs a {@code JsonSerializableMenuBook} with the given menu items.
     */
    @JsonCreator
    public JsonSerializableMenuBook(@JsonProperty("menuItems") List<JsonAdaptedMenuItem> menuItems) {
        this.isMenuItemsFieldMissing = menuItems == null;
        if (menuItems != null) {
            this.menuItems.addAll(menuItems);
        }
    }

    /**
     * Converts a given {@code ReadOnlyMenuBook} into this class for Jackson use.
     */
    public JsonSerializableMenuBook(ReadOnlyMenuBook source) {
        isMenuItemsFieldMissing = false;
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
        if (isMenuItemsFieldMissing) {
            throw new IllegalValueException(MESSAGE_MISSING_MENU_ITEMS_LIST);
        }
        MenuBook menuBook = new MenuBook();
        for (JsonAdaptedMenuItem jsonAdaptedMenuItem : menuItems) {
            if (jsonAdaptedMenuItem == null) {
                throw new IllegalValueException(MESSAGE_INVALID_MENU_ITEM_ELEMENT);
            }
            MenuItem menuItem = jsonAdaptedMenuItem.toModelType();
            if (menuBook.hasMenuItem(menuItem)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MENU_ITEM);
            }
            menuBook.addMenuItem(menuItem);
        }
        return menuBook;
    }
}
