package seedu.homechef.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.commons.core.LogsCenter;
import seedu.homechef.logic.commands.Command;
import seedu.homechef.logic.commands.CommandResult;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.logic.parser.HomeChefParser;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.Model;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.menu.MenuItem;
import seedu.homechef.model.order.Order;
import seedu.homechef.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final HomeChefParser homeChefParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        homeChefParser = new HomeChefParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = homeChefParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveHomeChef(model.getHomeChef());
            storage.saveMenuBook(model.getMenuBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyHomeChef getHomeChef() {
        return model.getHomeChef();
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return model.getFilteredOrderList();
    }

    @Override
    public ObservableList<MenuItem> getFilteredMenuItemList() {
        return model.getFilteredMenuItemList();
    }

    @Override
    public Path getHomeChefFilePath() {
        return model.getHomeChefFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
