package entities;

import java.util.Date;

public class DayEvent {
	Date day;
	int members, bicycles, membersCheckedIn, bicyclesInGarage;

	public DayEvent(Date day) {
		this.day = day;
		members = 0;
		bicycles = 0;
		membersCheckedIn = 0;
		bicyclesInGarage = 0;
	}
	public DayEvent(){
		this(new Date());
	}
	public int getMembers() {
		return members;
	}
	public void setMembers(int members) {
		this.members = members;
	}
	public int getBicycles() {
		return bicycles;
	}
	public void setBicycles(int bicycles) {
		this.bicycles = bicycles;
	}
	public int getMembersCheckedIn() {
		return membersCheckedIn;
	}
	public void setMembersCheckedIn(int membersCheckedIn) {
		this.membersCheckedIn = membersCheckedIn;
	}
	public int getBicyclesInGarage() {
		return bicyclesInGarage;
	}
	public void setBicyclesInGarage(int bicyclesInGarage) {
		this.bicyclesInGarage = bicyclesInGarage;
	}
	
	
}
