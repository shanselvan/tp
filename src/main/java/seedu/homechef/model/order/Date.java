package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents an Order's fulfillment date in HomeChef.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format dd-MM-yyyy and must be a valid calendar date.";
    public static final String URGENCY_CONSTRAINTS =
            "Urgency should be Normal, Urgent, or Overdue.";

    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);

    public static final String NORMAL = "Normal";
    public static final String URGENT = "Urgent";
    public static final String OVERDUE = "Overdue";

    public static final int URGENT_PERIOD_DAYS = 3;

    private final LocalDate value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = LocalDate.parse(date, FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public int compareTo(Date other) {
        return this.value.compareTo(other.value);
    }

    /**
     * Returns true if this date is before the current day.
     */
    public boolean isBeforeToday() {
        return value.isBefore(LocalDate.now());
    }

    /**
     * Returns a string representation of the urgency status of a Date.
     * A Date is considered urgent if it is within the specified URGENT_PERIOD_DAYS.
     */
    public String getUrgency() {
        if (isBeforeToday()) {
            return OVERDUE;
        }
        if (value.isBefore(LocalDate.now().plusDays(URGENT_PERIOD_DAYS))) {
            return URGENT;
        }
        return NORMAL;
    }

    /**
     * Returns true if a given urgency is a valid urgency status.
     */
    public static boolean isValidUrgency(String test) {
        switch (test) {
        case NORMAL, URGENT, OVERDUE:
            return true;
        default:
            return false;
        }
    }

    @Override
    public String toString() {
        return value.format(FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
