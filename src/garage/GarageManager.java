package garage;

import java.util.Date;

import entities.*;
import interfaces.*;
import garage.Database;

public class GarageManager implements BicycleGarageManager {

	private static final long TERMINAL_TIMEOUT = 10000;

	private Database db;
	private StringBuilder pinBuilder;
	private Date lastPinInput;

	private BarcodePrinter printer;
	private ElectronicLock entryLock, exitLock;
	private PinCodeTerminal terminal;

	public GarageManager(Database db) {
		this.db = db;
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
		Bicycle bicycle = db.getBicycle(bicycleID);
		if (bicycle != null) {
			terminal.lightLED(PinCodeTerminal.GREEN_LED, 2);
			entryLock.open(10);
			bicycle.park();
		} else {
			terminal.lightLED(PinCodeTerminal.RED_LED, 2);
		}

	}

	public void exitBarcode(String bicycleID) {
		Bicycle bicycle = db.getBicycle(bicycleID);
		if (bicycle != null) {
			if (db.getMember(bicycle.getOwnerPIN().isCheckedIn())) {
				exitLock.open(10);
				bicycle.unPark();
			}
		}

	}

	public void entryCharacter(char c) {
		if (new Date().getTime() - lastPinInput.getTime() < TERMINAL_TIMEOUT) {
			pinBuilder.append(c);
			lastPinInput = new Date();
			if (pinBuilder.length() == 6) {
				lastPinInput.setTime(0);
				Member member = db.getMember(pinBuilder.toString());
				if (member != null) {
					terminal.lightLED(PinCodeTerminal.GREEN_LED, 10);
					entryLock.open(10);
					// TODO: check in member.
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
