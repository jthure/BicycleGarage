package entities;

import java.io.Serializable;
import java.util.Date;

public class DayEvent implements Serializable {
	Date day;
	int members, bicycles, membersCheckedIn, bicyclesInGarage;

	/**Creates a new DayEvent object with the specified date and all other values set to 0.
	 * @param day Date to be set.
	 */
	public DayEvent(Date day) {
		this.day = day;
		members = 0;
		bicycles = 0;
		membersCheckedIn = 0;
		bicyclesInGarage = 0;
	}
	/**
	 * Creates a new DayEvent object with the current date and all other values set to 0.
	 */
	public DayEvent(){
		this(new Date());
	}
	/**Returns this dayevent's date.
	 * @return
	 */
	public Date getDay(){
		return day;
	}
	/**Returns the number of registered members in this dayevent.
	 * @return the number of registered members in this dayevent.
	 */
	public int getMembers() {
		return members;
	}
	/** Sets the number of registered members in this dayevent to the specified value.
	 * @param members the number of registered members to be set in this dayevent.
	 */
	public void setMembers(int members) {
		this.members = members;
	}
	/**Returns the number of registered bicycles in this dayevent.
	 * @return the number of registered bicycles in this dayevent.
	 */
	public int getBicycles() {
		return bicycles;
	}
	/**Sets the number of registered bicycles in this dayevent to the specified value.
	 * @param bicycles the number of registered bicycles to be set in this dayevent.
	 */
	public void setBicycles(int bicycles) {
		this.bicycles = bicycles;
	}
	/**Returns the number of members that have checked in, in this dayevent.
	 * @return the number of members that have checked in, in this dayevent.
	 */
	public int getMembersCheckedIn() {
		return membersCheckedIn;
	}
	/**
	 * Increments the number of checked in members by 1.
	 */
	public void incrementMembersCheckedIn() {
		this.membersCheckedIn++;
	}
	/**
	 * Sets the number of checked members in this dayevent to the specified value.
	 * @param i the number of checked members to be set.
	 */
	public void setMembersCheckedIn(int i){
		this.membersCheckedIn=i;
	}
	/**Returns the number of bicycles that is parked in the garage, in this dayevent.
	 * @return the number of bicycles that is parked in the garage, in this dayevent.
	 */
	public int getBicyclesInGarage() {
		return bicyclesInGarage;
	}
	/**Sets the number of parked bicycles in this dayevent to the specified value.
	 * @param bicyclesInGarage the number of parked bicycles to be set.
	 */
	public void setBicyclesInGarage(int bicyclesInGarage) {
		this.bicyclesInGarage = bicyclesInGarage;
	}
	/**Sets the date of this dayevent.
	 * @param day date to be set.
	 */
	public void setDay(Date day) {
		this.day = day;
	}
	
	
}
