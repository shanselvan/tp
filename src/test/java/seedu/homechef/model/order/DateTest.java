package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DateTest {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final int dayWithinUrgentPeriod = Date.URGENT_PERIOD_DAYS - 1;
    private static final int dayOutsideUrgentPeriod1 = Date.URGENT_PERIOD_DAYS + 1;
    private static final int dayOutsideUrgentPeriod2 = Date.URGENT_PERIOD_DAYS + 100;
    private static final LocalDate overdueLocalDate1 = LocalDate.now().minusDays(1);
    private static final LocalDate overdueLocalDate2 = LocalDate.now().minusDays(99);
    private static final LocalDate urgentLocalDate1 = LocalDate.now();
    private static final LocalDate urgentLocalDate2 = LocalDate.now().plusDays(dayWithinUrgentPeriod);
    private static final LocalDate normalLocalDate1 = LocalDate.now().plusDays(dayOutsideUrgentPeriod1);
    private static final LocalDate normalLocalDate2 = LocalDate.now().plusDays(dayOutsideUrgentPeriod2);
    private static final LocalDate normalLocalDate3 = LocalDate.now().plusDays(Date.URGENT_PERIOD_DAYS);
    private static final Date overdueDate1 = new Date(overdueLocalDate1.format(FORMATTER));
    private static final Date overdueDate2 = new Date(overdueLocalDate2.format(FORMATTER));
    private static final Date urgentDate1 = new Date(urgentLocalDate1.format(FORMATTER));
    private static final Date urgentDate2 = new Date(urgentLocalDate2.format(FORMATTER));
    private static final Date normalDate1 = new Date(normalLocalDate1.format(FORMATTER));
    private static final Date normalDate2 = new Date(normalLocalDate2.format(FORMATTER));
    private static final Date normalDate3 = new Date(normalLocalDate3.format(FORMATTER));

    @Test
    public void constructor_null_throwsNullPointerException() {
        // EP: null date
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        // EP: invalid date
        String invalidDate = "";

        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void getUrgency_overdueDate() {
        // EP: overdue date
        assertEquals(overdueDate1.getUrgency(), Date.OVERDUE); // 1 day overdue, boundary value
        assertEquals(overdueDate2.getUrgency(), Date.OVERDUE);

        // EP: urgent date
        assertNotEquals(urgentDate1.getUrgency(), Date.OVERDUE); // current day, boundary value
        assertNotEquals(urgentDate2.getUrgency(), Date.OVERDUE); // 1 day from urgent period limit

        // EP: normal date
        assertNotEquals(normalDate1.getUrgency(), Date.OVERDUE); // 2 days to urgent period
        assertNotEquals(normalDate2.getUrgency(), Date.OVERDUE); // 100 days to urgent period
        assertNotEquals(normalDate3.getUrgency(), Date.OVERDUE); // 1 day to urgent period, boundary value
    }

    @Test
    public void getUrgency_urgentDate() {
        // EP: overdue date
        assertNotEquals(overdueDate1.getUrgency(), Date.URGENT); // 1 day overdue, boundary value
        assertNotEquals(overdueDate2.getUrgency(), Date.URGENT); // 99 days overdue

        // EP: urgent date
        assertEquals(urgentDate1.getUrgency(), Date.URGENT); // current day, boundary value
        assertEquals(urgentDate2.getUrgency(), Date.URGENT); // 1 day from urgent period limit

        // EP: normal date
        assertNotEquals(normalDate1.getUrgency(), Date.URGENT); // 2 days to urgent period
        assertNotEquals(normalDate2.getUrgency(), Date.URGENT); // 100 days to urgent period
        assertNotEquals(normalDate3.getUrgency(), Date.URGENT); // 1 day to urgent period, boundary value
    }

    @Test
    public void getUrgency_normalDate() {
        // EP: overdue date
        assertNotEquals(overdueDate1.getUrgency(), Date.NORMAL);
        assertNotEquals(overdueDate2.getUrgency(), Date.NORMAL);

        // EP: urgent date
        assertNotEquals(urgentDate1.getUrgency(), Date.NORMAL); // current day, boundary value
        assertNotEquals(urgentDate2.getUrgency(), Date.NORMAL); // 1 day from urgent period limit

        // EP: normal date
        assertEquals(normalDate1.getUrgency(), Date.NORMAL); // 2 days to urgent period
        assertEquals(normalDate2.getUrgency(), Date.NORMAL); // 100 days to urgent period
        assertEquals(normalDate3.getUrgency(), Date.NORMAL); // 1 day to urgent period, boundary value
    }

    @Test
    public void isValidDate() {
        // EP: null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // EP: invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("2026-12-01")); // wrong format
        assertFalse(Date.isValidDate("1-12-2026")); // wrong format
        assertFalse(Date.isValidDate("32-01-2026")); // invalid day
        assertFalse(Date.isValidDate("01-13-2026")); // invalid month
        assertFalse(Date.isValidDate("31-02-2026")); // invalid day/month combination
        assertFalse(Date.isValidDate("29-02-2025")); // non-leap year Feb 29
        assertFalse(Date.isValidDate("abc")); // non-numeric input

        // EP: valid date
        assertTrue(Date.isValidDate("01-01-2026"));
        assertTrue(Date.isValidDate("09-12-2026"));
        assertTrue(Date.isValidDate("29-02-2024")); // leap year
        assertTrue(Date.isValidDate("31-12-2030"));
    }

    @Test
    public void isValidUrgency() {
        // EP: null urgency
        assertThrows(NullPointerException.class, () -> Date.isValidUrgency(null));

        // EP: invalid urgency
        assertFalse(Date.isValidUrgency("")); // empty string, boundary value
        assertFalse(Date.isValidUrgency(" "));
        assertFalse(Date.isValidUrgency("Invalid Urgency"));
        assertFalse(Date.isValidUrgency("Noormal")); // common misspelling
        assertFalse(Date.isValidUrgency("0verdue")); // common misspelling

        // EP: valid urgency
        assertTrue(Date.isValidUrgency("Normal"));
        assertTrue(Date.isValidUrgency("Urgent"));
        assertTrue(Date.isValidUrgency("Overdue"));
    }

    @Test
    public void equals() {
        Date date = new Date("01-01-2025");

        // EP: same values -> returns true
        assertTrue(date.equals(new Date("01-01-2025")));

        // EP: same object -> returns true
        assertTrue(date.equals(date));

        // EP: null -> returns false
        assertFalse(date.equals(null));

        // EP: different types -> returns false
        assertFalse(date.equals(5.0f));

        // EP: different values -> returns false
        assertFalse(date.equals(new Date("02-01-2026")));
    }

    @Test
    public void hashcode_sameDatesHaveSameHashcode() {
        Date date = new Date("01-01-2025");
        LocalDate localDate = LocalDate.parse("01-01-2025", FORMATTER);

        // EP: same dates
        assertEquals(date.hashCode(), localDate.hashCode());
    }

    @Test
    public void hashcode_differentDatesHaveDifferentHashcode() {
        Date date = new Date("01-01-2025");
        LocalDate localDate = LocalDate.parse("02-01-2025", FORMATTER);

        // EP: different dates
        assertNotEquals(date.hashCode(), localDate.hashCode());
    }

    @Test
    public void hashCode_equalObjectsHaveEqualHashCode() {
        Date date1 = new Date("01-01-2025");
        Date date2 = new Date("01-01-2025");

        // EP: date objects with same dates
        assertEquals(date1.hashCode(), date2.hashCode());

        // EP: same date object
        assertEquals(date1.hashCode(), date1.hashCode());
    }

    @Test
    public void hashcode_differentObjectsHaveDifferentHashcode() {
        Date date1 = new Date("01-01-2025");
        Date date2 = new Date("02-01-2025");

        // EP: different date objects
        assertNotEquals(date1.hashCode(), date2.hashCode());
    }
}
