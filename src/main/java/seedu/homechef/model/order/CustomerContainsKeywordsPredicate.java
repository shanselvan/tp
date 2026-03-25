package seedu.homechef.model.order;

import java.util.List;
import java.util.function.Predicate;

import seedu.homechef.commons.util.StringUtil;
import seedu.homechef.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Order}'s {@code Customer} matches any of the keywords given.
 */
public class CustomerContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;

    public CustomerContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Order order) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(order.getCustomer().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerContainsKeywordsPredicate otherCustomerContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherCustomerContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
