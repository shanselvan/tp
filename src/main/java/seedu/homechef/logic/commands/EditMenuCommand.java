package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.logic.Messages.MESSAGE_DUPLICATE_MENU_ITEM;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.CollectionUtil;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.menu.Availability;
import seedu.homechef.model.menu.MenuItem;

/**
 * Edits the details of an existing menu item.
 */
public class EditMenuCommand extends Command {

    public static final String COMMAND_WORD = "edit-menu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the menu item identified "
            + "by the index number used in the displayed menu list. "
            + "At least one field must be provided.\n"
            + "Parameters: INDEX (must be a non-zero positive integer) "
            + "[" + PREFIX_FOOD + "NAME] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_AVAILABILITY + "AVAILABILITY]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PRICE + "6.00 " + PREFIX_AVAILABILITY + "no";

    public static final String MESSAGE_EDIT_MENU_ITEM_SUCCESS = "Edited menu item: %1$s $%2$s (availability: %3$s)";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditMenuDescriptor editMenuDescriptor;

    /**
     * @param index              of the menu item in the filtered menu list to edit
     * @param editMenuDescriptor details to edit the menu item with
     */
    public EditMenuCommand(Index index, EditMenuDescriptor editMenuDescriptor) {
        requireNonNull(index);
        requireNonNull(editMenuDescriptor);
        this.index = index;
        this.editMenuDescriptor = new EditMenuDescriptor(editMenuDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<MenuItem> lastShownList = model.getFilteredMenuItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX);
        }

        MenuItem menuItemToEdit = lastShownList.get(index.getZeroBased());
        MenuItem editedMenuItem = createEditedMenuItem(menuItemToEdit, editMenuDescriptor);

        if (!menuItemToEdit.isSameMenuItem(editedMenuItem) && model.hasMenuItem(editedMenuItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_MENU_ITEM);
        }

        model.setMenuItem(menuItemToEdit, editedMenuItem);
        return new CommandResult(String.format(MESSAGE_EDIT_MENU_ITEM_SUCCESS,
                editedMenuItem.getFood(),
                editedMenuItem.getPrice().toString(),
                editedMenuItem.getAvailability()));
    }

    /**
     * Creates and returns a {@code MenuItem} with the details of {@code menuItemToEdit}
     * edited with {@code editMenuDescriptor}.
     */
    private static MenuItem createEditedMenuItem(MenuItem menuItemToEdit,
                                                 EditMenuDescriptor editMenuDescriptor) {
        assert menuItemToEdit != null;

        Food updatedName = editMenuDescriptor.getName().orElse(menuItemToEdit.getFood());
        Price updatedPrice = editMenuDescriptor.getPrice().orElse(menuItemToEdit.getPrice());
        Availability updatedAvailability = editMenuDescriptor.getAvailability().orElse(
                menuItemToEdit.getAvailability());
        return new MenuItem(updatedName, updatedPrice, updatedAvailability);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMenuCommand)) {
            return false;
        }

        EditMenuCommand otherCommand = (EditMenuCommand) other;
        return index.equals(otherCommand.index)
                && editMenuDescriptor.equals(otherCommand.editMenuDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editMenuDescriptor", editMenuDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the menu item with.
     * Each non-empty field value will replace the corresponding field value of the menu item.
     */
    public static class EditMenuDescriptor {
        private Food name;
        private Price price;
        private Availability availability;

        /**
         * Creates an empty {@code EditMenuDescriptor}.
         */
        public EditMenuDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditMenuDescriptor(EditMenuDescriptor toCopy) {
            setName(toCopy.name);
            setPrice(toCopy.price);
            setAvailability(toCopy.availability);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, price, availability);
        }

        /**
         * Sets the name to edit.
         */
        public void setName(Food name) {
            this.name = name;
        }

        /**
         * Returns the name to edit, if any.
         */
        public Optional<Food> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets the price to edit.
         */
        public void setPrice(Price price) {
            this.price = price;
        }

        /**
         * Returns the price to edit, if any.
         */
        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        /**
         * Sets the availability to edit.
         */
        public void setAvailability(Availability availability) {
            this.availability = availability;
        }

        /**
         * Returns the availability to edit, if any.
         */
        public Optional<Availability> getAvailability() {
            return Optional.ofNullable(availability);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMenuDescriptor)) {
                return false;
            }

            EditMenuDescriptor otherDesc = (EditMenuDescriptor) other;
            return Objects.equals(name, otherDesc.name)
                    && Objects.equals(price, otherDesc.price)
                    && Objects.equals(availability, otherDesc.availability);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("price", price)
                    .add("availability", availability)
                    .toString();
        }
    }
}
