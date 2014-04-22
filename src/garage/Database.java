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

public class Database {

	private static final int MAX_USERS = 100000;
	private static final int MAX_BARCODES = 100000;

	private HashMap<String, User> users;
	private HashMap<String, Bicycle> bicycles;

	private LinkedList<String> barcodes;

	public Database() {
		users = new HashMap<>();
		bicycles = new HashMap<>();
		barcodes = new LinkedList<>();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < MAX_BARCODES; i++) {
			sb.setLength(0);
			sb.append(i);
			while (sb.length() < Bicycle.ID_LENGTH) {
				sb.insert(0, "0");
			}
			barcodes.add(sb.toString());

		}
		Collections.shuffle(barcodes);

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
		try{
		LinkedList<String> input = (LinkedList<String>) readFromFile("barcodes.db").readObject();
		barcodes = input;
		}
		catch(Exception e){
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

}
