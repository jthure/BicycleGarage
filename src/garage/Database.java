package garage;

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

	private void generateCodes(LinkedList<String> list, int digits) {
		Random r = new Random();
		String leadingZeroes = "%0" + digits + "d";

		for (int i = 0; i < digits; i++) {
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
			return true;
		}
		return false;
	}

	public Member getMember(String PIDNbr) {
		return members.get(PIDNbr);
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

	public boolean addBicycle(Member m) {
		String barcode = availableBar.pop(); // Get new bar code
		Bicycle b = new Bicycle(m.getPIN(), barcode); // Create new bicycle
		if (m.addBicycle(barcode)) { // Check if member is full
			if (bicycles.put(barcode, b) != null) { // Check if db full
			// PRINT BARCODE
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

	@SuppressWarnings("unchecked")
	public void loadDatabase() {
		try {
			FileInputStream fin = new FileInputStream("db\\members.bg");
			ObjectInputStream ois = new ObjectInputStream(fin);
			members = (LimitedHashMap<String, Member>) ois.readObject();
			ois.close();
			fin.close();

			fin = new FileInputStream("db\\bicycles.bg");
			ois = new ObjectInputStream(fin);
			bicycles = (LimitedHashMap<String, Bicycle>) ois.readObject();
			ois.close();
			fin.close();

			fin = new FileInputStream("db\\pin.bg");
			ois = new ObjectInputStream(fin);
			availablePIN = (LinkedList<String>) ois.readObject();
			ois.close();
			fin.close();

			fin = new FileInputStream("db\\bar.bg");
			ois = new ObjectInputStream(fin);
			availableBar = (LinkedList<String>) ois.readObject();
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

	public void saveDatabase() {
		try {
			FileOutputStream fout = new FileOutputStream("db\\members.bg");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(members);
			oos.close();
			fout.close();

			fout = new FileOutputStream("db\\bicycles.bg");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(bicycles);
			oos.close();
			fout.close();

			fout = new FileOutputStream("db\\pin.bg");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(availablePIN);
			oos.close();
			fout.close();

			fout = new FileOutputStream("db\\bar.bg");
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
		if (members.remove(PIN) != null) {
			availablePIN.add(PIN);
			return true;
		}
		return false;
	}

	public boolean removeBicycle(String barcode) {
		Bicycle b = bicycles.remove(barcode);
		if (b != null) {
			members.get(b.getOwnerPIN()).removeBicycle(barcode);
			availableBar.add(barcode);
			return true;
		}
		return false;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setMaxParkingslots() {
		// TODO Auto-generated method stub
		return false;
	}

}
