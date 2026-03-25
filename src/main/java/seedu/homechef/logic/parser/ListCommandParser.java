package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYMENT_STATUS;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Optional;

import seedu.homechef.logic.commands.ListCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.PaymentStatus;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DATE, PREFIX_CUSTOMER, PREFIX_FOOD, PREFIX_PHONE,
                PREFIX_COMPLETION_STATUS, PREFIX_PAYMENT_STATUS);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE, PREFIX_CUSTOMER, PREFIX_FOOD, PREFIX_PHONE,
                PREFIX_COMPLETION_STATUS, PREFIX_PAYMENT_STATUS);

        Optional<String> dateArg = argMultimap.getValue(PREFIX_DATE);
        Optional<String> customerArg = argMultimap.getValue(PREFIX_CUSTOMER);
        Optional<String> foodArg = argMultimap.getValue(PREFIX_FOOD);
        Optional<String> phoneArg = argMultimap.getValue(PREFIX_PHONE);
        Optional<String> completionArg = argMultimap.getValue(PREFIX_COMPLETION_STATUS);
        Optional<String> paymentArg = argMultimap.getValue(PREFIX_PAYMENT_STATUS);


        // No recognized prefixes -> keep old behaviour: list everything
        if (dateArg.isEmpty() && customerArg.isEmpty() && foodArg.isEmpty() && phoneArg.isEmpty()
                && completionArg.isEmpty() && paymentArg.isEmpty()) {
            return new ListCommand();
        }

        ListCommand.ListFilterDescriptor descriptor = new ListCommand.ListFilterDescriptor();

        if (dateArg.isPresent()) {
            Date date = ParserUtil.parseDate(dateArg.get());
            descriptor.setDate(date);
        }

        if (customerArg.isPresent()) {
            String trimmed = customerArg.get().trim();
            if (trimmed.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            descriptor.setCustomerQuery(trimmed);
        }

        if (foodArg.isPresent()) {
            String trimmed = foodArg.get().trim();
            if (trimmed.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            descriptor.setFoodQuery(trimmed);
        }

        if (phoneArg.isPresent()) {
            String trimmed = phoneArg.get().trim();
            if (trimmed.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            descriptor.setPhoneQuery(trimmed);
        }

        if (completionArg.isPresent()) {
            descriptor.setCompletionStatus(CompletionStatus.fromString(completionArg.get()));
        }

        if (paymentArg.isPresent()) {
            descriptor.setPaymentStatus(PaymentStatus.fromString(paymentArg.get()));
        }

        return new ListCommand(descriptor);
    }
}
