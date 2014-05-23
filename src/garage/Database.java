package garage;

import interfaces.BarcodePrinter;
import interfaces.DatabaseInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

import entities.Bicycle;
import entities.DayEvent;
import entities.Member;

public class Database {
	private LimitedHashMap<String, Member> members; // Key = PIN
	private LimitedHashMap<String, Bicycle> bicycles; // Key = Bar code
	private LinkedList<String> availablePIN, availableBar;
	private ArrayList<DayEvent> dayEvents;
	private Date creationDate;
	private Statistics stats;
	private Integer maxParkingSlots;

	/**
	 * Creates a new Database from scratch with the specified values for maximum
	 * number of Bicycles and Members.
	 * 
	 * @param maxBikes
	 *            Maximum number of bicycles that can be registered to the
	 *            database.
	 * @param maxMembers
	 *            Maximum number of members that can be registered to the
	 *            database.
	 */
	public Database(int maxBikes, int maxMembers) {
		members = new LimitedHashMap<String, Member>(maxMembers);
		bicycles = new LimitedHashMap<String, Bicycle>(maxBikes);
		availablePIN = new LinkedList<String>();
		availableBar = new LinkedList<String>();
		generateCodes(availablePIN, 6);
		generateCodes(availableBar, 5);
		dayEvents = new ArrayList<DayEvent>();
		dayEvents.add(new DayEvent());
		creationDate = new Date();
		stats = new Statistics(this);
		maxParkingSlots = maxBikes;
		saveAvailableBar();
		saveAvailablePIN();
		saveBicycles();
		saveMembers();
		saveStats();
		saveMaxParkingSlots();
	}

	/**
	 * Creates a new Database that load previously saved files.
	 * 
	 * @param members
	 *            Filename for the file containing members data.
	 * @param bicycles
	 *            Filename for the file containing bicycles data.
	 * @param availablePIN
	 *            Filename for the file containing available PIN-codes data.
	 * @param availableBar
	 *            Filename for the file containing available barcodes data.
	 * @param stats
	 *            Filename for the file containing stats data.
	 * @param slots
	 *            Filename for the file containing parking slots value.
	 */
	public Database(String members, String bicycles, String availablePIN,
			String availableBar, String stats, String slots) {
		loadDatabase(members, bicycles, availablePIN, availableBar, stats,
				slots);
		this.stats = new Statistics(this);
		creationDate = dayEvents.get(0).getDay();
	}

	private void generateCodes(LinkedList<String> list, int digits) {
		String leadingZeroes = "%0" + digits + "d";
		for (int i = 0; i < Math.pow(10, digits); i++) {
			list.add(String.format(leadingZeroes, i));
		}
		Collections.shuffle(list);
	}

	/**
	 * Adds a member with the specified data to the database.
	 * 
	 * @param fName
	 *            Member's first name
	 * @param lName
	 *            Member's last name
	 * @param PIDNbr
	 *            Member's personal ID number.
	 * @param telNbr
	 *            Member's telephone number
	 * @return true if the member was added, false otherwise.
	 */
	public boolean addMember(String fName, String lName, String PIDNbr,
			String telNbr) {
		String PIN = availablePIN.pop(); // Get new PIN
		Member m = new Member(fName, lName, PIDNbr, telNbr, PIN); // Create
																	// member
		if (members.put(PIN, m) != null) { // Check if full
			stats.memberChange();
			saveMembers();
			saveAvailablePIN();
			saveStats();
			return true;
		}
		return false;
	}

	/**
	 * Returns the member with the specified PIN-code, if it exists.
	 * 
	 * @param PIN
	 *            PIN-code to search for.
	 * @return The member with the specified PIN-code. null if the PIN-code
	 *         doesn't exists.
	 */
	public Member getMember(String PIN) {
		return members.get(PIN);
	}

