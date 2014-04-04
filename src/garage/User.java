package garage;

import java.util.LinkedList;
import java.util.List;

public class User {

	private List<Bicycle> bicycleList;

	private String name;
	private String pin;
	
	private static final int PIN_LENGTH = 6;
	
	public User(String name, String pin){
		if(pin.length()!=PIN_LENGTH){
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

	public void setPin(String pin) {
		this.pin = pin;
	}
	
}
