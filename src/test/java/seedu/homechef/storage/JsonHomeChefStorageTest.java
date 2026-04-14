package seedu.homechef.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalOrders.ALICE;
import static seedu.homechef.testutil.TypicalOrders.HOON;
import static seedu.homechef.testutil.TypicalOrders.IDA;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.homechef.commons.exceptions.DataLoadingException;
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.ReadOnlyHomeChef;

public class JsonHomeChefStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonHomeChefStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readHomeChef_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readHomeChef(null));
    }

    private java.util.Optional<ReadOnlyHomeChef> readHomeChef(String filePath) throws Exception {
        return new JsonHomeChefStorage(Paths.get(filePath)).readHomeChef(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readHomeChef("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readHomeChef("notJsonFormatHomeChef.json"));
    }

    @Test
    public void readHomeChef_invalidOrderHomeChef_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHomeChef("invalidOrderHomeChef.json"));
    }

    @Test
    public void readHomeChef_invalidAndValidOrderHomeChef_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHomeChef("invalidAndValidOrderHomeChef.json"));
    }

    @Test
    public void readHomeChef_missingOrdersListHomeChef_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHomeChef("missingOrdersListHomeChef.json"));
    }

    @Test
    public void readAndSaveHomeChef_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempHomeChef.json");
        HomeChef original = getTypicalHomeChef();
        JsonHomeChefStorage jsonHomeChefStorage = new JsonHomeChefStorage(filePath);

        // Save in new file and read back
        jsonHomeChefStorage.saveHomeChef(original, filePath);
        ReadOnlyHomeChef readBack = jsonHomeChefStorage.readHomeChef(filePath).get();
        assertEquals(original, new HomeChef(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addOrder(HOON);
        original.removeOrder(ALICE);
        jsonHomeChefStorage.saveHomeChef(original, filePath);
        readBack = jsonHomeChefStorage.readHomeChef(filePath).get();
        assertEquals(original, new HomeChef(readBack));

        // Save and read without specifying file path
        original.addOrder(IDA);
        jsonHomeChefStorage.saveHomeChef(original); // file path not specified
        readBack = jsonHomeChefStorage.readHomeChef().get(); // file path not specified
        assertEquals(original, new HomeChef(readBack));

    }

    @Test
    public void saveHomeChef_nullHomeChef_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHomeChef(null, "SomeFile.json"));
    }

    /**
     * Saves {@code homeChef} at the specified {@code filePath}.
     */
    private void saveHomeChef(ReadOnlyHomeChef homeChef, String filePath) {
        try {
            new JsonHomeChefStorage(Paths.get(filePath))
                    .saveHomeChef(homeChef, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveHomeChef_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHomeChef(new HomeChef(), null));
    }
}
