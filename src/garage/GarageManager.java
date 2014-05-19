package garage;

import interfaces.BarcodePrinter;
import interfaces.BicycleGarageManager;
import interfaces.ElectronicLock;
import interfaces.PinCodeTerminal;
import garage.Database;

public class GarageManager implements BicycleGarageManager {
	private Database db;
	private char[] code;
	
	public GarageManager(Database db) {
		this.db = db;
	}
	
	public void registerHardwareDrivers(BarcodePrinter printer,
			ElectronicLock entryLock, ElectronicLock exitLock,
			PinCodeTerminal terminal) {
		
	}

	public void entryBarcode(String bicycleID) {
		
	}
	
	public void exitBarcode(String bicycleID) {
		
	}
	
	public void entryCharacter(char c) {
		if (code.length < 6) {
			code[code.length] = c;
		}
//		TODO: Add code for looking up PIN against member database
	}
}
