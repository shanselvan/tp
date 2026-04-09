package seedu.homechef.logic.parser;

import static java.util.Objects.requireNonNull;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.EditCommand;
import seedu.homechef.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.order.DietTag;
import seedu.homechef.model.order.PaymentInfo;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FOOD, PREFIX_CUSTOMER, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_DATE, PREFIX_QUANTITY, PREFIX_TAG,
                        PREFIX_BANK_PAYMENT, PREFIX_PAYNOW_PAYMENT, PREFIX_CASH_PAYMENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FOOD, PREFIX_CUSTOMER, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS,
                PREFIX_DATE);

        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();

        if (argMultimap.getValue(PREFIX_FOOD).isPresent()) {
            editOrderDescriptor.setFood(ParserUtil.parseFood(argMultimap.getValue(PREFIX_FOOD).get()));
        }
        if (argMultimap.getValue(PREFIX_CUSTOMER).isPresent()) {
            editOrderDescriptor.setCustomer(ParserUtil.parseCustomer(argMultimap.getValue(PREFIX_CUSTOMER).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editOrderDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editOrderDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editOrderDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editOrderDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editOrderDescriptor.setQuantity(
                    ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editOrderDescriptor::setTags);

        Optional<String> bankPayment = argMultimap.getValue(PREFIX_BANK_PAYMENT);
        Optional<String> payNowPayment = argMultimap.getValue(PREFIX_PAYNOW_PAYMENT);
        Optional<String> cashPayment = argMultimap.getValue(PREFIX_CASH_PAYMENT);
        int paymentPrefixCount = countPresent(bankPayment, payNowPayment, cashPayment);
        if (paymentPrefixCount > 1) {
            throw new ParseException(ParserUtil.MESSAGE_MULTIPLE_PAYMENT_PREFIXES);
        }
        if (cashPayment.isPresent()) {
            Optional<PaymentInfo> parsedCashPayment = ParserUtil.parseCashPayment(cashPayment.get());
            if (parsedCashPayment.isPresent()) {
                editOrderDescriptor.setPaymentInfo(parsedCashPayment.get());
            } else {
                editOrderDescriptor.clearPaymentInfo();
            }
        } else {
            Optional<PaymentInfo> paymentInfo = ParserUtil.parsePaymentInfo(
                    bankPayment, payNowPayment, Optional.empty());
            paymentInfo.ifPresent(editOrderDescriptor::setPaymentInfo);
        }

        if (!editOrderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editOrderDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<DietTag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<DietTag>} containing zero tags.
     */
    private Optional<Set<DietTag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    @SafeVarargs
    private static int countPresent(Optional<String>... values) {
        int count = 0;
        for (Optional<String> value : values) {
            if (value.isPresent()) {
                count++;
            }
        }
        return count;
    }

}
