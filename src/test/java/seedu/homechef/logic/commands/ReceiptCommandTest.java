package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.ReceiptUtil;
import seedu.homechef.logic.Messages;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.menu.MenuBook;
import seedu.homechef.model.order.Order;

/**
 * Contains integration tests and unit tests for {@code ReceiptCommand}.
 */
public class ReceiptCommandTest {

    @TempDir
    public Path tempDir;

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Model model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());
        Path homeChefFilePath = tempDir.resolve("data").resolve("homechef.json");
        model.setHomeChefFilePath(homeChefFilePath);

        Order orderToReceipt = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        ReceiptCommand receiptCommand = new ReceiptCommand(INDEX_FIRST_ORDER);

        Path expectedReceiptPath = ReceiptUtil.buildReceiptPath(homeChefFilePath, orderToReceipt);
        String expectedFeedback = String.format(ReceiptCommand.MESSAGE_SUCCESS, expectedReceiptPath.toAbsolutePath())
                + System.lineSeparator()
                + System.lineSeparator()
                + ReceiptUtil.formatReceipt(orderToReceipt);

        CommandResult result = receiptCommand.execute(model);

        assertEquals(expectedFeedback, result.getFeedbackToUser());
        assertTrue(Files.exists(expectedReceiptPath));
        assertReceiptContains(expectedReceiptPath,
                "HOMECHEF",
                "ORDER RECEIPT",
                String.format("%-20s : %s", "Food Item", orderToReceipt.getFood()),
                String.format("%-20s : $%s", "Price", orderToReceipt.getPrice()),
                String.format("%-20s : %s", "Name", orderToReceipt.getCustomer()),
                String.format("%-20s : %s", "Payment Status", orderToReceipt.getPaymentStatus()));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);

        assertCommandFailure(new ReceiptCommand(outOfBoundIndex), model,
                Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_sameOrderTwice_createsNewSuffixedReceiptFile() throws Exception {
        Model model = new ModelManager(getTypicalHomeChef(), new MenuBook(), new UserPrefs());
        Path homeChefFilePath = tempDir.resolve("data").resolve("homechef.json");
        model.setHomeChefFilePath(homeChefFilePath);

        Order orderToReceipt = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        ReceiptCommand receiptCommand = new ReceiptCommand(INDEX_FIRST_ORDER);

        Path firstPath = ReceiptUtil.buildReceiptPath(homeChefFilePath, orderToReceipt);
        receiptCommand.execute(model);

        Path secondPath = ReceiptUtil.buildReceiptPath(homeChefFilePath, orderToReceipt);
        CommandResult secondResult = receiptCommand.execute(model);

        assertTrue(Files.exists(firstPath));
        assertTrue(Files.exists(secondPath));
        assertNotEquals(firstPath, secondPath);
        assertTrue(secondPath.getFileName().toString().endsWith("_1.txt"));
        assertTrue(secondResult.getFeedbackToUser().contains(secondPath.toAbsolutePath().toString()));
    }

    @Test
    public void equals() {
        ReceiptCommand receiptFirstCommand = new ReceiptCommand(INDEX_FIRST_ORDER);
        ReceiptCommand receiptSecondCommand = new ReceiptCommand(INDEX_SECOND_ORDER);

        assertEquals(new ReceiptCommand(INDEX_FIRST_ORDER), receiptFirstCommand);
        assertNotEquals(1, receiptFirstCommand);
        assertNotEquals(null, receiptFirstCommand);
        assertNotEquals(receiptFirstCommand, receiptSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ReceiptCommand receiptCommand = new ReceiptCommand(targetIndex);
        String expected = ReceiptCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, receiptCommand.toString());
    }

    private static void assertReceiptContains(Path receiptPath, String... expectedSnippets) throws IOException {
        String content = Files.readString(receiptPath);
        for (String expectedSnippet : expectedSnippets) {
            assertTrue(content.contains(expectedSnippet));
        }
    }
}
