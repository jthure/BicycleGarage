package garage;

import java.util.Date;

import entities.DayEvent;

public class Statistics {
	Database db;

	public Statistics(Database db) {
		this.db = db;
	}

	private int computeIndex(Date date) {
		long ms = date.getTime() - db.creationDate().getTime();
		long divide = 86400000;
		return (int) (ms / divide);
	}

	private DayEvent newDayEvent() {
		DayEvent newDayEvent = new DayEvent();
		db.getDayEvents().set(computeIndex(newDayEvent.getDate()), newDayEvent);
		memberChange();
		bicycleChange();
		bicyclesInGarageChange();
		return newDayEvent;
	}

	public void memberChange() {
		int index = computeIndex(new Date());
		DayEvent dayEvent = db.getDayEvents().get(index);
		if (dayEvent != null) {
			dayEvent.setMembers(db.getMemberSize());
		} else {
			newDayEvent();
		}
	}

	public void bicycleChange() {
		int index = computeIndex(new Date());
		DayEvent dayEvent = db.getDayEvents().get(index);
		if (dayEvent != null) {
			dayEvent.setBicycles(db.getBicycleSize());
		} else {
			newDayEvent();
		}
	}

	public void bicyclesInGarageChange() {
		int index = computeIndex(new Date());
		DayEvent dayEvent = db.getDayEvents().get(index);
		if (dayEvent != null) {
			dayEvent.setBicycles(db.getBicyclesInGarage());
		} else {
			newDayEvent();
		}
	}

	public void userCheckInChange() {
		int index = computeIndex(new Date());
		DayEvent dayEvent = db.getDayEvents().get(index);
		if (dayEvent != null) {
			dayEvent.incrementMembersCheckedIn();
		} else {
			newDayEvent();
			userCheckInChange();
		}
	}
	public Integer[][] getInfo(Date startDate, Date endDate){
		int startIndex = computeIndex(startDate);
		int endIndex = computeIndex(endDate);
		int size = endIndex-startIndex+1;
		Integer[][] matrix = new Integer[size][4];
		for(int i = startIndex; i<endIndex+1;i++){
			DayEvent day = db.getDayEvents().get(i);
			matrix[i][0]=day.getMembers();
			matrix[i][1]=day.getBicycles();
			matrix[i][2]=day.getMembersCheckedIn();
			matrix[i][3]=day.getBicyclesInGarage();
		}
		return matrix;
	}
}
