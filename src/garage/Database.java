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

public class Database implements DatabaseInterface {
	private LimitedHashMap<String, Member> members; // Key = PIN
	private LimitedHashMap<String, Bicycle> bicycles; // Key = Bar code
	private LinkedList<String> availablePIN, availableBar;
	private ArrayList<DayEvent> dayEvents;
	private Date creationDate;
	private Statistics stats;

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
	}

	public Database(String members, String bicycles, String availablePIN,
			String availableBar, String stats) {
		loadDatabase(members, bicycles, availablePIN, availableBar, stats);
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

	public boolean addMember(String fName, String lName, String PIDNbr,
			String telNbr) {
		String PIN = availablePIN.pop(); // Get new PIN
		Member m = new Member(fName, lName, PIDNbr, telNbr, PIN); // Create
																	// member
		if (members.put(PIN, m) != null) { // Check if full
			stats.memberChange();
			saveMembers(); saveAvailableBar();
			return true;
		}
		return false;
	}

	public Member getMember(String PIN) {
		return members.get(PIN);
	}

	public LinkedList<Member> findMembersByName(String name) {
		LinkedList<Member> matchedMembers = new LinkedList<>();
		Set<Entry<String, Member>> set = members.entrySet();
		for (Entry<String, Member> e : set) {
			Member member = e.getValue();
			String regexName = "(?i)" + name + ".*";
			if (member.getInfo()[0].matches(regexName)
					|| member.getInfo()[1].matches(regexName)) {
				matchedMembers.add(member);
			}
		}
		return matchedMembers;
	}

	public boolean addBicycle(Member m, BarcodePrinter p) {
		String barcode = availableBar.pop(); // Get new bar code
		Bicycle b = new Bicycle(m.getPIN(), barcode); // Create new bicycle
		if (m.addBicycle(barcode)) { // Check if member is full
			if (bicycles.put(barcode, b) != null) { // Check if db full
				p.printBarcode(barcode);
				stats.bicycleChange();
				saveBicycles(); saveAvailableBar();
				return true;
			}
			m.removeBicycle(barcode); // Remove inserted barcode if db was full
		}
		availableBar.add(barcode); // Put back retrieved barcode if failed.
		return false;
	}

	public Bicycle getBicycle(String barcode) {
		return bicycles.get(barcode);
	}

	public String changePIN(String oldPIN) {
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
		return newPIN;
	}

	@SuppressWarnings("unchecked")
	public void loadDatabase(String members, String bicycles,
			String availablePIN, String availableBar, String stats) {
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			System.out.println("The path db/"+fileName+" could not be found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error while serializing");
			e.printStackTrace();
		}

	}
	public void saveMembers(){
		writeToFile(this.members, "members.bg");
		saveStats();
	}
	public void saveBicycles(){
		writeToFile(this.bicycles, "bicycles.bg");
		saveStats();
	}
	public void saveAvailableBar(){
		writeToFile(this.availableBar, "availableBar.bg");
		saveStats();
	}
	public void saveAvailablePIN(){
		writeToFile(this.availablePIN, "availablePIN.bg");
		saveStats();
	}
	public void saveStats(){
		writeToFile(this.availablePIN, "availablePIN.bg");
		saveStats();
	}

	public boolean removeMember(String PIN) {
		Member m = members.remove(PIN);
		if (m != null) {
			availablePIN.add(PIN);
			for (String b : m.getBicycles()) { // Remove members bicycles
				bicycles.remove(b);
			}
			stats.memberChange();
			stats.bicycleChange();
			saveMembers(); saveAvailableBar(); saveBicycles(); saveAvailablePIN();
			return true;
		}
		return false;
	}

	public boolean removeBicycle(String barcode) {
		Bicycle b = bicycles.remove(barcode); // Remove bicycle
		if (b != null) {
			members.get(b.getOwnerPIN()).removeBicycle(barcode);	// Find owner
//			m.removeBicycle(barcode);	// Remove bicycle from member
//			members.put(m.getPIN(), m);	// Put member back into map
			// detta?
			availableBar.add(barcode);
			stats.bicycleChange();
			saveBicycles();saveAvailableBar();
			return true;
		}
		return false;
	}
	
//	public Member[] getMemberList() {
//		Member[] ms = new Member[members.size()];
//		int i = 0;
//		for (Member m : members.values()) {
//			ms[i] = m;
//			i++;
//		}
//		return ms;
//	}

	public boolean setMaxParkingslots() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setMaxBicycleCapacity(int limit) {
		bicycles.changeMaxCapacity(limit);
	}

	public int getMemberSize() {
		return members.size();
	}

	public int getBicycleSize() {
		return bicycles.size();
	}

	public int getMaxMemberSize() {
		return members.getMaxCapacity();
	}

	public int getMaxBicycleSize() {
		return bicycles.getMaxCapacity();
	}

	public Date creationDate() {
		return creationDate;
	}

	public ArrayList<DayEvent> getDayEvents() {
		return dayEvents;
	}

	public boolean suspendMember(String PIDNbr) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Test methods
	 */
	public LinkedList<String> getAvailPIN() {
		return availablePIN;
	}

	public LinkedList<String> getAvailBar() {
		return availableBar;
	}

	public String addMember(String[] info) {
		String PIN = availablePIN.pop(); // Get new PIN
		Member m = new Member(info[0], info[1], info[2], info[3], PIN); // Create
																		// member
		if (members.put(PIN, m) != null) { // Check if full
			saveMembers();saveAvailablePIN();
			return PIN;
		}
		return null;
	}

	public int getBicyclesInGarage() {
		int counter = 0;
		for (Entry<String, Bicycle> e : bicycles.entrySet()) {
			if (e.getValue().isParked())
				counter++;
		}
		return counter;
	}

	public Statistics getStats() {
		return stats;
	}
	public LimitedHashMap<String, Member> getMembers(){
		return members;
	}
	public LimitedHashMap<String, Bicycle> getBicycles(){
		return bicycles;
	}
}
