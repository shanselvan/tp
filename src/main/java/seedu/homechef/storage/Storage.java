package seedu.homechef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.homechef.commons.exceptions.DataLoadingException;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.ReadOnlyUserPrefs;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.menu.ReadOnlyMenuBook;

/**
 * API of the Storage component
 */
public interface Storage extends HomeChefStorage, MenuBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getHomeChefFilePath();

    @Override
    Optional<ReadOnlyHomeChef> readHomeChef() throws DataLoadingException;

    @Override
    void saveHomeChef(ReadOnlyHomeChef homeChef) throws IOException;

    @Override
    Path getMenuBookFilePath();

    @Override
    Optional<ReadOnlyMenuBook> readMenuBook() throws DataLoadingException;

    @Override
    void saveMenuBook(ReadOnlyMenuBook menuBook) throws IOException;

}
