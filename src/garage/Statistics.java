package garage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import entities.DayEvent;

public class Statistics {
	private static final long day = 86400000;
	Database db;

	public Statistics(Database db) {
		this.db = db;
	}

	private int computeIndex(Date date) {
		if (date.compareTo(new Date()) > 0) {
			date = new Date();
		}
		long ms = date.getTime() - db.creationDate().getTime();
		int index = (int) (ms / day);
		return index;
	}

	private void checkLength() {
		int index = computeIndex(new Date());
		ArrayList<DayEvent> des = db.getDayEvents();
		while (index >= des.size()) {
			DayEvent de = des.get(des.size() - 1);
			de.setDay(new Date(de.getDay().getTime() + day));
			de.setMembersCheckedIn(0);
			des.add(de);
		}
	}

	private DayEvent newDayEvent() {
		checkLength();
		DayEvent newDayEvent = new DayEvent();
		db.getDayEvents().set(computeIndex(newDayEvent.getDay()), newDayEvent);
		memberChange();
		bicycleChange();
		bicyclesInGarageChange();
		return newDayEvent;
	}

	public void memberChange() {
		// checkLength();
		int index = computeIndex(new Date());
		DayEvent dayEvent = db.getDayEvents().get(index);
		if (dayEvent != null) {
			dayEvent.setMembers(db.getMemberSize());
		} else {
			newDayEvent();
		}
	}

	// Test start
	public void memberChange(int days) {
		checkLength();
		int index = computeIndex(new Date(new Date().getTime()
				+ (long) 86400000 * (long) days));
		DayEvent dayEvent = db.getDayEvents().get(index);
		if (dayEvent != null) {
			dayEvent.setMembers(db.getMemberSize());
		} else {
			newDayEvent();
		}
	}

	// Test end

	public void bicycleChange() {
		checkLength();
		int index = computeIndex(new Date());
		DayEvent dayEvent = db.getDayEvents().get(index);
		if (dayEvent != null) {
			dayEvent.setBicycles(db.getBicycleSize());
		} else {
			newDayEvent();
		}
	}

	public void bicyclesInGarageChange() {
		checkLength();
		int index = computeIndex(new Date());
		DayEvent dayEvent = db.getDayEvents().get(index);
		if (dayEvent != null) {
			dayEvent.setBicycles(db.getBicyclesInGarage());
		} else {
			newDayEvent();
		}
	}

	public void userCheckInChange() {
		checkLength();
		int index = computeIndex(new Date());
		DayEvent dayEvent = db.getDayEvents().get(index);
		if (dayEvent != null) {
			dayEvent.incrementMembersCheckedIn();
		} else {
			newDayEvent();
			userCheckInChange();
		}
	}

	public String[][] getInfo(Date startDate, Date endDate) {
		checkLength();
		int startIndex = computeIndex(startDate);
		startIndex = (startIndex < 0) ? 0 : startIndex;
		int endIndex = computeIndex(endDate);
		endIndex = (endIndex < 0) ? 0 : endIndex;
		int size = endIndex - startIndex + 1;
		String[][] matrix = new String[size][5];
		Calendar cal = Calendar.getInstance();
		for (int i = startIndex; i < endIndex + 1; i++) {
			DayEvent day = db.getDayEvents().get(i);
			cal.setTime(day.getDay());
			matrix[i][0] = String.valueOf(day.getDay().getYear()) + "-"
					+ String.valueOf(day.getDay().getMonth()) + "-"
					+ String.valueOf(day.getDay().getDate());
			matrix[i][1] = String.valueOf(day.getMembers());
			matrix[i][2] = String.valueOf(day.getBicycles());
			matrix[i][3] = String.valueOf(day.getMembersCheckedIn());
			matrix[i][4] = String.valueOf(day.getBicyclesInGarage());
		}
		return matrix;
	}
}
