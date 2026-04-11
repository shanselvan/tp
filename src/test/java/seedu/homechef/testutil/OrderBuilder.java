package seedu.homechef.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.DietTag;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentStatus;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Quantity;
import seedu.homechef.model.util.SampleDataUtil;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    public static final String DEFAULT_FOOD = "Birthday Cake";
    public static final String DEFAULT_CUSTOMER = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DATE = "10-03-2099";
    public static final String DEFAULT_COMPLETION_STATUS = "Pending";
    public static final String DEFAULT_PAYMENT_STATUS = "Unpaid";
    public static final String DEFAULT_PRICE = "6.70";

    private Food food;
    private Customer customer;
    private Phone phone;
    private Email email;
    private Address address;
    private Date date;
    private CompletionStatus completionStatus;
    private PaymentStatus paymentStatus;
    private Set<DietTag> dietTags;
    private Quantity quantity;
    private Price price;
    private Optional<PaymentInfo> paymentInfo = Optional.empty();

    /**
     * Creates a {@code OrderBuilder} with the default details.
     */
    public OrderBuilder() {
        food = new Food(DEFAULT_FOOD);
        customer = new Customer(DEFAULT_CUSTOMER);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        date = new Date(DEFAULT_DATE);
        completionStatus = CompletionStatus.fromString(DEFAULT_COMPLETION_STATUS);
        paymentStatus = PaymentStatus.fromString(DEFAULT_PAYMENT_STATUS);
        quantity = new Quantity(1);
        price = new Price(DEFAULT_PRICE);
        dietTags = new HashSet<>();
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(Order orderToCopy) {
        food = orderToCopy.getFood();
        customer = orderToCopy.getCustomer();
        phone = orderToCopy.getPhone();
        email = orderToCopy.getEmail();
        address = orderToCopy.getAddress();
        date = orderToCopy.getDate();
        completionStatus = orderToCopy.getCompletionStatus();
        paymentStatus = orderToCopy.getPaymentStatus();
        quantity = orderToCopy.getQuantity();
        price = orderToCopy.getPrice();
        dietTags = new HashSet<>(orderToCopy.getTags());
        paymentInfo = orderToCopy.getPaymentInfo();
    }

    /**
     * Sets the {@code Food} of the {@code Order} that we are building.
     */
    public OrderBuilder withFood(String foodName) {
        this.food = new Food(foodName);
        return this;
    }

    /**
     * Sets the {@code Customer} of the {@code Order} that we are building.
     */
    public OrderBuilder withCustomer(String name) {
        this.customer = new Customer(name);
        return this;
    }

    /**
     * Parses the {@code dietTags} into a {@code Set<DietTag>} and set it to the {@code Order} that we are building.
     */
    public OrderBuilder withTags(String... tags) {
        this.dietTags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Order} that we are building.
     */
    public OrderBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Order} that we are building.
     */
    public OrderBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Order} that we are building.
     */
    public OrderBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Order} that we are building.
     */
    public OrderBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code CompletionStatus} of the {@code Order} that we are building.
     */
    public OrderBuilder withCompletionStatus(String completionStatus) {
        this.completionStatus = CompletionStatus.fromString(completionStatus);
        return this;
    }

    /**
     * Sets the {@code PaymentStatus} of the {@code Order} that we are building.
     */
    public OrderBuilder withPaymentStatus(String paymentStatus) {
        this.paymentStatus = PaymentStatus.fromString(paymentStatus);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Order} that we are building.
     */
    public OrderBuilder withQuantity(int quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Order} that we are building.
     */
    public OrderBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code PaymentInfo} of the {@code Order} that we are building.
     */
    public OrderBuilder withPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = Optional.of(paymentInfo);
        return this;
    }

    /**
     * Builds and returns the {@code Order}.
     */
    public Order build() {
        return new Order(food, customer, phone, email, address, date,
                completionStatus, paymentStatus, dietTags, quantity, price, paymentInfo);
    }

}
