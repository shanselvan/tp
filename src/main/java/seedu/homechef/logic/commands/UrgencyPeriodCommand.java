package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;

/**
 * Deletes an order identified using it's displayed index from the HomeChef.
 */
public class UrgencyPeriodCommand extends Command {

    public static final String COMMAND_WORD = "urgency";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the number of days before a due date, which the app will flag the Order as urgent.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_URGENCY_ORDER_SUCCESS =
            "Number of days before due date considered as urgent: %1$s";

    private final int value;

    public UrgencyPeriodCommand(int value) {
        this.value = value;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_URGENCY_ORDER_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UrgencyPeriodCommand)) {
            return false;
        }

        UrgencyPeriodCommand otherDeleteCommand = (UrgencyPeriodCommand) other;
        return value == otherDeleteCommand.value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("urgencyPeriod", value)
                .toString();
    }
}
