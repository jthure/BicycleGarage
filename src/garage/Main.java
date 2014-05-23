package garage;

import gui_new.GUI;
import interfaces.BarcodePrinter;
import interfaces.BarcodeReader;
import interfaces.ElectronicLock;
import interfaces.PinCodeTerminal;

import java.awt.EventQueue;
import java.io.File;

import testdrivers.BarcodePrinterTestDriver;
import testdrivers.BarcodeReaderEntryTestDriver;
import testdrivers.BarcodeReaderExitTestDriver;
import testdrivers.ElectronicLockTestDriver;
import testdrivers.PinCodeTerminalTestDriver;

public class Main {
	private final String[] FILE_NAMES = { "members.bg", "bicycles.bg",
			"availablePIN.bg", "availableBar.bg", "stats.bg", "slots.bg" };

	private Database db;
	private GarageManager manager;
	private ElectronicLock entryLock;
	private ElectronicLock exitLock;
	private BarcodePrinter printer;
	private PinCodeTerminal terminal;
	private BarcodeReader readerEntry;
	private BarcodeReader readerExit;

	/**
	 * Initializes the Bicycle garage system with the specified max values for
	 * members and bicycles if no previous saved files was found.
	 * 
	 * @param maxBikes Max number of bicycles that the database will hold.
	 * @param maxMembers Max number of members that the database will hold.
	 */
	public Main(int maxBikes, int maxMembers) {
		File m = new File("db\\" + FILE_NAMES[0]);
		File b = new File("db\\" + FILE_NAMES[1]);
		File p = new File("db\\" + FILE_NAMES[2]);
		File c = new File("db\\" + FILE_NAMES[3]);
		File s = new File("db\\" + FILE_NAMES[4]);
		File g = new File("db\\" + FILE_NAMES[5]);
		if (m.exists() && b.exists() && p.exists() && c.exists() && s.exists()
				&& g.exists()) {
			db = new Database(FILE_NAMES[0], FILE_NAMES[1], FILE_NAMES[2],
					FILE_NAMES[3], FILE_NAMES[4], FILE_NAMES[5]);
		} else {
			db = new Database(maxBikes, maxMembers);
		}
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
		if (args.length >= 2) {
			new Main(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		} else {
			new Main(100000, 100000);
		}
	}

}
