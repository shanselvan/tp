package seedu.homechef.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.homechef.model.HomeChef;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.MenuItemName;
import seedu.homechef.model.menu.Price;
import seedu.homechef.model.menu.ReadOnlyMenuBook;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Food;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentStatus;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.tag.DietTag;

/**
 * Contains utility methods for populating {@code HomeChef} with sample data.
 */
public class SampleDataUtil {

    public static final CompletionStatus IN_PROGRESS_STATUS = new CompletionStatus("In progress");
    public static final CompletionStatus COMPLETED_STATUS = new CompletionStatus("Completed");

    public static Order[] getSampleOrders() {
        Order alex = new Order(new Food("Birthday Cake"), new Customer("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Date("10-03-2026"),
                IN_PROGRESS_STATUS,
                new PaymentStatus(PaymentStatus.IS_UNPAID),
                getTagSet("friends"));
        Order bernice = new Order(new Food("Cupcakes (24pcs)"), new Customer("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Date("20-03-2026"),
                COMPLETED_STATUS,
                new PaymentStatus(PaymentStatus.IS_UNPAID),
                getTagSet("colleagues", "friends"));
        Order charlotte = new Order(new Food("Chocolate Chip Cookies (3pcs)"), new Customer("Charlotte Oliveiro"),
                new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Date("11-04-2026"),
                IN_PROGRESS_STATUS,
                new PaymentStatus(PaymentStatus.IS_PAID),
                getTagSet("neighbours"));
        Order david = new Order(new Food("Cookies Assortment (50pcs"), new Customer("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Date("26-04-2026"),
                COMPLETED_STATUS,
                new PaymentStatus(PaymentStatus.IS_UNPAID),
                getTagSet("family"));
        Order irfan = new Order(new Food("Blueberry Pie"), new Customer("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Date("30-04-2026"),
                COMPLETED_STATUS,
                new PaymentStatus(PaymentStatus.IS_PAID),
                getTagSet("classmates"));
        Order roy = new Order(new Food("Sourdough Bread (3pcs)"), new Customer("Roy Balakrishnan"),
                new Phone("92624417"),
                new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Date("30-04-2026"),
                IN_PROGRESS_STATUS,
                new PaymentStatus(PaymentStatus.IS_PAID),
                getTagSet("colleagues"));
        return new Order[]{alex, bernice, charlotte, david, irfan, roy};
    }

    /**
     * Returns a {@code ReadOnlyMenuBook} containing menu items for each unique food in the sample orders.
     * Each item is available and priced at 10.00.
     */
    public static ReadOnlyMenuBook getSampleMenuBook() {
        MenuBook menuBook = new MenuBook();
        String[] sampleFoodNames = {
            "Birthday Cake",
            "Cupcakes (24pcs)",
            "Chocolate Chip Cookies (3pcs)",
            "Cookies Assortment (50pcs",
            "Blueberry Pie",
            "Sourdough Bread (3pcs)"
        };
        for (String foodName : sampleFoodNames) {
            menuBook.addMenuItem(new MenuItem(new MenuItemName(foodName), new Price("10.00"), true));
        }
        return menuBook;
    }

    public static ReadOnlyHomeChef getSampleHomeChef() {
        HomeChef sampleAb = new HomeChef();
        for (Order sampleOrder : getSampleOrders()) {
            sampleAb.addOrder(sampleOrder);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<DietTag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(DietTag::new)
                .collect(Collectors.toSet());
    }

}
