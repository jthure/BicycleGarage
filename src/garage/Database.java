package garage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class Database {

	private static final int MAX_USERS = 100000;
	private static final int MAX_BARCODES = 100000;
	static final long CHECK_IN_TIMER = 600000;

	private HashMap<String, User> users;
	private HashMap<String, Bicycle> bicycles;

	private LinkedList<String> barcodes;
	private LinkedList<String> pincodes;

	public Database() {
		users = new HashMap<>();
		bicycles = new HashMap<>();
		barcodes = new LinkedList<>();
		pincodes = new LinkedList<>();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < MAX_BARCODES; i++) {
			sb.setLength(0);
			sb.append(i);
			while (sb.length() < Bicycle.ID_LENGTH) {
				sb.insert(0, "0");
			}
			barcodes.add(sb.toString());

		}
		for (int i = 0; i < MAX_USERS; i++) {
			sb.setLength(0);
			sb.append(i);
			while (sb.length() < User.PIN_LENGTH) {
				sb.insert(0, "0");
			}
			pincodes.add(sb.toString());

		}
		Collections.shuffle(barcodes);
		Collections.shuffle(pincodes);

	}

	public String getNextBarcode() {
		if (hasNextBarcode()) {
			return barcodes.remove(0);
		}
		return null;
	}

	private boolean hasNextBarcode() {
		return barcodes.size() != 0;
	}

	public String getNextPincode() {
		if (hasNextPincode()) {
			return pincodes.remove(0);
		}
		return null;
	}

	private boolean hasNextPincode() {
		return pincodes.size() != 0;
	}

	public void saveBarcodes() {
		writeToFile(barcodes, "barcodes.db");
	}

	public void saveUsers() {
		writeToFile(users, "users.db");
	}

	public void saveBicycles() {
		writeToFile(bicycles, "bicycles.db");
	}

	public void readBarcodes() {
		try {
			LinkedList<String> input = (LinkedList<String>) readFromFile(
					"barcodes.db").readObject();
			barcodes = input;
		} catch (Exception e) {
			System.out.println("Error while deserializing barcodes");
			e.printStackTrace();
		}
	}

	public void readUsers() {
		try {
			HashMap<String, User> input = (HashMap<String, User>) readFromFile(
					"users.db").readObject();
			users = input;
		} catch (Exception e) {
			System.out.println("Error while deserializing barcodes");
			e.printStackTrace();
		}
	}

	public void readBicycles() {
		try {
			HashMap<String, Bicycle> input = (HashMap<String, Bicycle>) readFromFile(
					"bicycles.db").readObject();
			bicycles = input;
		} catch (Exception e) {
			System.out.println("Error while deserializing barcodes");
			e.printStackTrace();
		}
	}

	private <T extends Serializable> void writeToFile(T object, String fileName) {
		try {
			FileOutputStream fout = new FileOutputStream(new File("db/"
					+ fileName));
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(object);
			oout.close();
		} catch (Exception e) {
			System.out.println("Error while serializing");
			e.printStackTrace();
		}
	}

	private ObjectInput readFromFile(String fileName) {
		ObjectInput input = null;
		try {
			InputStream file = new FileInputStream("db/" + fileName);
			InputStream buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);
			file.close();
		} catch (Exception e) {
			System.out.println("Error while deserializing");
			e.printStackTrace();
		}
		return input;
	}

	public User addUser(String name) {
		String pin = getNextPincode();
		if (pin != null) {
			User user = new User(name, pin);
			users.put(user.getPin(), user);
			return user;
		} else {
			return null;
		}
	}

	public Bicycle addBicycle(User user) {
		String barcode = getNextBarcode();
		if (barcode != null) {
			Bicycle bicycle = new Bicycle(getNextBarcode());
			if (user.addBicyle(bicycle)) {
				bicycles.put(bicycle.getId(), bicycle);
				return bicycle;
			}else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	public User getUserWithPin(String pin){
		return users.get(pin);
	}
	public LinkedList<User> getUsersWithNameRegex(String name){
		StringBuilder sb = new StringBuilder();
		LinkedList<User> matchedUsers = new LinkedList<>();
		Set<Entry<String,User>> set = users.entrySet();
		for (Entry<String,User> e:set){
			User user = e.getValue();
			String regexName = "(?i)"+name+".*";
			if (user.getName().matches(regexName)){
				matchedUsers.add(user);
			}
		}
		return matchedUsers;
	}
	
	public User removeUser(String pin){
		User user = users.remove(pin);
		List<Bicycle> userBicycles = user.getBicycles();
		for (Bicycle b : userBicycles){
			bicycles.remove(b.getId());
		}
		return user;
	}
	
	public Bicycle getBicycleWithID(String bicycleID){
		return bicycles.get(bicycleID);
	}

}
