package seedu.homechef.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.homechef.model.HomeChef;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.MenuItemName;
import seedu.homechef.model.menu.ReadOnlyMenuBook;
import seedu.homechef.model.order.*;
import seedu.homechef.model.tag.DietTag;

/**
 * Contains utility methods for populating {@code HomeChef} with sample data.
 */
public class SampleDataUtil {

    public static Order[] getSampleOrders() {
        Order alex = new Order(new Food("Birthday Cake"), new Customer("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Date("10-03-2026"),
                CompletionStatus.PENDING,
                PaymentStatus.UNPAID,
                getTagSet("friends"),
                new Price("32.50"));
        Order bernice = new Order(new Food("Cupcakes (24pcs)"), new Customer("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Date("20-03-2026"),
                CompletionStatus.COMPLETED,
                PaymentStatus.UNPAID,
                getTagSet("colleagues", "friends"),
                new Price("55.67"));
        Order charlotte = new Order(new Food("Chocolate Chip Cookies (3pcs)"), new Customer("Charlotte Oliveiro"),
                new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Date("11-04-2026"),
                CompletionStatus.IN_PROGRESS,
                PaymentStatus.PAID,
                getTagSet("neighbours"),
                new Price("4"));
        Order david = new Order(new Food("Cookies Assortment (50pcs)"), new Customer("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Date("26-04-2026"),
                CompletionStatus.COMPLETED,
                PaymentStatus.UNPAID,
                getTagSet("family"),
                new Price("50"));
        Order irfan = new Order(new Food("Blueberry Pie"), new Customer("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Date("30-04-2026"),
                CompletionStatus.COMPLETED,
                PaymentStatus.PAID,
                getTagSet("classmates"),
                new Quantity(2),
                new Price("15.20"));
        Order roy = new Order(new Food("Sourdough Bread (3pcs)"), new Customer("Roy Balakrishnan"),
                new Phone("92624417"),
                new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Date("30-04-2026"),
                CompletionStatus.IN_PROGRESS,
                PaymentStatus.PAID,
                getTagSet("colleagues"),
                new Price("11.8"),
                Optional.of(new PaymentInfo(PaymentType.BANK, null, "Singapore Bank", "1234567", null, null, null)));
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
            "Cookies Assortment (50pcs)",
            "Blueberry Pie",
            "Sourdough Bread (3pcs)"
        };
        for (String foodName : sampleFoodNames) {
            seedu.homechef.model.menu.Price menuPrice = new seedu.homechef.model.menu.Price("10.00");
            menuBook.addMenuItem(new MenuItem(new MenuItemName(foodName), menuPrice, true));
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
