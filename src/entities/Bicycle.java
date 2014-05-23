package entities;

import java.io.Serializable;

public class Bicycle implements Serializable{
	private String barCode, ownerPIN;
	private boolean parked;
	
	/**
	 * Creates a new bicycle object.
	 * @param ownerPIN PIN-code related to the owner Member.
	 * @param barcode The bicycle's ID number.
	 */
	public Bicycle(String ownerPIN, String barcode) {
		barCode = barcode;
		this.ownerPIN = ownerPIN;
	}
	
	/**
	 * Returns this bicycle's barcode.
	 * @return this bicycle's barcode.
	 */
	public String getBarcode() {
		return barCode;
	}
	
	/**Returns the PIN-code for this bicycle's owner Member.
	 * @return the PIN-code for this bicycle's owner Member.
	 */
	public String getOwnerPIN() {
		return ownerPIN;
	}
	
	/**Sets the PIN-code for this bicycle's owner Member.
	 * @param the PIN-code to be set as this bicycle's owner Member.
	 */
	public void setOwnerPIN(String PIN) {
		ownerPIN = PIN;
	}
	
	/**Marks this bicycle as parked in the garage.
	 * @return True if the bicycle was not before, false otherwise.
	 */
	public boolean park() {
		if (parked) {
			return false;
		}
		parked = true;
		return true;
	}
	
	/**
	 * Marks this bicycle as not parked in the garage.
	 */
	public void unPark(){
		parked = false;
	}
	
	/**Returns whether this bicycle is parked in the garage or not.
	 * @return True if this bicycle is parked in the garage, false otherwise.
	 */
	public boolean isParked() {
		return parked;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return barCode;
	}
}
