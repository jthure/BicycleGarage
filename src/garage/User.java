package garage;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4252256277959600771L;

	private List<Bicycle> bicycleList;

	private String name;
	private String pin;
	private String pNbr;
	private String address;
	private Date lastCheckIn;

	static final int PIN_LENGTH = 6;
	private static final int MAX_BICYCLES = 5;

	public User(String name, String pin) {
		if (pin.length() != PIN_LENGTH) {
			throw new IllegalArgumentException("Pin code is of wrong length");
		}
		this.name = name;
		this.pin = pin;
		bicycleList = new LinkedList<>();
		lastCheckIn = new Date(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPin() {
		return pin;
	}

	public boolean setPin(String pin) {
		if (pin.length() == PIN_LENGTH) {
			this.pin = pin;
			return true;
		} else {
			return false;
		}

	}

	public boolean addBicyle(Bicycle b) {
		if (!bicycleListFull()) {
			b.setOwner(this);
			return bicycleList.add(b);
		} else {
			return false;
		}
	}
	public boolean removeBicycle(Bicycle b){
		return bicycleList.remove(b);
	}

	public boolean bicycleListFull() {
		return bicycleList.size() >= MAX_BICYCLES;
	}

	public void checkIn() {
		lastCheckIn.setTime(new Date().getTime());
	}

	public boolean isCheckedIn() {
		return new Date().getTime() - lastCheckIn.getTime() <= Database.CHECK_IN_TIMER;
	}
	public List<Bicycle> getBicycles(){
		return bicycleList;
	}

	public String toString() {
		return name + ": " + pin;
	}

}
