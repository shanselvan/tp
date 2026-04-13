package seedu.homechef;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.homechef.commons.exceptions.DataLoadingException;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.menu.ReadOnlyMenuBook;
import seedu.homechef.storage.JsonHomeChefStorage;
import seedu.homechef.storage.JsonMenuBookStorage;
import seedu.homechef.storage.JsonUserPrefsStorage;
import seedu.homechef.storage.StorageManager;

public class MainAppTest {

    @TempDir
    public Path temporaryFolder;

    private MainApp app;

    @BeforeEach
    public void setUp() {
        app = new MainApp();
    }

    @Test
    public void initModelManager_corruptHomeChef_setsOrderWarning() {
        JsonHomeChefStorage corruptHomeChef = new JsonHomeChefStorage(temporaryFolder.resolve("hc.json")) {
            @Override
            public Optional<ReadOnlyHomeChef> readHomeChef(Path filePath) throws DataLoadingException {
                throw new DataLoadingException(new Exception("corrupt"));
            }
        };
        StorageManager storage = new StorageManager(
                corruptHomeChef,
                new JsonMenuBookStorage(temporaryFolder.resolve("menu.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("prefs.json")));

        app.initModelManager(storage, new UserPrefs());

        assertEquals("Order data file is corrupted and could not be loaded."
                + " Starting with an empty order list.", app.startupWarning);
    }

    @Test
    public void initModelManager_corruptMenuBook_setsMenuWarning() {
        JsonMenuBookStorage corruptMenu = new JsonMenuBookStorage(temporaryFolder.resolve("menu.json")) {
            @Override
            public Optional<ReadOnlyMenuBook> readMenuBook(Path filePath) throws DataLoadingException {
                throw new DataLoadingException(new Exception("corrupt"));
            }
        };
        StorageManager storage = new StorageManager(
                new JsonHomeChefStorage(temporaryFolder.resolve("hc.json")),
                corruptMenu,
                new JsonUserPrefsStorage(temporaryFolder.resolve("prefs.json")));

        app.initModelManager(storage, new UserPrefs());

        assertEquals("Menu data file is corrupted and could not be loaded."
                + " Starting with an empty menu.", app.startupWarning);
    }

    @Test
    public void initModelManager_bothCorrupt_setsCombinedWarning() {
        JsonHomeChefStorage corruptHomeChef = new JsonHomeChefStorage(temporaryFolder.resolve("hc.json")) {
            @Override
            public Optional<ReadOnlyHomeChef> readHomeChef(Path filePath) throws DataLoadingException {
                throw new DataLoadingException(new Exception("corrupt"));
            }
        };
        JsonMenuBookStorage corruptMenu = new JsonMenuBookStorage(temporaryFolder.resolve("menu.json")) {
            @Override
            public Optional<ReadOnlyMenuBook> readMenuBook(Path filePath) throws DataLoadingException {
                throw new DataLoadingException(new Exception("corrupt"));
            }
        };
        StorageManager storage = new StorageManager(
                corruptHomeChef, corruptMenu,
                new JsonUserPrefsStorage(temporaryFolder.resolve("prefs.json")));

        app.initModelManager(storage, new UserPrefs());

        assertTrue(app.startupWarning.contains("Order data file"));
        assertTrue(app.startupWarning.contains("Menu data file"));
        assertTrue(app.startupWarning.contains("\n"));
    }

    @Test
    public void initModelManager_noCorruption_warningRemainsEmpty() {
        StorageManager storage = new StorageManager(
                new JsonHomeChefStorage(temporaryFolder.resolve("hc.json")),
                new JsonMenuBookStorage(temporaryFolder.resolve("menu.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("prefs.json")));

        app.initModelManager(storage, new UserPrefs());

        assertEquals("", app.startupWarning);
    }
}
