package garage;

import interfaces.DatabaseInterface;

import java.util.*;

import entities.Bicycle;
import entities.Member;

public class Database implements DatabaseInterface{
	private LimitedHashMap<String, LinkedList<Member>> members;
	private LimitedHashMap<String, LinkedList<Bicycle>> bicycles;
	private LinkedList<String> availablePIN, availableBar;
	
	public Database(int maxBikes, int maxMembers) {
		members = new LimitedHashMap<String, LinkedList<Member>>(maxMembers);
		bicycles = new LimitedHashMap<String, LinkedList<Bicycle>>(maxBikes);
//		generateCodes(availablePIN, 6);
//		generateCodes(availableBar, 5);
	}
	
	private void generateCodes(LinkedList<String> list, int digits) {
		Random r = new Random();
		String leadingZeroes = "%0" + digits + "d";
		
		for (int i = 0; i < digits; i++) {
			list.add(String.format(leadingZeroes, i));
		}
		Collections.shuffle(list);
	}
	
	public boolean addMember(String fName, String lName, String PID, String tel) {
//		if (members.put(m.getPIDNbr(), m) != null) {
//			PIN_MemberPairs.put(m.getPIN(), m.getPIDNbr());
//			
//		}
		return false;
	}
//	
	public Member getMember(String PIDNbr) {
//		return members.get(PIDNbr);
		return null;
	}
	
	public boolean addBicycle(Member m){
		return false;
	}
	public Bicycle getBicycle(String barcode){
		return null;
	}
	
	
	public void loadDatabase(){
		
	}
	public void saveDatabase(){
		
	}

	@Override
	public boolean removeMember(String PIDNbr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeBicycle(String barcode) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
