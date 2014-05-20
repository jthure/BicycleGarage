package interfaces;

import java.util.LinkedList;

import entities.Bicycle;
import entities.Member;

public interface DatabaseInterface {
	public boolean addMember(String fName, String lName, String PIDNbr, String telNbr);

	public Member getMember(String PIDNbr);
	public LinkedList<Member> getUsersWithNameRegex(String name);
	
	public boolean removeMember(String PIDNbr);
	
//	public boolean suspendMember (String PIDNbr);

	public boolean addBicycle(Member m);

	public Bicycle getBicycle(String barcode);
	
	public boolean removeBicycle(String barcode);

	public void loadDatabase();

	public void saveDatabase();
	
	public boolean isFull();
	
	public boolean setMaxParkingslots();
}
