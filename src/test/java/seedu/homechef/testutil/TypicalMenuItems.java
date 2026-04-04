package seedu.homechef.testutil;

import seedu.homechef.model.common.Food;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.Price;

/**
 * A utility class containing a list of {@code MenuItem} objects to be used in tests.
 * Names match the Food values used in TypicalOrders and CommandTestUtil so that
 * AddCommand/EditCommand integration tests pass menu validation.
 */
public class TypicalMenuItems {

    // From TypicalOrders.getTypicalOrders() - ALICE through GEORGE
    public static final MenuItem BIRTHDAY_CAKE = new MenuItem(
            new Food("Birthday Cake"), new Price("25.00"), true);
    public static final MenuItem SOURDOUGH_BREAD = new MenuItem(
            new Food("Sourdough Bread"), new Price("8.00"), true);
    public static final MenuItem CUPCAKES = new MenuItem(
            new Food("Cupcakes (24pcs)"), new Price("30.00"), true);
    public static final MenuItem WEDDING_CAKE_3TIER = new MenuItem(
            new Food("Wedding Cake - 3 Tier"), new Price("150.00"), true);
    public static final MenuItem CHOCOLATE_CHIP_COOKIES = new MenuItem(
            new Food("Chocolate Chip Cookies (3pcs)"), new Price("6.00"), true);
    public static final MenuItem MUFFIN = new MenuItem(
            new Food("Muffin (4pc)"), new Price("10.00"), true);
    public static final MenuItem DOUGHNUT_ASSORTMENT = new MenuItem(
            new Food("Doughnut Assortment"), new Price("12.00"), true);

    // From CommandTestUtil - VALID_FOOD_BOB = "Wedding Cake" (distinct from "Wedding Cake - 3 Tier")
    public static final MenuItem WEDDING_CAKE = new MenuItem(
            new Food("Wedding Cake"), new Price("80.00"), true);

    // Extra item used in new integration test cases
    public static final MenuItem CHICKEN_RICE = new MenuItem(
            new Food("Chicken Rice"), new Price("5.50"), true);

    private TypicalMenuItems() {} // prevents instantiation

    /**
     * Returns a {@code MenuBook} with all the typical menu items.
     */
    public static MenuBook getTypicalMenuBook() {
        MenuBook mb = new MenuBook();
        for (MenuItem item : getTypicalMenuItems()) {
            mb.addMenuItem(item);
        }
        return mb;
    }

    /**
     * Returns an array of all typical menu items.
     */
    public static MenuItem[] getTypicalMenuItems() {
        return new MenuItem[] {
            BIRTHDAY_CAKE, SOURDOUGH_BREAD, CUPCAKES, WEDDING_CAKE_3TIER,
            CHOCOLATE_CHIP_COOKIES, MUFFIN, DOUGHNUT_ASSORTMENT, WEDDING_CAKE, CHICKEN_RICE
        };
    }
}
