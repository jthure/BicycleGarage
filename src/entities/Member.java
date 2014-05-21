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
	
	public Member(String firstName, String lastName, String PIDNbr, String telNbr, String PINCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.PIDNbr = PIDNbr;
		this.telNbr = telNbr;
		suspExpDate = new Date();
		checkedIn = new Date(0);
		this.PINCode = PINCode;
		bicycles = new ArrayList<String>(MAX_BICYCLES);
		checkedIn = new Date((new Date()).getTime() - CHECK_IN_TIME);	// Initialize check in date so that 
																		// member is checked out when created
	}
	
	public boolean isCheckedIn() {
		if ((new Date()).getTime() - checkedIn.getTime() < CHECK_IN_TIME) {
			return true;
		}
		return false;
	}
	
	public void checkIn() {
		checkedIn = new Date();
	}
	
	public boolean addBicycle(String barcode){
		if (bicycles.size() < 2) {
			bicycles.add(barcode);
			return true;
		}
		return false;
	}
	
	public boolean removeBicycle(String barcode) {
		return bicycles.remove(barcode);
	}
	
	public void suspend(Date suspendUntil) {
		suspExpDate = suspendUntil;
	}
	
	public void unsuspend() {
		suspExpDate = new Date();
	}
	
	public boolean isSuspended() {
		if (suspExpDate.after(new Date())) {
			return true;
		}
		return false;
	}
	
	public Date suspendedUntil() {
		return suspExpDate;
	}
	
	public String[] getInfo() {
		return new String[] {firstName, lastName, telNbr};
	}
	
	public String getPIDNbr() {
		return PIDNbr;
	}
	
	public String getPIN() {
		return PINCode;
	}
	
	public String setPIN(String PINCode) {
		this.PINCode = PINCode;
		return PINCode;
	}
	
	public ArrayList<String> getBicycles() {
		return bicycles;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Member) {
			if (PIDNbr.equals(((Member) o).getPIDNbr())) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	public String toString(){
		return firstName + " " + lastName;
	}
}
