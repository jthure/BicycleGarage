package garage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import entities.DayEvent;

public class Statistics {
	private static final long day = 86400000;
	Database db;

	/**Creates a new Statistics object which will use the specified databse.
	 * @param db Database that should be used.
	 */
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

	/**
	 * Updates the number of registered members for the current day.
	 */
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


	/**
	 * Updates the number of registered bicyles for the current day.
	 */
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

	/**
	 * Updates the number of bicycles parked in the garage for the current day.
	 */
	public void bicyclesInGarageChange() {
		checkLength();
		int index = computeIndex(new Date());
		DayEvent dayEvent = db.getDayEvents().get(index);
		if (dayEvent != null) {
			dayEvent.setBicyclesInGarage(db.getBicyclesInGarage());
		} else {
			newDayEvent();
			
		}
	}

	/**
	 * Increments the number of members that have been checked in for the current day.
	 */
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

	/**Returns a matrix consisting of: 
	 * By column: the date, number of registered members, number of registered bicycles, number of bicycles parked in the garage and the number of members that have checked.
	 * By row: The column values by date, ranging from the specified dates.
	 * 
	 * @param startDate Start date.
	 * @param endDate End date.
	 * @return
	 */
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
			matrix[i][0] = String.valueOf(day.getDay().getYear() - 100) + "-"
					+ String.valueOf(day.getDay().getMonth() + 1) + "-"
					+ String.valueOf(day.getDay().getDate());
			matrix[i][1] = String.valueOf(day.getMembers());
			matrix[i][2] = String.valueOf(day.getBicycles());
			matrix[i][3] = String.valueOf(day.getBicyclesInGarage());
			matrix[i][4] = String.valueOf(day.getMembersCheckedIn());
		}
		return matrix;
	}
}
