package seedu.homechef.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.homechef.commons.core.GuiSettings;
import seedu.homechef.commons.core.LogsCenter;
import seedu.homechef.logic.Logic;
import seedu.homechef.logic.commands.CommandResult;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String MESSAGE_UNEXPECTED_ERROR =
            "An unexpected internal error occurred while executing the command.";
    // Require a minimally visible overlap so disconnected-monitor coordinates are treated as invalid.
    private static final double MIN_VISIBLE_WIDTH = 50;
    private static final double MIN_VISIBLE_HEIGHT = 50;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private OrderListPanel orderListPanel;
    private MenuListPanel menuListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane orderListPanelPlaceholder;

    @FXML
    private StackPane menuListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    /**
     * Returns the primary stage of this window.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window with their respective UI parts.
     */
    void fillInnerParts() {
        orderListPanel = new OrderListPanel(logic.getFilteredOrderList());
        orderListPanelPlaceholder.getChildren().add(orderListPanel.getRoot());

        menuListPanel = new MenuListPanel(logic.getFilteredMenuItemList());
        menuListPanelPlaceholder.getChildren().add(menuListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        applyWindowSize(guiSettings);

        java.awt.Point coordinates = guiSettings.getWindowCoordinates();
        if (coordinates != null
                && isVisibleOnAnyScreen(coordinates.getX(), coordinates.getY(),
                primaryStage.getWidth(), primaryStage.getHeight())) {
            primaryStage.setX(coordinates.getX());
            primaryStage.setY(coordinates.getY());
            return;
        }

        centerOnPrimaryScreen();
    }

    private void applyWindowSize(GuiSettings guiSettings) {
        Rectangle2D largestVisualBounds = getLargestVisualBounds();
        primaryStage.setWidth(Math.min(guiSettings.getWindowWidth(), largestVisualBounds.getWidth()));
        primaryStage.setHeight(Math.min(guiSettings.getWindowHeight(), largestVisualBounds.getHeight()));
    }

    private Rectangle2D getLargestVisualBounds() {
        List<Screen> screens = Screen.getScreens();
        Rectangle2D largest = Screen.getPrimary().getVisualBounds();
        for (Screen screen : screens) {
            Rectangle2D bounds = screen.getVisualBounds();
            if (bounds.getWidth() * bounds.getHeight() > largest.getWidth() * largest.getHeight()) {
                largest = bounds;
            }
        }
        return largest;
    }

    private boolean isVisibleOnAnyScreen(double x, double y, double width, double height) {
        double windowMaxX = x + width;
        double windowMaxY = y + height;

        for (Screen screen : Screen.getScreens()) {
            Rectangle2D bounds = screen.getVisualBounds();
            double overlapWidth = Math.min(windowMaxX, bounds.getMaxX()) - Math.max(x, bounds.getMinX());
            double overlapHeight = Math.min(windowMaxY, bounds.getMaxY()) - Math.max(y, bounds.getMinY());
            if (overlapWidth >= MIN_VISIBLE_WIDTH && overlapHeight >= MIN_VISIBLE_HEIGHT) {
                return true;
            }
        }
        return false;
    }

    private void centerOnPrimaryScreen() {
        Rectangle2D primaryBounds = Screen.getPrimary().getVisualBounds();
        double x = primaryBounds.getMinX() + Math.max(0, (primaryBounds.getWidth() - primaryStage.getWidth()) / 2);
        double y = primaryBounds.getMinY() + Math.max(0, (primaryBounds.getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.setX(x);
        primaryStage.setY(y);
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.getRoot().setIconified(false);
            helpWindow.focus();
        }
    }

    /**
     * Shows the main window.
     */
    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Returns the order list panel.
     */
    public OrderListPanel getOrderListPanel() {
        return orderListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.homechef.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            logger.info("Unexpected error while executing command: " + commandText + ". " + e);
            resultDisplay.setFeedbackToUser(MESSAGE_UNEXPECTED_ERROR);
            throw new CommandException(MESSAGE_UNEXPECTED_ERROR, e);
        }
    }
}
