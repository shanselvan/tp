package seedu.homechef.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.homechef.commons.core.LogsCenter;
import seedu.homechef.commons.exceptions.DataLoadingException;
import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.commons.util.FileUtil;
import seedu.homechef.commons.util.JsonUtil;
import seedu.homechef.model.menu.ReadOnlyMenuBook;

/**
 * A class to access MenuBook data stored as a JSON file on the hard disk.
 */
public class JsonMenuBookStorage implements MenuBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMenuBookStorage.class);

    private Path filePath;

    /**
     * Creates a {@code JsonMenuBookStorage} with the given file path.
     */
    public JsonMenuBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getMenuBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMenuBook> readMenuBook() throws DataLoadingException {
        return readMenuBook(filePath);
    }

    /**
     * Similar to {@link #readMenuBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    @Override
    public Optional<ReadOnlyMenuBook> readMenuBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableMenuBook> jsonMenuBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableMenuBook.class);
        if (!jsonMenuBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMenuBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveMenuBook(ReadOnlyMenuBook menuBook) throws IOException {
        saveMenuBook(menuBook, filePath);
    }

    /**
     * Similar to {@link #saveMenuBook(ReadOnlyMenuBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveMenuBook(ReadOnlyMenuBook menuBook, Path filePath) throws IOException {
        requireNonNull(menuBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMenuBook(menuBook), filePath);
    }
}
