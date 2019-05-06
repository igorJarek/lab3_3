package edu.iis.mto.time;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import java.time.Clock;

public class Order {
	private static final int VALID_PERIOD_HOURS = 24;
	private State orderState;
	private List<OrderItem> items = new ArrayList<OrderItem>();
	private DateTime subbmitionDate;
	private Clock fakeClock;

	public Order(Clock fakeClock) {
		orderState = State.CREATED;
		this.fakeClock = fakeClock;
	}

	public void addItem(OrderItem item) {
		requireState(State.CREATED, State.SUBMITTED);

		items.add(item);
		orderState = State.CREATED;

	}

	public void submit(DateTime sumbitData) {
		requireState(State.CREATED);

		orderState = State.SUBMITTED;
		subbmitionDate = sumbitData;

	}

	public void confirm() {
		requireState(State.SUBMITTED);
		long milisecondsElapsedAfterSubmittion = fakeClock.millis() - subbmitionDate.getMillis();
		double hoursElapsedAfterSubmittion = milisecondsElapsedAfterSubmittion / 3600000.0;
		if(hoursElapsedAfterSubmittion > VALID_PERIOD_HOURS){
			orderState = State.CANCELLED;
			throw new OrderExpiredException();
		}
	}

	public void realize() {
		requireState(State.CONFIRMED);
		orderState = State.REALIZED;
	}

	State getOrderState() {
		return orderState;
	}
	
	private void requireState(State... allowedStates) {
		for (State allowedState : allowedStates) {
			if (orderState == allowedState)
				return;
		}

		throw new OrderStateException("order should be in state "
				+ allowedStates + " to perform required  operation, but is in "
				+ orderState);

	}

	public static enum State {
		CREATED, SUBMITTED, CONFIRMED, REALIZED, CANCELLED
	}
}
