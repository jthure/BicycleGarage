package entities;

import java.util.Date;

public class Member {
	public final static int maxBicycles = 2;
	
	private String firstName, lastName, PIDNbr, telNbr, PINCode;
	private boolean suspended;
	private Date checkedIn, suspExpDate;
	private String[] bicycles;
	
	public Member(String firstName, String lastName, String PIDNbr, String telNbr, String PINCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.PIDNbr = PIDNbr;
		this.telNbr = telNbr;
		suspended = false;
		suspExpDate = new Date();
		this.PINCode = PINCode;
		bicycles = new String[maxBicycles];
	}
	public boolean addBicycle(String barcode){
		return false;
	}
	
	
	public void suspend(Date suspendUntil) {
		suspExpDate = suspendUntil;
		suspended = true;
	}
	
	public void unsuspend() {
		suspended = false;
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
	
	public String changePIN(String PINCode) {
//		TODO: Insert code for generating PIN code
		return null;
	}
	
	public String[] getBicycles() {
//		TODO: Code for looking up bicycles
		return null;
	}
}
