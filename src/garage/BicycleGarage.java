package garage;
import testdrivers.*;
import gui.GUI;
import interfaces.*;

public class BicycleGarage {
    public BicycleGarage() {
    	Database db = new Database();
    	db.loadDatabase();
//    	_Test.test(db);
        BicycleGarageManager manager = new TestBicycleGarageManager(db);
        ElectronicLock entryLock = new ElectronicLockTestDriver("Entry lock");
        ElectronicLock exitLock = new ElectronicLockTestDriver("Exit lock");
        BarcodePrinter printer = new BarcodePrinterTestDriver();
        PinCodeTerminal terminal = new PinCodeTerminalTestDriver();
        manager.registerHardwareDrivers(printer, entryLock, exitLock, terminal);
        terminal.register(manager);
        BarcodeReader readerEntry = new BarcodeReaderEntryTestDriver();
        BarcodeReader readerExit = new BarcodeReaderExitTestDriver();
        readerEntry.register(manager);
        readerExit.register(manager);
        
        new GUI(db);
    }
    public static void main(String[] args) {
        new BicycleGarage();
    }
}