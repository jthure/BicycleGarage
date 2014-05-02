package garage;

import java.util.Date;

import interfaces.*;

public class TestBicycleGarageManager implements BicycleGarageManager {

	private static final long TERMINAL_TIMEOUT = 10000;

	private BarcodePrinter printer;
	private ElectronicLock entryLock, exitLock;
	private PinCodeTerminal terminal;

	private Database db;
	private StringBuilder pinBuilder;
	private Date lastPinInput;

	public TestBicycleGarageManager(Database db) {
		this.db = db;
		pinBuilder = new StringBuilder();
		lastPinInput = new Date(0);

	}

	public void registerHardwareDrivers(BarcodePrinter printer,
			ElectronicLock entryLock, ElectronicLock exitLock,
			PinCodeTerminal terminal) {
		this.printer = printer;
		this.entryLock = entryLock;
		this.exitLock = exitLock;
		this.terminal = terminal;

	}

	public void entryBarcode(String bicycleID) {
		Bicycle bicycle = db.getBicycleWithID(bicycleID);
		if (bicycle != null) {
			terminal.lightLED(PinCodeTerminal.GREEN_LED, 2);
			entryLock.open(10);
			bicycle.parkInGarage();
		} else {
			terminal.lightLED(PinCodeTerminal.RED_LED, 2);
		}

	}

	public void exitBarcode(String bicycleID) {
		Bicycle bicycle = db.getBicycleWithID(bicycleID);
		if (bicycle != null) {
			if (bicycle.getOwner().isCheckedIn()) {
				exitLock.open(10);
				bicycle.unParkInGarage();
			}
		}

	}

	public void entryCharacter(char c) {
		if (new Date().getTime() - lastPinInput.getTime() < TERMINAL_TIMEOUT) {
			pinBuilder.append(c);
			lastPinInput = new Date();
			if (pinBuilder.length() == 6) {
				lastPinInput.setTime(0);
				User user = db.getUserWithPin(pinBuilder.toString());
				if (user != null) {
					terminal.lightLED(PinCodeTerminal.GREEN_LED, 2);
					entryLock.open(10);
					user.checkIn();
				} else {
					terminal.lightLED(PinCodeTerminal.RED_LED, 2);
				}
			}
		} else {
			pinBuilder.setLength(0);
			pinBuilder.append(c);
			lastPinInput = new Date();
		}

	}

}
