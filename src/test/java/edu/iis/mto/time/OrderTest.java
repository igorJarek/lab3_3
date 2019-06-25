package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {

    private FakeClock fakeClock;

    @Before
    public void setUp() {
        fakeClock = new FakeClock();
    }

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowOrderExpiredExceptionWhenOrderIsExpired() {
        Order order = new Order(fakeClock);
        order.submit();
        order.confirm();
    }
}
