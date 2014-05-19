package entities;

public class Bicycle {
	private String barCode, ownerPIN;
	private boolean parked;
	
	public Bicycle(String ownerPIN) {
//		TODO: Insert code for generating barcode
		this.ownerPIN = ownerPIN;
	}
	
	public String getBarcode() {
		return barCode;
	}
	
	public String getOwnerPIN() {
		return ownerPIN;
	}
	
	public boolean park() {
		if (parked)
			return false;
//		TODO: Insert code for checking if garage is full
		parked = true;
		return true;
	}
	public void unPark(){
		parked = false;
	}
	
	public boolean isParked() {
		return parked;
	}
}
