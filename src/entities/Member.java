package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Member implements Serializable {

	
	public final static int MAX_BICYCLES = 2;
	public final static long CHECK_IN_TIME = 600000;	// represented in milliseconds
	
	private String firstName, lastName, PIDNbr, telNbr, PINCode;
	private Date checkedIn, suspExpDate;
	private ArrayList<String> bicycles;
	
	/** Creates a new Member object.
	 * @param firstName Member's first name.
	 * @param lastName Member's last name.
	 * @param PIDNbr Member's personal ID number.
	 * @param telNbr Member's telephone number.
	 * @param PINCode Member's PIN-code.
	 */
	public Member(String firstName, String lastName, String PIDNbr, String telNbr, String PINCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.PIDNbr = PIDNbr;
		this.telNbr = telNbr;
		suspExpDate = new Date();
		checkedIn = new Date();
		this.PINCode = PINCode;
		bicycles = new ArrayList<String>(MAX_BICYCLES);
		checkedIn = new Date((new Date()).getTime() - CHECK_IN_TIME);	// Initialize check in date so that 
																		// member is checked out when created
	}
	
	/**Checks whether this member is checked in to the garage or not.
	 * @return True if this member is checked in to the garage, false otherwise.
	 */
	public boolean isCheckedIn() {
		if ((new Date()).getTime() - checkedIn.getTime() < CHECK_IN_TIME) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks in this user to the garage.
	 */
	public void checkIn() {
		checkedIn = new Date();
	}
	/**
	 * Checks out this user from the garage.
	 */
	public void checkOut() {
		checkedIn = new Date((new Date()).getTime() - CHECK_IN_TIME);
	}
	
	
	/**Adds a bicycle barcode to this member if this member has less than 2 bicycle barcodes already.
	 * @param barcode barcode to be added.
	 * @return True if barcode was added, false otherwise.
	 */
	public boolean addBicycle(String barcode){
		if (bicycles.size() < 2) {
			bicycles.add(barcode);
			return true;
		}
		return false;
	}
	
	
	/**Removes a bicycle barcode from this member if it exists.
	 * @param barcode barcode to be removed.
	 * @return True if the barcode existed and was removed, false otherwise.
	 */
	public boolean removeBicycle(String barcode) {
		return bicycles.remove(barcode);
	}
	
	/**Suspends this member until the specified date.
	 * @param suspendUntil Date which this member should be suspended until.
	 */
	public void suspend(Date suspendUntil) {
		suspExpDate = suspendUntil;
	}
	
	/**
	 * Unsuspends this member.
	 */
	public void unsuspend() {
		suspExpDate = new Date();
	}
	
	/**Checks whether this member is suspended or not.
	 * @return True if this member is suspended, false otherwise.
	 */
	public boolean isSuspended() {
		if (suspExpDate.after(new Date())) {
			return true;
		}
		return false;
	}
	
	/**Checks what date this member's suspension expires.
	 * @return the date this member's suspension expires.
	 */
	public Date suspendedUntil() {
		return suspExpDate;
	}
	
	/**Returns this member's first name, last name and telephone number.
	 * @return this member's first name, last name and telephone number.
	 */
	public String[] getInfo() {
		return new String[] {firstName, lastName, telNbr};
	}
	
	/**Returns this members personal ID number.
	 * @return this members personal ID number.
	 */
	public String getPIDNbr() {
		return PIDNbr;
	}
	
	/** Returns this members PIN-code.
	 * @return this members PIN-code
	 */
	public String getPIN() {
		return PINCode;
	}
	
	/**Sets this member's PIN-code to the specified PIN-code.
	 * @param PINCode PIN-code to be set.
	 * @return The PIN-code that has been set.
	 */
	public String setPIN(String PINCode) {
		this.PINCode = PINCode;
		return PINCode;
	}
	
	/**Returns this member's list of bicycle barcodes.
	 * @return this member's list of bicycle barcodes.
	 */
	public ArrayList<String> getBicycles() {
		return bicycles;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o instanceof Member) {
			if (PIDNbr.equals(((Member) o).getPIDNbr())) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return firstName + " " + lastName;
	}
}
