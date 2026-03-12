package seedu.homechef.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.order.Order;

public class SampleDataUtilTest {

    @Test
    public void getSampleOrders_returnsNonEmptyArray() {
        Order[] orders = SampleDataUtil.getSampleOrders();
        assertTrue(orders.length > 0);
    }

    @Test
    public void getSampleOrders_allOrdersNonNull() {
        for (Order order : SampleDataUtil.getSampleOrders()) {
            assertNotNull(order);
        }
    }

    @Test
    public void getSampleHomeChef_containsAllSampleOrders() {
        Order[] sampleOrders = SampleDataUtil.getSampleOrders();
        ReadOnlyHomeChef homeChef = SampleDataUtil.getSampleHomeChef();
        assertEquals(sampleOrders.length, homeChef.getOrderList().size());
    }

    @Test
    public void getTagSet_emptyInput_returnsEmptySet() {
        assertTrue(SampleDataUtil.getTagSet().isEmpty());
    }

    @Test
    public void getTagSet_singleTag_returnsSetWithOneElement() {
        assertEquals(1, SampleDataUtil.getTagSet("friends").size());
    }

    @Test
    public void getTagSet_multipleTags_returnsSetWithAllElements() {
        assertEquals(2, SampleDataUtil.getTagSet("friends", "family").size());
    }
}
