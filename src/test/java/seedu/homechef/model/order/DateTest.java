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

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void getUrgency_overdueDate() {
        LocalDate overdueLocalDate = LocalDate.now().minusDays(1);
        Date overdueDate = new Date(overdueLocalDate.format(FORMATTER));
        assertEquals(overdueDate.getUrgency(), Date.OVERDUE);
    }

    @Test
    public void getUrgency_urgentDate() {
        int dayWithinUrgentPeriod = Date.URGENT_PERIOD_DAYS - 1;

        LocalDate urgentLocalDate1 = LocalDate.now();
        LocalDate urgentLocalDate2 = LocalDate.now().plusDays(dayWithinUrgentPeriod);

        Date urgentDate1 = new Date(urgentLocalDate1.format(FORMATTER));
        Date urgentDate2 = new Date(urgentLocalDate2.format(FORMATTER));

        assertEquals(urgentDate1.getUrgency(), Date.URGENT);
        assertEquals(urgentDate2.getUrgency(), Date.URGENT);
    }

    @Test
    public void getUrgency_normalDate() {
        int dayOutsideUrgentPeriod1 = Date.URGENT_PERIOD_DAYS + 1;
        int dayOutsideUrgentPeriod2 = Date.URGENT_PERIOD_DAYS + 100;

        LocalDate normalLocalDate1 = LocalDate.now().plusDays(dayOutsideUrgentPeriod1);
        LocalDate normalLocalDate2 = LocalDate.now().plusDays(dayOutsideUrgentPeriod2);

        Date urgentDate1 = new Date(normalLocalDate1.format(FORMATTER));
        Date urgentDate2 = new Date(normalLocalDate2.format(FORMATTER));

        assertEquals(urgentDate1.getUrgency(), Date.NORMAL);
        assertEquals(urgentDate2.getUrgency(), Date.NORMAL);
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("2026-12-01")); // wrong format
        assertFalse(Date.isValidDate("1-12-2026")); // wrong format
        assertFalse(Date.isValidDate("32-01-2026")); // invalid day
        assertFalse(Date.isValidDate("01-13-2026")); // invalid month
        assertFalse(Date.isValidDate("abc")); // non-numeric input

        // valid date
        assertTrue(Date.isValidDate("01-01-2026"));
        assertTrue(Date.isValidDate("09-12-2026"));
        assertTrue(Date.isValidDate("29-02-2024")); // leap year
        assertTrue(Date.isValidDate("31-12-2030"));
    }

    @Test
    public void isValidUrgency() {
        // null urgency
        assertThrows(NullPointerException.class, () -> Date.isValidUrgency(null));

        // invalid urgency
        assertFalse(Date.isValidUrgency(""));
        assertFalse(Date.isValidUrgency(" "));
        assertFalse(Date.isValidUrgency("Invalid Urgency"));
        assertFalse(Date.isValidUrgency("Noormal"));
        assertFalse(Date.isValidUrgency("0verdue"));

        // valid urgency
        assertTrue(Date.isValidUrgency("Normal"));
        assertTrue(Date.isValidUrgency("Urgent"));
        assertTrue(Date.isValidUrgency("Overdue"));
    }

    @Test
    public void equals() {
        Date date = new Date("01-01-2025");

        // same values -> returns true
        assertTrue(date.equals(new Date("01-01-2025")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new Date("02-01-2026")));
    }

    @Test
    public void hashcode_sameDatesHaveSameHashcode() {
        Date date = new Date("01-01-2025");
        LocalDate localDate = LocalDate.parse("01-01-2025", FORMATTER);

        assertEquals(date.hashCode(), localDate.hashCode());
    }

    @Test
    public void hashcode_differentDatesHaveDifferentHashcode() {
        Date date = new Date("01-01-2025");
        LocalDate localDate = LocalDate.parse("02-01-2025", FORMATTER);

        assertNotEquals(date.hashCode(), localDate.hashCode());
    }

    @Test
    public void hashCode_equalObjectsHaveEqualHashCode() {
        Date date1 = new Date("01-01-2025");
        Date date2 = new Date("01-01-2025");
        assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    public void hashcode_differentObjectsHaveDifferentHashcode() {
        Date date1 = new Date("01-01-2025");
        Date date2 = new Date("02-01-2025");

        assertNotEquals(date1.hashCode(), date2.hashCode());
    }
}
