package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.logic.Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.ReceiptUtil;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.order.Order;

/**
 * Generates a receipt file for an order identified by its displayed index.
 */
public class ReceiptCommand extends Command {

    public static final String COMMAND_WORD = "receipt";
    public static final String COMMAND_ALIAS = "rec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates a receipt file for the order identified by the "
            + "index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Receipt generated at: %1$s";
    public static final String MESSAGE_FAILURE = "Could not generate receipt: %1$s";
    public static final String MESSAGE_ONLY_PAID_ALLOWED =
            "Receipts can only be generated for orders with payment status 'Paid'.";

    private final Index targetIndex;

    public ReceiptCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToReceipt = lastShownList.get(targetIndex.getZeroBased());
        if (!orderToReceipt.getPaymentStatus().isPaid()) {
            throw new CommandException(MESSAGE_ONLY_PAID_ALLOWED);
        }
        Path outputPath = ReceiptUtil.buildReceiptPath(model.getHomeChefFilePath(), orderToReceipt);

        try {
            ReceiptUtil.writeReceipt(outputPath, orderToReceipt);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_FAILURE, ioe.getMessage()), ioe);
        }

        String receiptContent = ReceiptUtil.formatReceipt(orderToReceipt);
        String feedback = String.format(MESSAGE_SUCCESS, outputPath.toAbsolutePath())
                + System.lineSeparator()
                + System.lineSeparator()
                + receiptContent;
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ReceiptCommand)) {
            return false;
        }

        ReceiptCommand otherReceiptCommand = (ReceiptCommand) other;
        return targetIndex.equals(otherReceiptCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
