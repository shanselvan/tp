package seedu.homechef.ui;

import static seedu.homechef.commons.util.AppUtil.checkArgument;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentStatus;
import seedu.homechef.model.order.Phone;

/**
 * An UI component that displays information of a {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    private static final String PENDING_SYMBOL = "◯";
    private static final String IN_PROGRESS_SYMBOL = "◎";
    private static final String COMPLETED_SYMBOL = "⬤";
    private static final String PAYMENT_SYMBOL = "$";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/homechef-level4/issues/336">The issue on HomeChef level 4</a>
     */

    public final Order order;
    private final Image customerIcon = new Image(this.getClass().getResourceAsStream("/images/customer.png"));
    private final Image phoneIcon = new Image(this.getClass().getResourceAsStream("/images/phone.png"));
    private final Image addressIcon = new Image(this.getClass().getResourceAsStream("/images/address.png"));
    private final Image dateIcon = new Image(this.getClass().getResourceAsStream("/images/date.png"));
    private final Image emailIcon = new Image(this.getClass().getResourceAsStream("/images/email.png"));

    @FXML
    private HBox cardPane;
    @FXML
    private Label food;
    @FXML
    private ImageView customerDisplayIcon;
    @FXML
    private Label customer;
    @FXML
    private Label id;
    @FXML
    private ImageView phoneDisplayIcon;
    @FXML
    private Label phone;
    @FXML
    private ImageView addressDisplayIcon;
    @FXML
    private Label address;
    @FXML
    private ImageView dateDisplayIcon;
    @FXML
    private Label date;
    @FXML
    private ImageView emailDisplayIcon;
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
        setIdDisplay(displayedIndex);
        setFoodDisplay(order.getFood());
        setCustomerDisplay(order.getCustomer());
        setPhoneDisplay(order.getPhone());
        setAddressDisplay(order.getAddress());
        setDateDisplay(order.getDate());
        setEmailDisplay(order.getEmail());
        setPriceDisplay();
        setCompletionStatusDisplay(order.getCompletionStatus());
        setPaymentStatusDisplay(order.getPaymentStatus());
        order.getPaymentInfo().ifPresentOrElse(
                info -> paymentInfo.setText("Payment: " + info.toString()), () -> {
                    paymentInfo.setVisible(false);
                    paymentInfo.setManaged(false);
                });
        order.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> dietTags.getChildren().add(new Label(tag.tagName)));
    }

    private void setIdDisplay(int index) {
        this.id.setText(index + ". ");
    }

    private void setFoodDisplay(Food food) {
        this.food.setText(food.toString());
    }

    private void setCustomerDisplay(Customer customer) {
        customerDisplayIcon.setImage(customerIcon);
        this.customer.setText(customer.toString());
    }

    private void setPhoneDisplay(Phone phone) {
        phoneDisplayIcon.setImage(phoneIcon);
        this.phone.setText(phone.toString());
    }

    private void setAddressDisplay(Address address) {
        addressDisplayIcon.setImage(addressIcon);
        this.address.setText(address.toString());
    }

    private void setDateDisplay(Date date) {
        dateDisplayIcon.setImage(dateIcon);
        this.date.setText(date.toString());
        String dateUrgency = date.getUrgency();

        assert dateUrgency != null && !dateUrgency.isBlank();

        switch (dateUrgency) {
        case Date.NORMAL:
            this.date.getStyleClass().add("normal");
            break;
        case Date.URGENT:
            this.date.getStyleClass().add("urgent");
            break;
        case Date.OVERDUE:
            this.date.getStyleClass().add("overdue");
            break;
        default:
            checkArgument(Date.isValidUrgency(dateUrgency),
                    Date.URGENCY_CONSTRAINTS);
        }
    }

    private void setEmailDisplay(Email email) {
        emailDisplayIcon.setImage(emailIcon);
        this.email.setText(email.toString());
    }

    private void setCompletionStatusDisplay(CompletionStatus status) {
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
            checkArgument(CompletionStatus.isValidCompletionStatus(status.toString()),
                    CompletionStatus.MESSAGE_CONSTRAINTS);
        }
    }

    private void setPaymentStatusDisplay(PaymentStatus status) {
        assert status != null;

        paymentStatus.setText(PAYMENT_SYMBOL + " " + status);
        switch (status) {
        case PAID:
            paymentStatus.getStyleClass().add("payment_status_label_paid");
            break;
        case UNPAID:
            paymentStatus.getStyleClass().add("payment_status_label_unpaid");
            break;
        case PARTIAL:
            paymentStatus.getStyleClass().add("payment_status_label_partial");
            break;
        default:
            checkArgument(PaymentStatus.isValidPaymentStatus(status.toString()),
                    PaymentStatus.MESSAGE_CONSTRAINTS);
        }
    }

    private void setPriceDisplay() {
        String orderPrice = order.getPrice().toString();
        String priceLabel = "Total: " + PAYMENT_SYMBOL + orderPrice;
        price.setText(priceLabel);
    }
}
