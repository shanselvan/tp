package seedu.homechef.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.homechef.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.commons.util.JsonUtil;
import seedu.homechef.model.HomeChef;
import seedu.homechef.testutil.TypicalOrders;

public class JsonSerializableHomeChefTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableHomeChefTest");
    private static final Path TYPICAL_ORDERS_FILE = TEST_DATA_FOLDER.resolve("typicalOrdersHomeChef.json");
    private static final Path INVALID_ORDER_FILE = TEST_DATA_FOLDER.resolve("invalidOrderHomeChef.json");
    private static final Path DUPLICATE_ORDER_FILE = TEST_DATA_FOLDER.resolve("duplicateOrderHomeChef.json");
    private static final Path MISSING_ORDERS_LIST_FILE = TEST_DATA_FOLDER.resolve("missingOrdersListHomeChef.json");
    private static final Path NULL_ORDER_ELEMENT_FILE = TEST_DATA_FOLDER.resolve("nullOrderElementHomeChef.json");
    private static final Path NULL_TAG_ELEMENT_FILE = TEST_DATA_FOLDER.resolve("nullTagElementHomeChef.json");

    @Test
    public void toModelType_typicalOrdersFile_success() throws Exception {
        JsonSerializableHomeChef dataFromFile = JsonUtil.readJsonFile(TYPICAL_ORDERS_FILE,
                JsonSerializableHomeChef.class).get();
        HomeChef homeChefFromFile = dataFromFile.toModelType();
        HomeChef typicalOrdersHomeChef = TypicalOrders.getTypicalHomeChef();
        assertEquals(homeChefFromFile, typicalOrdersHomeChef);
    }

    @Test
    public void toModelType_invalidOrderFile_throwsIllegalValueException() throws Exception {
        JsonSerializableHomeChef dataFromFile = JsonUtil.readJsonFile(INVALID_ORDER_FILE,
                JsonSerializableHomeChef.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateOrders_throwsIllegalValueException() throws Exception {
        JsonSerializableHomeChef dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ORDER_FILE,
                JsonSerializableHomeChef.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableHomeChef.MESSAGE_DUPLICATE_ORDER,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_missingOrdersList_throwsIllegalValueException() throws Exception {
        JsonSerializableHomeChef dataFromFile = JsonUtil.readJsonFile(MISSING_ORDERS_LIST_FILE,
                JsonSerializableHomeChef.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableHomeChef.MESSAGE_MISSING_ORDERS_LIST,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_nullOrderElement_throwsIllegalValueException() throws Exception {
        JsonSerializableHomeChef dataFromFile = JsonUtil.readJsonFile(NULL_ORDER_ELEMENT_FILE,
                JsonSerializableHomeChef.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableHomeChef.MESSAGE_INVALID_ORDER_ELEMENT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_nullTagElement_throwsIllegalValueException() throws Exception {
        JsonSerializableHomeChef dataFromFile = JsonUtil.readJsonFile(NULL_TAG_ELEMENT_FILE,
                JsonSerializableHomeChef.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
