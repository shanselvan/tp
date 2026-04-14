package seedu.homechef.commons.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.homechef.commons.core.Config;
import seedu.homechef.commons.exceptions.DataLoadingException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    /**
     * Returns the {@code Config} object from the given file, or {@code Optional.empty()} if the file is not found.
     *
     * @param configFilePath Path to the config file.
     * @throws DataLoadingException if loading the config file failed.
     */
    public static Optional<Config> readConfig(Path configFilePath) throws DataLoadingException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    /**
     * Saves the given {@code Config} object to the specified file.
     *
     * @param config Config object to save.
     * @param configFilePath Path to the config file.
     * @throws IOException if there was an error during writing to the file.
     */
    public static void saveConfig(Config config, Path configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
