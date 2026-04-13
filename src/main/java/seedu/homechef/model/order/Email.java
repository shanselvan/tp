package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

/**
 * Represents a Order's email in the HomeChef.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    // reference: https://emailregex.com/
    private static final String SPECIAL_CHARACTERS = "!#$%&'*+/=?^_`{|}~._%\\-";
    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain.tld "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters: "
            + SPECIAL_CHARACTERS + "\n"
            + "2. This is followed by a '@' and then a domain name.\n"
            + "3. The domain name must contain only alphanumeric characters, dots, or hyphens.\n"
            + "4. The email must end with a top-level domain (TLD) of at least 2 alphabetic characters "
            + "(e.g. .com, .org, .io).";

    private static final String LOCAL_PART_REGEX = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~._%\\-]+";
    private static final String DOMAIN_REGEX = "[a-zA-Z0-9.\\-]+";
    private static final String TLD_REGEX = "[a-zA-Z]{2,}";
    public static final String VALIDATION_REGEX = "^" + LOCAL_PART_REGEX + "@" + DOMAIN_REGEX + "\\." + TLD_REGEX + "$";

    private final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
