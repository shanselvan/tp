package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

/**
 * Represents an Order's food name in HomeChef.
 * Guarantees: immutable; is valid as declared in {@link #isValidFood(String)}
 */
public class Food {

    public static final String MESSAGE_CONSTRAINTS =
            "Food name should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the food name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ()\\[\\]-]*";

    private final String foodName;

    /**
     * Constructs a {@code Food}.
     *
     * @param name A valid food name.
     */
    public Food(String name) {
        requireNonNull(name);
        checkArgument(isValidFood(name), MESSAGE_CONSTRAINTS);
        foodName = name;
    }

    /**
     * Returns true if a given string is a valid food name.
     */
    public static boolean isValidFood(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return foodName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Food)) {
            return false;
        }

        Food otherFood = (Food) other;
        return foodName.equals(otherFood.foodName);
    }

    @Override
    public int hashCode() {
        return foodName.hashCode();
    }

}
