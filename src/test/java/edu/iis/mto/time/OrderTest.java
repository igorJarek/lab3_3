package edu.iis.mto.time;

import org.joda.time.DateTime;

import java.time.Clock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    private DateTime submitData;
    private Clock fakeClock;
    private Order order;

    @Before
    public void setUp() {
        submitData = new DateTime(2000, 1, 1, 0, 0);
        fakeClock = mock(Clock.class);
        order = new Order(fakeClock);
        order.addItem(new OrderItem());
        order.addItem(new OrderItem());
        order.addItem(new OrderItem());
    }

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowOrderExpiredExceptionWhenOrderIsExpired() {
        DateTime nowTime = submitData.plusHours(24).plusSeconds(1);

        when(fakeClock.millis()).thenReturn(nowTime.getMillis());

        order.submit(submitData);
        order.confirm();
    }

    @Test
    public void shouldNotThrowOrderExpiredExceptionWhenOrderIsNotExpired() {
        DateTime nowTime = submitData.plusHours(24);

        when(fakeClock.millis()).thenReturn(nowTime.getMillis());

        order.submit(submitData);
        order.confirm();
    }
}
