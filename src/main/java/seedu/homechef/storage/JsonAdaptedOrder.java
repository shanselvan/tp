package seedu.homechef.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.Name;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentType;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.tag.DietTag;

/**
 * Jackson-friendly version of {@link Order}.
 */
class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String dish;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String date;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
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
            @JsonProperty("dish") String dish,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("date") String date,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("paymentType") String paymentType,
            @JsonProperty("paymentHandle") String paymentHandle,
            @JsonProperty("paymentBankName") String paymentBankName,
            @JsonProperty("paymentReferenceNumber") String paymentReferenceNumber,
            @JsonProperty("paymentLastFourDigits") String paymentLastFourDigits,
            @JsonProperty("paymentWalletProvider") String paymentWalletProvider,
            @JsonProperty("paymentWalletAccountId") String paymentWalletAccountId) {
        this.dish = dish;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.date = date;
        if (tags != null) {
            this.tags.addAll(tags);
        }
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
        dish = source.getFood().foodName;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        date = source.getDate().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));

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

        if (dish == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Food.class.getSimpleName()));
        }
        if (!Food.isValidFood(dish)) {
            throw new IllegalValueException(Food.MESSAGE_CONSTRAINTS);
        }
        final Food modelFood = new Food(dish);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

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

        return new Order(modelFood, modelName, modelPhone, modelEmail,
                modelAddress, modelDate, modelDietTags, modelPaymentInfo);
    }

}
