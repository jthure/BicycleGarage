package garage;

import gui_new.GUI;
import interfaces.BarcodePrinter;
import interfaces.BarcodeReader;
import interfaces.ElectronicLock;
import interfaces.PinCodeTerminal;

import java.awt.EventQueue;

import testdrivers.BarcodePrinterTestDriver;
import testdrivers.BarcodeReaderEntryTestDriver;
import testdrivers.BarcodeReaderExitTestDriver;
import testdrivers.ElectronicLockTestDriver;
import testdrivers.PinCodeTerminalTestDriver;

public class Main {
	private Database db;
	private GarageManager manager;
    private ElectronicLock entryLock;
    private ElectronicLock exitLock;
    private BarcodePrinter printer;
    private PinCodeTerminal terminal;
    private BarcodeReader readerEntry;
    private BarcodeReader readerExit;
    
	public Main() {
    	db = new Database(100000, 1000000);
    	manager = new GarageManager(db);
        entryLock = new ElectronicLockTestDriver("Entry lock");
        exitLock = new ElectronicLockTestDriver("Exit lock");
        printer = new BarcodePrinterTestDriver();
        terminal = new PinCodeTerminalTestDriver();
        manager.registerHardwareDrivers(printer, entryLock, exitLock, terminal);
        terminal.register(manager);
        readerEntry = new BarcodeReaderEntryTestDriver();
        readerExit = new BarcodeReaderExitTestDriver();
        readerEntry.register(manager);
        readerExit.register(manager);
        
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI(db, printer);
					window.frmBicycleGarage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
