package garage;

import interfaces.BarcodePrinter;
import interfaces.DatabaseInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.Map.Entry;

import entities.Bicycle;
import entities.Member;

public class Database implements DatabaseInterface {
	private LimitedHashMap<String, Member> members; // Key = PIN
	private LimitedHashMap<String, Bicycle> bicycles; // Key = Bar code
	private LinkedList<String> availablePIN, availableBar;

	public Database(int maxBikes, int maxMembers) {
		members = new LimitedHashMap<String, Member>(maxMembers);
		bicycles = new LimitedHashMap<String, Bicycle>(maxBikes);
		availablePIN = new LinkedList<String>();
		availableBar = new LinkedList<String>();
		generateCodes(availablePIN, 6);
		generateCodes(availableBar, 5);
	}
	
	public Database(String members, String bicycles, String availablePIN, String availableBar) {
		loadDatabase(members, bicycles, availablePIN, availableBar);
	}
 
	private void generateCodes(LinkedList<String> list, int digits) {
		String leadingZeroes = "%0" + digits + "d";
		for (int i = 0; i < Math.pow(10, digits); i++) {
			list.add(String.format(leadingZeroes, i));
		}
		Collections.shuffle(list);
	}

	public boolean addMember(String fName, String lName, String PIDNbr, String telNbr) {
		String PIN = availablePIN.pop(); // Get new PIN
		Member m = new Member(fName, lName, PIDNbr, telNbr, PIN); // Create member
		if (members.put(PIN, m) != null) { // Check if full
			return true;
		}
		return false;
	}

	public Member getMember(String PIN) {
		return members.get(PIN);
	}

	public LinkedList<Member> getUsersWithNameRegex(String name) {
		LinkedList<Member> matchedUsers = new LinkedList<>();
		Set<Entry<String, Member>> set = members.entrySet();
		for (Entry<String, Member> e : set) {
			Member member = e.getValue();
			String regexName = "(?i)" + name + ".*";
			if (member.getInfo()[0].matches(regexName)
					|| member.getInfo()[1].matches(regexName)) {
				matchedUsers.add(member);
			}
		}
		return matchedUsers;
	}

	public boolean addBicycle(Member m, BarcodePrinter p) {
		String barcode = availableBar.pop(); // Get new bar code
		Bicycle b = new Bicycle(m.getPIN(), barcode); // Create new bicycle
		if (m.addBicycle(barcode)) { // Check if member is full
			if (bicycles.put(barcode, b) != null) { // Check if db full
				p.printBarcode(barcode);
				return true;
			}
			m.removeBicycle(barcode); //Remove inserted barcode if db was full
		}
		availableBar.add(barcode); //Put back retrieved barcode if failed.
		return false;
	}

	public Bicycle getBicycle(String barcode) {
		return bicycles.get(barcode);
	}

	public String changePIN(String oldPIN) {
		Member m = members.get(oldPIN);		// Get member	
		members.remove(oldPIN);				// Remove old entry
		String newPIN = availablePIN.pop();	// Get new PIN
		m.setPIN(newPIN);					// Change PIN
		members.put(newPIN, m);				// Put in new entry
		for (String s : m.getBicycles()) {
			Bicycle b = bicycles.get(s);		// Get bicycle
			b.setOwnerPIN(newPIN);				// Change PIN
			bicycles.put(b.getBarcode(), b);	// Put back in
		}
		availablePIN.add(oldPIN);		// Put back old PIN
		return newPIN;
	}
	
	@SuppressWarnings("unchecked")
	public void loadDatabase(String members, String bicycles, String availablePIN, String availableBar) {
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

	public void saveDatabase(String members, String bicycles, String availablePIN, String availableBar) {
		try {
			FileOutputStream fout = new FileOutputStream("db\\" + members);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(members);
			oos.close();
			fout.close();

			fout = new FileOutputStream("db\\" + bicycles);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(bicycles);
			oos.close();
			fout.close();

			fout = new FileOutputStream("db\\" + availablePIN);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(availablePIN);
			oos.close();
			fout.close();

			fout = new FileOutputStream("db\\" + availableBar);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(availableBar);
			oos.close();
			fout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean removeMember(String PIN) {
		Member m = members.get(PIN);
		if (members.remove(PIN) != null) {
			availablePIN.add(PIN);
			for (String b : m.getBicycles()) {	// Remove members bicycles
				bicycles.remove(b);
			}
			return true;
		}
		return false;
	}

	public boolean removeBicycle(String barcode) {
		Bicycle b = bicycles.remove(barcode);	// Remove bicycle
		if (b != null) {
			Member m = members.get(b.getOwnerPIN());	// Find owner
			m.removeBicycle(barcode);	// Remove bicycle from member
			members.put(m.getPIN(), m);	// Put member back into map
			availableBar.add(barcode);
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

	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setMaxParkingslots() {
		// TODO Auto-generated method stub
		return false;
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
		Member m = new Member(info[0], info[1], info[2], info[3], PIN); // Create member
		if (members.put(PIN, m) != null) { // Check if full
			return PIN;
		}
		return null;
	}
}
