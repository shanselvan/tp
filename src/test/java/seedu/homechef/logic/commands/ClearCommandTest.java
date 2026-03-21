package seedu.homechef.logic.commands;

import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import org.junit.jupiter.api.Test;

import seedu.homechef.model.HomeChef;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.menu.MenuBook;

public class ClearCommandTest {

    @Test
    public void execute_emptyHomeChef_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyHomeChef_success() {
        Model model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());
        expectedModel.setHomeChef(new HomeChef());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
