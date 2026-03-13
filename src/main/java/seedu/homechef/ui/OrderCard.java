package seedu.homechef.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.homechef.model.order.Order;

/**
 * An UI component that displays information of a {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/homechef-level4/issues/336">The issue on HomeChef level 4</a>
     */

    public final Order order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label food;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label date;
    @FXML
    private Label email;
    @FXML
    private Label paymentInfo;
    @FXML
    private FlowPane dietTags;

    /**
     * Creates a {@code OrderCode} with the given {@code Order} and index to display.
     */
    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        food.setText(order.getFood().foodName);
        name.setText(order.getName().fullName);
        phone.setText(order.getPhone().value);
        address.setText(order.getAddress().value);
        date.setText(order.getDate().toString());
        email.setText(order.getEmail().value);
        order.getPaymentInfo().ifPresentOrElse(
                info -> paymentInfo.setText("Payment: " + info.toString()), () -> {
                    paymentInfo.setVisible(false);
                    paymentInfo.setManaged(false);
                });
        order.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> dietTags.getChildren().add(new Label(tag.tagName)));
    }
}
