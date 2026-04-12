package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_BANK_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CASH_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYNOW_PAYMENT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.homechef.logic.commands.AddCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.common.Food;
import seedu.homechef.model.common.Price;
import seedu.homechef.model.order.Address;
import seedu.homechef.model.order.CompletionStatus;
import seedu.homechef.model.order.Customer;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.DietTag;
import seedu.homechef.model.order.Email;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.order.PaymentStatus;
import seedu.homechef.model.order.Phone;
import seedu.homechef.model.order.Quantity;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final String PLACEHOLDER_PRICE = "0.01";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FOOD, PREFIX_CUSTOMER, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_DATE, PREFIX_TAG, PREFIX_QUANTITY,
                        PREFIX_BANK_PAYMENT, PREFIX_PAYNOW_PAYMENT, PREFIX_CASH_PAYMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_FOOD, PREFIX_CUSTOMER, PREFIX_ADDRESS,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FOOD, PREFIX_CUSTOMER, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_DATE, PREFIX_QUANTITY,
                PREFIX_BANK_PAYMENT, PREFIX_PAYNOW_PAYMENT, PREFIX_CASH_PAYMENT);
        Food food = ParserUtil.parseFood(argMultimap.getValue(PREFIX_FOOD).get());
        Customer customer = ParserUtil.parseCustomer(argMultimap.getValue(PREFIX_CUSTOMER).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        // Initialise every Order as "Pending"
        CompletionStatus completionStatus = CompletionStatus.PENDING;
        // Initialise every Order as unpaid
        PaymentStatus paymentStatus = PaymentStatus.UNPAID;
        Set<DietTag> dietTagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Optional<PaymentInfo> paymentInfo = ParserUtil.parsePaymentInfo(
                argMultimap.getValue(PREFIX_BANK_PAYMENT),
                argMultimap.getValue(PREFIX_PAYNOW_PAYMENT),
                argMultimap.getValue(PREFIX_CASH_PAYMENT));
        Quantity quantity = argMultimap.getValue(PREFIX_QUANTITY).isPresent()
                            ? ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get())
                            : new Quantity(1);
        // Price is resolved from the menu item at command execution time; use a placeholder here.
        Price placeholderPrice = new Price(PLACEHOLDER_PRICE);
        Order order = new Order(food, customer, phone, email, address, date,
                completionStatus, paymentStatus, dietTagList, quantity, placeholderPrice, paymentInfo);

        return new AddCommand(order);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
