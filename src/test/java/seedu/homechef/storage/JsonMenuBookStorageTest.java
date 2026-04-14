package seedu.homechef.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.homechef.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.homechef.commons.exceptions.DataLoadingException;
import seedu.homechef.model.menu.ReadOnlyMenuBook;

public class JsonMenuBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMenuBookStorageTest");

    private java.util.Optional<ReadOnlyMenuBook> readMenuBook(String filePath) throws Exception {
        return new JsonMenuBookStorage(Paths.get(filePath)).readMenuBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String fileInTestDataFolder) {
        return fileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(fileInTestDataFolder)
                : null;
    }

    @Test
    public void readMenuBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMenuBook(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMenuBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void readMenuBook_missingMenuItemsList_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readMenuBook("missingMenuItemsListMenuBook.json"));
    }
}

