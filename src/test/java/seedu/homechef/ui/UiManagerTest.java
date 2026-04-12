package seedu.homechef.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.homechef.logic.LogicManager;
import seedu.homechef.model.ModelManager;
import seedu.homechef.storage.JsonHomeChefStorage;
import seedu.homechef.storage.JsonMenuBookStorage;
import seedu.homechef.storage.JsonUserPrefsStorage;
import seedu.homechef.storage.StorageManager;

public class UiManagerTest {

    @TempDir
    public Path temporaryFolder;

    @Test
    public void constructor_withEmptyWarning_doesNotThrow() {
        StorageManager storage = buildStorage();
        LogicManager logic = new LogicManager(new ModelManager(), storage);

        assertDoesNotThrow(() -> new UiManager(logic, ""));
    }

    @Test
    public void constructor_withNonEmptyWarning_doesNotThrow() {
        StorageManager storage = buildStorage();
        LogicManager logic = new LogicManager(new ModelManager(), storage);

        assertDoesNotThrow(() -> new UiManager(logic, "Order data file is corrupted."));
    }

    private StorageManager buildStorage() {
        return new StorageManager(
                new JsonHomeChefStorage(temporaryFolder.resolve("hc.json")),
                new JsonMenuBookStorage(temporaryFolder.resolve("menu.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("prefs.json")));
    }
}
