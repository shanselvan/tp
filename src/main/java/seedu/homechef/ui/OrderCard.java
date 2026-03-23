package seedu.homechef.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentStatus;
import seedu.homechef.model.order.Price;

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
    private static final String PENDING_SYMBOL = "◯";
    private static final String IN_PROGRESS_SYMBOL = "◎";
    private static final String COMPLETED_SYMBOL = "⬤";
    private static final String PAYMENT_SYMBOL = "$";

    private static final String PRICE_SYMBOL = "$";

    public final Order order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label food;
    @FXML
    private Label customer;
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
    private Label price;
    @FXML
    private Label paymentInfo;
    @FXML
    private Label completionStatus;
    @FXML
    private Label paymentStatus;
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
        customer.setText(order.getCustomer().fullName);
        phone.setText(order.getPhone().value);
        address.setText(order.getAddress().value);
        date.setText(order.getDate().toString());
        email.setText(order.getEmail().value);
        setPriceLabel();
        setCompletionStatusLabel(order.getCompletionStatus());
        setPaymentStatusLabel(order.getPaymentStatus());
        order.getPaymentInfo().ifPresentOrElse(
                info -> paymentInfo.setText("Payment: " + info.toString()), () -> {
                    paymentInfo.setVisible(false);
                    paymentInfo.setManaged(false);
                });
        order.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> dietTags.getChildren().add(new Label(tag.tagName)));
    }

    private void setCompletionStatusLabel(CompletionStatus status) {
        assert status != null;

        switch (status) {
        case PENDING:
            completionStatus.setText(PENDING_SYMBOL + " " + status);
            completionStatus.getStyleClass().add("completion_status_label_pending");
            break;
        case IN_PROGRESS:
            completionStatus.setText(IN_PROGRESS_SYMBOL + " " + status);
            completionStatus.getStyleClass().add("completion_status_label_in_progress");
            break;
        case COMPLETED:
            completionStatus.setText(COMPLETED_SYMBOL + " " + status);
            completionStatus.getStyleClass().add("completion_status_label_complete");
            break;
        default:
            // Do nothing
        }
    }

    private void setPaymentStatusLabel(PaymentStatus status) {
        assert status != null;
        paymentStatus.setText(PAYMENT_SYMBOL + " " + status);
        switch (status) {
        case PAID:
            paymentStatus.getStyleClass().add("payment_status_label_paid");
            break;
        case UNPAID:
            paymentStatus.getStyleClass().add("payment_status_label_unpaid");
            break;
        default:
            // Do nothing
        }
    }

    private void setPriceLabel() {
        String orderPrice = order.getPrice().value;
        String priceLabel = "Total: " + PRICE_SYMBOL +  orderPrice;
        price.setText(priceLabel);
    }
}
