package interfaces;


public interface BicycleGarageManager {

	public void registerHardwareDrivers(BarcodePrinter printer,
			ElectronicLock entryLock, ElectronicLock exitLock,
			PinCodeTerminal terminal);

	public void entryBarcode(String bicycleID);
	public void exitBarcode(String bicycleID);
	public void entryCharacter(char c);

}
