package seedu.homechef.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.homechef.model.menu.MenuItem;

/**
 * A UI component that displays information of a {@code MenuItem}.
 */
public class MenuCard extends UiPart<Region> {

    private static final String FXML = "MenuListCard.fxml";

    /** The menu item displayed by this card. */
    public final MenuItem menuItem;

    @FXML
    private HBox menuCardPane;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label price;

    @FXML
    private Label availability;

    /**
     * Creates a {@code MenuCard} with the given {@code MenuItem} and index to display.
     */
    public MenuCard(MenuItem menuItem, int displayedIndex) {
        super(FXML);
        this.menuItem = menuItem;
        id.setText(displayedIndex + ". ");
        name.setText(menuItem.getName().fullName);
        price.setText("$" + menuItem.getPrice().value);
        availability.setText(menuItem.isAvailable() ? "Available" : "Unavailable");
    }
}
