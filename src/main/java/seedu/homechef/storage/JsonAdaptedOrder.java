package seedu.homechef.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentStatus;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Price;
import seedu.homechef.model.tag.DietTag;

/**
 * Jackson-friendly version of {@link Order}.
 */
class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String food;
    private final String customer;
    private final String phone;
    private final String email;
    private final String address;
    private final String date;
    private final String completionStatus;
    private final String paymentStatus;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String price;
    private final String paymentType;
    private final String paymentHandle;
    private final String paymentBankName;
    private final String paymentReferenceNumber;
    private final String paymentLastFourDigits;
    private final String paymentWalletProvider;
    private final String paymentWalletAccountId;

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(
            @JsonProperty("food") String food,
            @JsonProperty("customer") String customer,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("date") String date,
            @JsonProperty("price") String price,
            @JsonProperty("completionStatus") String completionStatus,
            @JsonProperty("paymentStatus") String paymentStatus,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("paymentType") String paymentType,
            @JsonProperty("paymentHandle") String paymentHandle,
            @JsonProperty("paymentBankName") String paymentBankName,
            @JsonProperty("paymentReferenceNumber") String paymentReferenceNumber,
            @JsonProperty("paymentLastFourDigits") String paymentLastFourDigits,
            @JsonProperty("paymentWalletProvider") String paymentWalletProvider,
            @JsonProperty("paymentWalletAccountId") String paymentWalletAccountId) {
        this.food = food;
        this.customer = customer;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.date = date;
        this.completionStatus = completionStatus;
        this.paymentStatus = paymentStatus;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.price = price;
        this.paymentType = paymentType;
        this.paymentHandle = paymentHandle;
        this.paymentBankName = paymentBankName;
        this.paymentReferenceNumber = paymentReferenceNumber;
        this.paymentLastFourDigits = paymentLastFourDigits;
        this.paymentWalletProvider = paymentWalletProvider;
        this.paymentWalletAccountId = paymentWalletAccountId;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        food = source.getFood().toString();
        customer = source.getCustomer().toString();
        phone = source.getPhone().toString();
        email = source.getEmail().toString();
        address = source.getAddress().toString();
        date = source.getDate().toString();
        completionStatus = source.getCompletionStatus().toString();
        paymentStatus = source.getPaymentStatus().toString();
        price = source.getPrice().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());

        Optional<PaymentInfo> optPaymentInfo = source.getPaymentInfo();
        if (optPaymentInfo.isPresent()) {
            PaymentInfo info = optPaymentInfo.get();
            paymentType = info.getType().name();
            paymentHandle = info.getHandle();
            paymentBankName = info.getBankName();
            paymentReferenceNumber = info.getReferenceNumber();
            paymentLastFourDigits = info.getLastFourDigits();
            paymentWalletProvider = info.getWalletProvider();
            paymentWalletAccountId = info.getWalletAccountId();
        } else {
            paymentType = null;
            paymentHandle = null;
            paymentBankName = null;
            paymentReferenceNumber = null;
            paymentLastFourDigits = null;
            paymentWalletProvider = null;
            paymentWalletAccountId = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType() throws IllegalValueException {
        final Set<DietTag> modelDietTags = new HashSet<>();
        for (JsonAdaptedTag tag : tags) {
            modelDietTags.add(tag.toModelType());
        }

        if (food == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Food.class.getSimpleName()));
        }
        if (!Food.isValidFood(food)) {
            throw new IllegalValueException(Food.MESSAGE_CONSTRAINTS);
        }
        final Food modelFood = new Food(food);

        if (customer == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Customer.class.getSimpleName()));
        }
        if (!Customer.isValidCustomer(customer)) {
            throw new IllegalValueException(Customer.MESSAGE_CONSTRAINTS);
        }
        final Customer modelCustomer = new Customer(customer);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (completionStatus == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, CompletionStatus.class.getSimpleName()));
        }
        if (!CompletionStatus.isValidCompletionStatus(completionStatus)) {
            throw new IllegalValueException(CompletionStatus.MESSAGE_CONSTRAINTS);
        }
        final CompletionStatus modelCompletionStatus = CompletionStatus.fromString(completionStatus);

        if (paymentStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PaymentStatus.class.getSimpleName()));
        }
        if (!PaymentStatus.isValidPaymentStatus(paymentStatus)) {
            throw new IllegalValueException(PaymentStatus.MESSAGE_CONSTRAINTS);
        }
        final PaymentStatus modelPaymentStatus = PaymentStatus.fromString(paymentStatus);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        Optional<PaymentInfo> modelPaymentInfo;
        if (paymentType == null) {
            modelPaymentInfo = Optional.empty();
        } else {
            PaymentType type;
            try {
                type = PaymentType.valueOf(paymentType);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(
                        "Invalid stored payment type: " + paymentType, e);
            }
            try {
                modelPaymentInfo = Optional.of(new PaymentInfo(
                        type, paymentHandle, paymentBankName, paymentReferenceNumber,
                        paymentLastFourDigits, paymentWalletProvider, paymentWalletAccountId));
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(e.getMessage(), e);
            }
        }

        return new Order(modelFood, modelCustomer, modelPhone, modelEmail, modelAddress, modelDate,
                modelCompletionStatus, modelPaymentStatus, modelDietTags, modelPrice, modelPaymentInfo);
    }

}
