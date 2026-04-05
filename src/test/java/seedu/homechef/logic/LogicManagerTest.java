package seedu.homechef.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static seedu.homechef.logic.Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;
import static seedu.homechef.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.homechef.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.CUSTOMER_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.FOOD_DESC_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalOrders.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.logic.commands.AddCommand;
import seedu.homechef.logic.commands.CommandResult;
import seedu.homechef.logic.commands.ListCommand;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.menu.MenuItemName;
import seedu.homechef.model.menu.Price;
import seedu.homechef.model.order.Order;
import seedu.homechef.storage.JsonHomeChefStorage;
import seedu.homechef.storage.JsonMenuBookStorage;
import seedu.homechef.storage.JsonUserPrefsStorage;
import seedu.homechef.storage.StorageManager;
import seedu.homechef.testutil.OrderBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonHomeChefStorage homeChefStorage =
                new JsonHomeChefStorage(temporaryFolder.resolve("homeChef.json"));
        JsonMenuBookStorage menuBookStorage =
                new JsonMenuBookStorage(temporaryFolder.resolve("menu.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(homeChefStorage, menuBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredOrderList().remove(0));
    }

    @Test
    public void getHomeChef_returnsModelHomeChef() {
        // EP: getter delegates directly to model
        assertSame(model.getHomeChef(), logic.getHomeChef());
    }

    @Test
    public void getFilteredMenuItemList_modifyList_throwsUnsupportedOperationException() {
        model.addMenuItem(new MenuItem(new MenuItemName("Chicken Rice"), new Price("5.50"), true));

        // EP: returned filtered menu list is unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredMenuItemList().remove(0));
    }

    @Test
    public void getHomeChefFilePath_returnsModelHomeChefFilePath() {
        // EP: file path getter delegates directly to model
        assertEquals(model.getHomeChefFilePath(), logic.getHomeChefFilePath());
    }

    @Test
    public void getGuiSettings_returnsModelGuiSettings() {
        // EP: gui settings getter delegates directly to model
        assertEquals(model.getGuiSettings(), logic.getGuiSettings());
    }

    @Test
    public void setGuiSettings_updatesModelGuiSettings() {
        GuiSettings newGuiSettings = new GuiSettings(640, 480, 10, 20);

        logic.setGuiSettings(newGuiSettings);

        // EP: gui settings setter delegates update to model
        assertEquals(newGuiSettings, model.getGuiSettings());
        assertEquals(newGuiSettings, logic.getGuiSettings());
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getHomeChef(), new MenuBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e               the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an HomeChefStorage that throws the IOException e when saving
        JsonHomeChefStorage homeChefStorage = new JsonHomeChefStorage(prefPath) {
            @Override
            public void saveHomeChef(ReadOnlyHomeChef homeChef, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonMenuBookStorage menuBookStorageForException =
                new JsonMenuBookStorage(temporaryFolder.resolve("ExceptionMenu.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(homeChefStorage, menuBookStorageForException, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Add "Birthday Cake" to the menu so that AddCommand passes menu validation
        model.addMenuItem(new MenuItem(new MenuItemName("Birthday Cake"), new Price("25.00"), true));

        // Triggers the saveHomeChef method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + FOOD_DESC_AMY + CUSTOMER_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY;
        // price is derived from the menu item ("Birthday Cake" costs "25.00")
        Order expectedOrder = new OrderBuilder(AMY).withTags().withPrice("25.00").build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addMenuItem(new MenuItem(new MenuItemName("Birthday Cake"), new Price("25.00"), true));
        expectedModel.addOrder(expectedOrder);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
