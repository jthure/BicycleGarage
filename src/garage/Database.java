package garage;

import java.util.*;

import entities.Bicycle;
import entities.Member;

public class Database {
	private LimitedHashMap<String, Bicycle> bicycles;
	private LimitedHashMap<String, Member> members;
	private LinkedList<String> availablePIN, availableBar;
	
	public Database(int maxBikes, int maxMembers) {
		bicycles = new LimitedHashMap<String, Bicycle>(maxBikes);
		members = new LimitedHashMap<String, Member>(maxMembers);
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
}
