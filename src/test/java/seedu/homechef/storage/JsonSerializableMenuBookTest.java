package seedu.homechef.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.homechef.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.commons.util.JsonUtil;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.testutil.TypicalMenuItems;

public class JsonSerializableMenuBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMenuBookTest");
    private static final Path TYPICAL_MENU_BOOK_FILE = TEST_DATA_FOLDER.resolve("typicalMenuBook.json");
    private static final Path MISSING_MENU_ITEMS_LIST_FILE = TEST_DATA_FOLDER.resolve(
            "missingMenuItemsListMenuBook.json");
    private static final Path NULL_MENU_ITEM_ELEMENT_FILE = TEST_DATA_FOLDER.resolve(
            "nullMenuItemElementMenuBook.json");

    @Test
    public void toModelType_typicalMenuBookFile_success() throws Exception {
        JsonSerializableMenuBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MENU_BOOK_FILE,
                JsonSerializableMenuBook.class).get();
        MenuBook menuBookFromFile = dataFromFile.toModelType();

        MenuBook expected = new MenuBook();
        expected.addMenuItem(TypicalMenuItems.BIRTHDAY);
        expected.addMenuItem(TypicalMenuItems.BREAD);

        assertEquals(menuBookFromFile, expected);
    }

    @Test
    public void toModelType_missingMenuItemsList_throwsIllegalValueException() throws Exception {
        JsonSerializableMenuBook dataFromFile = JsonUtil.readJsonFile(MISSING_MENU_ITEMS_LIST_FILE,
                JsonSerializableMenuBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMenuBook.MESSAGE_MISSING_MENU_ITEMS_LIST,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_nullMenuItemElement_throwsIllegalValueException() throws Exception {
        JsonSerializableMenuBook dataFromFile = JsonUtil.readJsonFile(NULL_MENU_ITEM_ELEMENT_FILE,
                JsonSerializableMenuBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMenuBook.MESSAGE_INVALID_MENU_ITEM_ELEMENT,
                dataFromFile::toModelType);
    }
}


