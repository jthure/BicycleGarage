package entities;

import java.io.Serializable;
import java.util.Date;

public class DayEvent implements Serializable {
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
	public Date getDay(){
		return day;
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
	public void incrementMembersCheckedIn() {
		this.membersCheckedIn++;
	}
	public void setMembersCheckedIn(int i){
		this.membersCheckedIn=i;
	}
	public int getBicyclesInGarage() {
		return bicyclesInGarage;
	}
	public void setBicyclesInGarage(int bicyclesInGarage) {
		this.bicyclesInGarage = bicyclesInGarage;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	
	
}
