package seedu.homechef.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.homechef.commons.core.LogsCenter;
import seedu.homechef.model.menu.MenuItem;

/**
 * Panel containing the list of menu items.
 */
public class MenuListPanel extends UiPart<Region> {

    private static final String FXML = "MenuListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MenuListPanel.class);

    @FXML
    private ListView<MenuItem> menuListView;

    /**
     * Creates a {@code MenuListPanel} with the given {@code ObservableList}.
     */
    public MenuListPanel(ObservableList<MenuItem> menuList) {
        super(FXML);
        menuListView.setItems(menuList);
        menuListView.setCellFactory(listView -> new MenuListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays a {@code MenuItem} using a {@code MenuCard}.
     */
    private class MenuListViewCell extends ListCell<MenuItem> {
        @Override
        protected void updateItem(MenuItem menuItem, boolean empty) {
            super.updateItem(menuItem, empty);

            if (empty || menuItem == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MenuCard(menuItem, getIndex() + 1).getRoot());
            }
        }
    }
}
