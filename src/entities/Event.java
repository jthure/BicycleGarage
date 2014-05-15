package entities;

import java.util.Date;

public abstract class Event {
	protected Date occurred;
	String memberPID;

	public Event(String memberPID) {
		occurred = new Date();
	}
}
