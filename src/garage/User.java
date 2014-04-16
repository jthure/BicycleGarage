package garage;

import java.util.LinkedList;
import java.util.List;

public class User {

	private List<Bicycle> bicycleList;

	private String name;
	private String pin;

	private static final int PIN_LENGTH = 6;
	private static final int MAX_BICYCLES = 2;

	public User(String name, String pin) {
		if (pin.length() != PIN_LENGTH) {
			throw new IllegalArgumentException("Pin code is of wrong length");
		}
		this.name = name;
		this.pin = pin;
		bicycleList = new LinkedList<>();
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

	public boolean bicycleListFull() {
		return bicycleList.size() >= MAX_BICYCLES;
	}

}
