package seedu.homechef.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.homechef.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.tag.DietTag;

/**
 * A utility class to help with building EditOrderDescriptor objects.
 */
public class EditOrderDescriptorBuilder {

    private EditOrderDescriptor descriptor;

    public EditOrderDescriptorBuilder() {
        descriptor = new EditOrderDescriptor();
    }

    public EditOrderDescriptorBuilder(EditOrderDescriptor descriptor) {
        this.descriptor = new EditOrderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditOrderDescriptor} with fields containing {@code order}'s details
     */
    public EditOrderDescriptorBuilder(Order order) {
        descriptor = new EditOrderDescriptor();
        descriptor.setFood(order.getFood());
        descriptor.setCustomer(order.getCustomer());
        descriptor.setPhone(order.getPhone());
        descriptor.setEmail(order.getEmail());
        descriptor.setAddress(order.getAddress());
        descriptor.setDate(order.getDate());
        descriptor.setTags(order.getTags());
        order.getPaymentInfo().ifPresent(descriptor::setPaymentInfo);
    }

    /**
     * Sets the {@code Food} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withFood(String foodName) {
        descriptor.setFood(new Food(foodName));
        return this;
    }

    /**
     * Sets the {@code Customer} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withCustomer(String name) {
        descriptor.setCustomer(new Customer(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<DietTag>} and set it to the {@code EditOrderDescriptor}
     * that we are building.
     */
    public EditOrderDescriptorBuilder withTags(String... tags) {
        Set<DietTag> dietTagSet = Stream.of(tags).map(DietTag::new).collect(Collectors.toSet());
        descriptor.setTags(dietTagSet);
        return this;
    }

    /**
     * Sets the {@code PaymentInfo} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withPaymentInfo(PaymentInfo paymentInfo) {
        descriptor.setPaymentInfo(paymentInfo);
        return this;
    }

    public EditOrderDescriptor build() {
        return descriptor;
    }
}
