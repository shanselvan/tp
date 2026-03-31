package seedu.homechef.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_BANK_NAME;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYMENT_METHOD;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PAYMENT_REF;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_WALLET_PROVIDER;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.logic.commands.EditCommand;
import seedu.homechef.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.order.PaymentInfo;
import seedu.homechef.model.tag.DietTag;

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
                        PREFIX_ADDRESS, PREFIX_DATE, PREFIX_TAG,
                        PREFIX_PAYMENT_METHOD, PREFIX_PAYMENT_REF, PREFIX_BANK_NAME, PREFIX_WALLET_PROVIDER);

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
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editOrderDescriptor::setTags);

        Optional<PaymentInfo> paymentInfo = ParserUtil.parsePaymentInfo(
                argMultimap.getValue(PREFIX_PAYMENT_METHOD),
                argMultimap.getValue(PREFIX_PAYMENT_REF),
                argMultimap.getValue(PREFIX_BANK_NAME),
                argMultimap.getValue(PREFIX_WALLET_PROVIDER));
        paymentInfo.ifPresent(editOrderDescriptor::setPaymentInfo);

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

}
