package seedu.homechef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.homechef.commons.exceptions.DataLoadingException;
import seedu.homechef.model.menu.ReadOnlyMenuBook;

/**
 * Represents a storage for {@link seedu.homechef.model.menu.MenuBook}.
 */
public interface MenuBookStorage {

    /**
     * Returns the file path of the menu data file.
     */
    Path getMenuBookFilePath();

    /**
     * Returns MenuBook data as a {@link ReadOnlyMenuBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyMenuBook> readMenuBook() throws DataLoadingException;

    /**
     * @see #readMenuBook()
     */
    Optional<ReadOnlyMenuBook> readMenuBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyMenuBook} to the storage.
     *
     * @param menuBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMenuBook(ReadOnlyMenuBook menuBook) throws IOException;

    /**
     * @see #saveMenuBook(ReadOnlyMenuBook)
     */
    void saveMenuBook(ReadOnlyMenuBook menuBook, Path filePath) throws IOException;
}