	/**
	 * Finds all members who matches the regular expression
	 * "search*"(case-insensitive).
	 * 
	 * @param name
	 *            name to search for.
	 * @return a list containing all members who was matched.
	 */
	public LinkedList<Member> findMembersByName(String name) {
		LinkedList<Member> matchedMembers = new LinkedList<>();
		Set<Entry<String, Member>> set = members.entrySet();
		for (Entry<String, Member> e : set) {
			Member member = e.getValue();
			String regexName = "(?i)" + name + ".*";
			if (member.getInfo()[0].matches(regexName)
					|| member.getInfo()[1].matches(regexName)
					|| (member.getInfo()[0] + " " + member.getInfo()[0])
							.matches(regexName)) {
				matchedMembers.add(member);
			}
		}
		return matchedMembers;
	}

	/**
	 * Adds a bicycle to the specified member, if the neither the member nor the
	 * database has reached it's maximum capacity.
	 * 
	 * @param m
	 *            Member which the bicycle should be added to.
	 * @param p
	 *            Barcde printer for printing barcode.
	 * @return True if the bicycle was addded, false otherwise.
	 */
	public boolean addBicycle(Member m, BarcodePrinter p) {
		String barcode = availableBar.pop(); // Get new bar code
		Bicycle b = new Bicycle(m.getPIN(), barcode); // Create new bicycle
		if (m.addBicycle(barcode)) { // Check if member is full
			if (bicycles.put(barcode, b) != null) { // Check if db full
				p.printBarcode(barcode);
				stats.bicycleChange();
				saveBicycles();
				saveMembers();
				saveAvailableBar();
				saveStats();
				return true;
			}
			m.removeBicycle(barcode); // Remove inserted barcode if db was full
		}
		availableBar.add(barcode); // Put back retrieved barcode if failed.
		return false;
	}

	/**
	 * Returns the bicycle with the specified barcode, if it exists.
	 * 
	 * @param barcode
	 *            barcode to search for.
	 * @return true if the bicycle existed, false otherwise.
	 */
	public Bicycle getBicycle(String barcode) {
		return bicycles.get(barcode);
	}

	/**
	 * Changes the PIN-code for the member with the specified PIN-code, to
	 * random of the available PIN-codes.
	 * 
	 * @param oldPIN
	 *            PIN-code that belongs to the user before the change.
	 * @return the new PIN-code.
	 */
	public String changePIN(String oldPIN) {
		if (members.containsKey(oldPIN)) {
			Member m = members.get(oldPIN); // Get member
			members.remove(oldPIN); // Remove old entry
			String newPIN = availablePIN.pop(); // Get new PIN
			m.setPIN(newPIN); // Change PIN
			members.put(newPIN, m); // Put in new entry
			for (String s : m.getBicycles()) {
				Bicycle b = bicycles.get(s); // Get bicycle
				b.setOwnerPIN(newPIN); // Change PIN
				bicycles.put(b.getBarcode(), b); // Put back in
			}
			availablePIN.add(oldPIN); // Put back old PIN
			saveMembers();
			saveAvailablePIN();
			saveBicycles();
			return newPIN;
		}
		return null;
	}

