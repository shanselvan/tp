package seedu.homechef.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.model.HomeChef;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonHomeChefStorage homeChefStorage = new JsonHomeChefStorage(getTempFilePath("ab"));
        JsonMenuBookStorage menuBookStorage = new JsonMenuBookStorage(getTempFilePath("menu"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(homeChefStorage, menuBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void homeChefReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonHomeChefStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonHomeChefStorageTest} class.
         */
        HomeChef original = getTypicalHomeChef();
        storageManager.saveHomeChef(original);
        ReadOnlyHomeChef retrieved = storageManager.readHomeChef().get();
        assertEquals(original, new HomeChef(retrieved));
    }

    @Test
    public void getHomeChefFilePath() {
        assertNotNull(storageManager.getHomeChefFilePath());
    }

}
