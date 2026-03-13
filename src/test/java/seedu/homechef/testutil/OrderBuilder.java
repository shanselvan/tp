package seedu.homechef.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.Name;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.tag.DietTag;
import seedu.homechef.model.util.SampleDataUtil;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    public static final String DEFAULT_FOOD = "Birthday Cake";
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DATE = "10-03-2026";

    private Food food;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Date date;
    private Set<DietTag> dietTags;
    private Optional<PaymentInfo> paymentInfo = Optional.empty();

    /**
     * Creates a {@code OrderBuilder} with the default details.
     */
    public OrderBuilder() {
        food = new Food(DEFAULT_FOOD);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        date = new Date(DEFAULT_DATE);
        dietTags = new HashSet<>();
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(Order orderToCopy) {
        food = orderToCopy.getFood();
        name = orderToCopy.getName();
        phone = orderToCopy.getPhone();
        email = orderToCopy.getEmail();
        address = orderToCopy.getAddress();
        date = orderToCopy.getDate();
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
     * Sets the {@code Name} of the {@code Order} that we are building.
     */
    public OrderBuilder withName(String name) {
        this.name = new Name(name);
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
        return new Order(food, name, phone, email, address, date, dietTags, paymentInfo);
    }

}
