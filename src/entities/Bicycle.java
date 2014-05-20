package entities;

public class Bicycle {
	private String barCode, ownerPIN;
	private boolean parked;
	
	public Bicycle(String ownerPIN, String barcode) {
		barCode = barcode;
		this.ownerPIN = ownerPIN;
	}
	
	public String getBarcode() {
		return barCode;
	}
	
	public String getOwnerPIN() {
		return ownerPIN;
	}
	
	public void setOwnerPIN(String PIN) {
		ownerPIN = PIN;
	}
	
	public boolean park() {
		if (parked) {
			return false;
		}
		parked = true;
		return true;
	}
	
	public void unPark(){
		parked = false;
	}
	
	public boolean isParked() {
		return parked;
	}
	
	public String toString(){
		return barCode;
	}
}
