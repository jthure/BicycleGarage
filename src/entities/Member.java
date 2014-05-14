package entities;

import java.util.Date;

public class Member {
	private String firstName, lastName, PIDNbr, telNbr, PINCode;
	private boolean suspended;
	private Date checkedIn;
	
	public Member(String firstName, String lastName, String PIDNbr, String telNbr) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.PIDNbr = PIDNbr;
		this.telNbr = telNbr;
		//this.PINCode = PINCode;
//		TODO: Insert code for generating PIN code
	}
	
	public void suspend() {
		suspended = true;
	}
	
	public void unsuspend() {
		suspended = false;
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
	
	public String changePIN() {
//		TODO: Insert code for generating PIN code
		return null;
	}
}