	/**
	 * Loads the files with the specified names.
	 * 
	 * @param members
	 *            Filename for the file containing members data.
	 * @param bicycles
	 *            Filename for the file containing bicycles data.
	 * @param availablePIN
	 *            Filename for the file containing available PIN-codes data.
	 * @param availableBar
	 *            Filename for the file containing available barcodes data.
	 * @param stats
	 *            Filename for the file containing stats data.
	 * @param slots
	 *            Filename for the file containing parking slots value.
	 */
	@SuppressWarnings("unchecked")
	public void loadDatabase(String members, String bicycles,
			String availablePIN, String availableBar, String stats, String slots) {
		try {
			FileInputStream fin = new FileInputStream("db\\" + members);
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.members = (LimitedHashMap<String, Member>) ois.readObject();
			ois.close();
			fin.close();

			fin = new FileInputStream("db\\" + bicycles);
			ois = new ObjectInputStream(fin);
			this.bicycles = (LimitedHashMap<String, Bicycle>) ois.readObject();
			ois.close();
			fin.close();

			fin = new FileInputStream("db\\" + availablePIN);
			ois = new ObjectInputStream(fin);
			this.availablePIN = (LinkedList<String>) ois.readObject();
			ois.close();
			fin.close();

			fin = new FileInputStream("db\\" + availableBar);
			ois = new ObjectInputStream(fin);
			this.availableBar = (LinkedList<String>) ois.readObject();
			ois.close();
			fin.close();

			fin = new FileInputStream("db\\" + stats);
			ois = new ObjectInputStream(fin);
			this.dayEvents = (ArrayList<DayEvent>) ois.readObject();
			ois.close();
			fin.close();

			fin = new FileInputStream("db\\" + slots);
			ois = new ObjectInputStream(fin);
			this.maxParkingSlots = (Integer) ois.readObject();
			ois.close();
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private <T extends Serializable> void writeToFile(T object, String fileName) {
		try {
			FileOutputStream fout = new FileOutputStream(new File("db\\"
					+ fileName));
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(object);
			oout.close();
			fout.close();
		} catch (FileNotFoundException e) {
			System.out.println("The path db\\" + fileName
					+ " could not be found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error while serializing");
			e.printStackTrace();
		}
	}

	private void saveMembers() {
		writeToFile(this.members, "members.bg");
	}

	private void saveBicycles() {
		writeToFile(this.bicycles, "bicycles.bg");
	}

	private void saveAvailableBar() {
		writeToFile(this.availableBar, "availableBar.bg");
	}

	private void saveAvailablePIN() {
		writeToFile(this.availablePIN, "availablePIN.bg");
	}

	private void saveStats() {
		writeToFile(this.dayEvents, "stats.bg");
	}

	private void saveMaxParkingSlots() {
		writeToFile(this.maxParkingSlots, "slots.bg");
	}

	/**
	 * Removes the member with the specified PIN-code, if this member exists.
	 * 
	 * @param PIN
	 * @return true if the member was removed, false otherwise.
	 */
	public boolean removeMember(String PIN) {
		Member m = members.get(PIN);
		if (m != null) {
			boolean bicyclesInGarage = false;
			for (String b : m.getBicycles()) { // Remove members bicycles
				if (bicycles.get(b).isParked()) {
					bicyclesInGarage = true;
				}
			}
			if (!bicyclesInGarage) {
				for (String b : m.getBicycles()) {
					bicycles.remove(b);
				}
				members.remove(PIN);
				availablePIN.add(PIN);
				stats.memberChange();
				stats.bicycleChange();
				saveMembers();
				saveAvailableBar();
				saveBicycles();
				saveAvailablePIN();
				saveStats();
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the bicycle with the specified barcode, if it exists.
	 * 
	 * @param barcode
	 * @return true if the bicycle was removed, false otherwise.
	 */
	public boolean removeBicycle(String barcode) {
		Bicycle b = bicycles.get(barcode);
		if (b != null) {
			if (!b.isParked()) {
				bicycles.remove(barcode);
				members.get(b.getOwnerPIN()).removeBicycle(barcode);
				availableBar.add(barcode);
				stats.bicycleChange();
				saveBicycles();
				saveMembers();
				saveAvailableBar();
				saveStats();
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets maximum parking slots available in the garage to the specified
	 * value, if it is higher than or equal to the current number of bicycles
	 * parked in the garage.
	 * 
	 * @param newMax
	 *            Value to set as new max.
	 * @return true if the new value was set, false otherwise.
	 */
	public boolean setMaxParkingSlots(int newMax) {
		if (newMax >= getBicyclesInGarage()) {
			maxParkingSlots = newMax;
			saveMaxParkingSlots();
			return true;
		}
		return false;
	}

	/**
	 * Sets the maximum capacity for bicycles for this database to the specified
	 * value.
	 * 
	 * @param limit
	 *            new value to be set.
	 */
	public void setMaxBicycleCapacity(int limit) {
		bicycles.changeMaxCapacity(limit);
		saveBicycles();
	}

	/**
	 * Returns the number of registered members.
	 * 
	 * @return the number of registered members.
	 */
	public int getMemberSize() {
		return members.size();
	}

	/**
	 * Returns the number of registered bicycles.
	 * 
	 * @return the number of registered bicycles
	 */
	public int getBicycleSize() {
		return bicycles.size();
	}

	/**
	 * Returns the maximum capacity of members for this database.
	 * 
	 * @return the maximum capacity of members for this database.
	 */
	public int getMaxMemberSize() {
		return members.getMaxCapacity();
	}

	/**
	 * Returns the maximum capacity of bicycles for this database.
	 * 
	 * @return the maximum capacity of bicycles for this database.
	 */
	public int getMaxBicycleSize() {
		return bicycles.getMaxCapacity();
	}

	/**
	 * Returns the creation date for this database.
	 * 
	 * @return the creation date for this database.
	 */
	public Date creationDate() {
		return creationDate;
	}

	/**
	 * Returns a list of this database DayEvents.
	 * 
	 * @return a list of this database DayEvents.
	 */
	public ArrayList<DayEvent> getDayEvents() {
		return dayEvents;
	}

	/**
	 * Suspends the member with the specified PIN-code unti the specified date.
	 * 
	 * @param PIN
	 * @param until
	 * @return true if the member exists, false otherwise.
	 */
	public boolean suspendMember(String PIN, Date until) {
		if (members.containsKey(PIN)) {
			members.get(PIN).suspend(until);
			saveMembers();
			return true;
		}
		return false;
	}

	/**
	 * Returns the number of bicycles currently parked in the garage.
	 * @return the number of bicycles currently parked in the garage.
	 */
	public int getBicyclesInGarage() {
		int counter = 0;
		for (Entry<String, Bicycle> e : bicycles.entrySet()) {
			if (e.getValue().isParked())
				counter++;
		}
		return counter;
	}

	/**
	 * Unsuspends the user with the specified PIN-code, if this member exists.
	 * @param PIN
	 * @return true if the member exists, false otherwise.
	 */
	public boolean unsuspendMember(String PIN) {
		if (members.containsKey(PIN)) {
			members.get(PIN).unsuspend();
			saveMembers();
			return true;
		}
		return false;
	}

	/**Checks in the member with the specified PIN-code.
	 * @param PIN
	 */
	public void checkInMember(String PIN) {
		members.get(PIN).checkIn();
		stats.userCheckInChange();
		saveMembers();
		saveStats();
	}

	/**Parks the bicycle with the specified barcode.
	 * @param barCode
	 */
	public void parkBicycle(String barCode) {
		bicycles.get(barCode).park();
		stats.bicyclesInGarageChange();
		saveBicycles();
		saveMembers();
	}

	/**Unparks the bicycle with the specified barcode.
	 * @param barCode
	 */
	public void unParkBicycle(String barCode) {
		bicycles.get(barCode).unPark();
		stats.bicyclesInGarageChange();
		saveBicycles();
		if (members.get(bicycles.get(barCode).getOwnerPIN()).isCheckedIn()) {
			saveMembers();
		}
	}

	/**
	 * Returns the maximum number of available parking slots in the garage.
	 * @return
	 */
	public int getMaxParkingSlots() {
		return maxParkingSlots;
	}

	/**Checks if a member with the specified personal ID number exists.
	 * @param PID Personal ID number
	 * @return true if the member exists, false otherwise.
	 */
	public boolean checkForDuplicateMember(String PID) {
		for (Entry<String, Member> e : members.entrySet()) {
			if (e.getValue().getPIDNbr().equals(PID)) {
				return true;
			}
		}
		return false;
	}
}
