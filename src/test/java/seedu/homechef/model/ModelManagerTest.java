package seedu.homechef.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalOrders.ALICE;
import static seedu.homechef.testutil.TypicalOrders.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.order.CustomerContainsKeywordsPredicate;
import seedu.homechef.model.order.Order;
import seedu.homechef.testutil.HomeChefBuilder;
import seedu.homechef.testutil.OrderBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new HomeChef(), new HomeChef(modelManager.getHomeChef()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setHomeChefFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setHomeChefFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setHomeChefFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setHomeChefFilePath(null));
    }

    @Test
    public void setHomeChefFilePath_validPath_setsHomeChefFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setHomeChefFilePath(path);
        assertEquals(path, modelManager.getHomeChefFilePath());
    }

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasOrder(null));
    }

    @Test
    public void hasOrder_orderNotInHomeChef_returnsFalse() {
        assertFalse(modelManager.hasOrder(ALICE));
    }

    @Test
    public void hasOrder_orderInHomeChef_returnsTrue() {
        modelManager.addOrder(ALICE);
        assertTrue(modelManager.hasOrder(ALICE));
    }

    @Test
    public void getFilteredOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredOrderList().remove(0));
    }

    @Test
    public void getFilteredOrderList_sortedByDateAscending() {
        Order later = new OrderBuilder().withCustomer("Zed").withFood("Cake").withPhone("99999999")
                .withEmail("z@example.com").withAddress("addr").withDate("20-04-2026").build();
        Order earlier = new OrderBuilder().withCustomer("Amy").withFood("Bread").withPhone("11111111")
                .withEmail("a@example.com").withAddress("addr").withDate("01-04-2026").build();
        Order middle = new OrderBuilder().withCustomer("Bob").withFood("Pie").withPhone("22222222")
                .withEmail("b@example.com").withAddress("addr").withDate("10-04-2026").build();

        HomeChef homeChef = new HomeChefBuilder().withOrder(later).withOrder(earlier).withOrder(middle).build();
        modelManager = new ModelManager(homeChef, new MenuBook(), new UserPrefs());

        assertEquals(earlier, modelManager.getFilteredOrderList().get(0));
        assertEquals(middle, modelManager.getFilteredOrderList().get(1));
        assertEquals(later, modelManager.getFilteredOrderList().get(2));
    }

    @Test
    public void getFilteredOrderList_sameDate_tieBreakByCustomerThenFood() {
        Order a1 = new OrderBuilder().withCustomer("Alice").withFood("Brownie").withPhone("11111111")
                .withEmail("a1@example.com").withAddress("addr").withDate("10-04-2026").build();
        Order a2 = new OrderBuilder().withCustomer("Alice").withFood("Apple Pie").withPhone("22222222")
                .withEmail("a2@example.com").withAddress("addr").withDate("10-04-2026").build();
        Order b = new OrderBuilder().withCustomer("Bob").withFood("Cake").withPhone("33333333")
                .withEmail("b@example.com").withAddress("addr").withDate("10-04-2026").build();

        HomeChef homeChef = new HomeChefBuilder().withOrder(b).withOrder(a1).withOrder(a2).build();
        modelManager = new ModelManager(homeChef, new MenuBook(), new UserPrefs());
        assertEquals(a2, modelManager.getFilteredOrderList().get(0));
        assertEquals(a1, modelManager.getFilteredOrderList().get(1));
        assertEquals(b, modelManager.getFilteredOrderList().get(2));
    }

    @Test
    public void equals() {
        HomeChef homeChef = new HomeChefBuilder().withOrder(ALICE).withOrder(BENSON).build();
        HomeChef differentHomeChef = new HomeChef();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(homeChef, new MenuBook(), userPrefs);
        ModelManager modelManagerCopy = new ModelManager(homeChef, new MenuBook(), userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different homeChef -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentHomeChef, new MenuBook(), userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getCustomer().fullName.split("\\s+");
        modelManager.updateFilteredOrderList(new CustomerContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(homeChef, new MenuBook(), userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setHomeChefFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(homeChef, new MenuBook(), differentUserPrefs)));
    }
}
