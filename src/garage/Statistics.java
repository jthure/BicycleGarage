package garage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.DayEvent;

public class Statistics {
	Database db;
	List<DayEvent> dayEvents;
	Date startDate;

	public Statistics(Database db) {
		this.db = db;
		dayEvents = new ArrayList<>();
		startDate = new Date();
	}

	private int computeIndex(Date date) {
		long ms = date.getTime() - startDate.getTime();
		long divide = 86400000;
		return (int) (ms / divide);
	}

	private DayEvent newDayEvent() {
		DayEvent newDayEvent = new DayEvent();
		dayEvents.set(computeIndex(newDayEvent.getDate()), newDayEvent);
		memberChange();
		bicycleChange();
		bicyclesInGarageChange();
		return newDayEvent;
	}

	public void memberChange() {
		int index = computeIndex(new Date());
		DayEvent dayEvent = dayEvents.get(index);
		if (dayEvent != null) {
			dayEvent.setMembers(db.getMembersSize());
		} else {
			newDayEvent();
		}
	}

	public void bicycleChange() {
		int index = computeIndex(new Date());
		DayEvent dayEvent = dayEvents.get(index);
		if (dayEvent != null) {
			dayEvent.setBicycles(db.getBicyclesSize());
		} else {
			newDayEvent();
		}
	}

	public void bicyclesInGarageChange() {
		int index = computeIndex(new Date());
		DayEvent dayEvent = dayEvents.get(index);
		if (dayEvent != null) {
			dayEvent.setBicycles(db.getBicyclesInGarage());
		} else {
			newDayEvent();
		}
	}

	public void userCheckInChange() {
		int index = computeIndex(new Date());
		DayEvent dayEvent = dayEvents.get(index);
		if (dayEvent != null) {
			dayEvent.incrementMembersCheckedIn();
		} else {
			newDayEvent();
			userCheckInChange();
		}
	}
	public int[][] getInfo(Date startDate, Date endDate){
		int startIndex = computeIndex(startDate);
		int endIndex = computeIndex(endDate);
		int size = endIndex-startIndex+1;
		int[][] matrix = new int[size][4];
		for(int i = startIndex; i<endIndex+1;i++){
			DayEvent day = dayEvents.get(i);
			matrix[i][0]=day.getMembers();
			matrix[i][1]=day.getBicycles();
			matrix[i][2]=day.getMembersCheckedIn();
			matrix[i][3]=day.getBicyclesInGarage();
		}
		return matrix;
	}
}
