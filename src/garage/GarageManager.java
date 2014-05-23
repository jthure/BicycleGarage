package garage;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import entities.*;
import interfaces.*;
import garage.Database;

public class GarageManager implements BicycleGarageManager {

	private static final long TERMINAL_TIMEOUT = 10000;

	private Database db;
	private StringBuilder pinBuilder;
	private Date lastPinInput, ignoreInputTime;
	private int invalidPinCounter;

	private BarcodePrinter printer;
	private ElectronicLock entryLock, exitLock;
	private PinCodeTerminal terminal;

	/**Creates a new GarageManger object which will use the specified database.
	 * @param db The database to be used.
	 */
	public GarageManager(Database db) {
		this.db = db;
		pinBuilder = new StringBuilder();
		lastPinInput = new Date(0);
		ignoreInputTime = new Date(0);
		invalidPinCounter = 0;

	}

	/* (non-Javadoc)
	 * @see interfaces.BicycleGarageManager#registerHardwareDrivers(interfaces.BarcodePrinter, interfaces.ElectronicLock, interfaces.ElectronicLock, interfaces.PinCodeTerminal)
	 */
	public void registerHardwareDrivers(BarcodePrinter printer,
			ElectronicLock entryLock, ElectronicLock exitLock,
			PinCodeTerminal terminal) {
		this.printer = printer;
		this.entryLock = entryLock;
		this.exitLock = exitLock;
		this.terminal = terminal;

	}

	/* (non-Javadoc)
	 * @see interfaces.BicycleGarageManager#entryBarcode(java.lang.String)
	 */
	public void entryBarcode(String bicycleID) {
		// Check if garage is full.
		if (db.getBicyclesInGarage() < db.getMaxParkingSlots()) {
			Bicycle bicycle = db.getBicycle(bicycleID);
			// Check if barcode is valid.
			if (bicycle != null) {
				// Check if owner is suspended.
				Member m =db.getMember(bicycle.getOwnerPIN());
				if (!m.isSuspended()) {
					terminal.lightLED(PinCodeTerminal.GREEN_LED, 10);
					entryLock.open(10);
					ignoreInputTime.setTime(new Date().getTime() + 10000);
					db.parkBicycle(bicycleID);
					db.checkInMember(bicycle.getOwnerPIN());
				} else {
					terminal.lightLED(PinCodeTerminal.RED_LED, 2);
				}
			} else {
				terminal.lightLED(PinCodeTerminal.RED_LED, 1);
				terminal.lightLED(PinCodeTerminal.RED_LED, 1);
			}
		} else {
			terminal.lightLED(PinCodeTerminal.RED_LED, 4);
		}
	}

	/* (non-Javadoc)
	 * @see interfaces.BicycleGarageManager#exitBarcode(java.lang.String)
	 */
	public void exitBarcode(String bicycleID) {
		Bicycle bicycle = db.getBicycle(bicycleID);
		// Check if barcode is valid.
		if (bicycle != null) {
			Member member = db.getMember(bicycle.getOwnerPIN());
			// Check if owner is checked in .
			if (member.isCheckedIn()) {
				exitLock.open(10);
				db.unParkBicycle(bicycleID);
				member.checkOut();
			}
		}

	}

	/* (non-Javadoc)
	 * @see interfaces.BicycleGarageManager#entryCharacter(char)
	 */
	public void entryCharacter(char c) {
		// Check if terminal ignores input (i.e. the door is open or 3 invalid
		// pincodes has been entered).
		if (new Date().compareTo(ignoreInputTime) > 0) {
			// Check if the terminal has timed out.
			if (new Date().getTime() - lastPinInput.getTime() < TERMINAL_TIMEOUT) {
				pinBuilder.append(c);
				lastPinInput = new Date();
				if (pinBuilder.length() == 6) { // Check if a full length
												// pincode has been entered.
					lastPinInput.setTime(0);
					Member member = db.getMember(pinBuilder.toString());
					if (member != null) { // Check if the pincode is valid.
						boolean bicycleInGarage = false;
						for (String barcode : member.getBicycles()) {
							if (db.getBicycle(barcode).isParked()) {
								bicycleInGarage = true;
							}
						}

						if (bicycleInGarage) {
							terminal.lightLED(PinCodeTerminal.GREEN_LED, 10);
							entryLock.open(10);
							ignoreInputTime
									.setTime(new Date().getTime() + 10000);
							db.checkInMember(member.getPIN());
							
							pinBuilder.setLength(0);
						} else {
							terminal.lightLED(PinCodeTerminal.RED_LED, 2);
							pinBuilder.setLength(0);
						}
					} else {
						invalidPinCounter++;
						if (invalidPinCounter >= 3) {
							ignoreInputTime
									.setTime(new Date().getTime() + 20000);
							invalidPinCounter = 0;
							terminal.lightLED(PinCodeTerminal.RED_LED, 20);
						} else {
							terminal.lightLED(PinCodeTerminal.RED_LED, 2);
							
						}
						pinBuilder.setLength(0);
					}
				}
			} else {
				pinBuilder.setLength(0);
				pinBuilder.append(c);
				lastPinInput = new Date();
			}

		}
	}
}
