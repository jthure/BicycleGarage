package garage;

import java.util.*;

import entities.Bicycle;
import entities.Member;

public class Database {
	private LimitedHashMap<String, LinkedList<Member>> members;
	private LimitedHashMap<String, LinkedList<Bicycle>> bicycles;
	private HashMap<String, String> PIN_MemberPairs;
	private LinkedList<String> availablePIN, availableBar;
	
	public Database(int maxBikes, int maxMembers) {
		members = new LimitedHashMap<String, LinkedList<Member>>(maxMembers);
		bicycles = new LimitedHashMap<String, LinkedList<Bicycle>>(maxBikes);
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
	
//	public boolean addMember(Member m) {
//		if (members.put(m.getPIDNbr(), m) != null) {
//			PIN_MemberPairs.put(m.getPIN(), m.getPIDNbr());
//			
//		}
//	}
//	
//	public Member getMember(String PIDNbr) {
//		return members.get(PIDNbr);
//	}
}
