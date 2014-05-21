package interfaces;

import java.util.LinkedList;

import entities.Bicycle;
import entities.Member;

public interface DatabaseInterface {
	public boolean addMember(String fName, String lName, String PIDNbr, String telNbr);

	public Member getMember(String PIDNbr);
	
	public LinkedList<Member> findMembersByName(String name);
	
	public boolean removeMember(String PIDNbr);
	
	public boolean suspendMember (String PIDNbr);

	public boolean addBicycle(Member m, BarcodePrinter p);

	public Bicycle getBicycle(String barcode);
	
	public boolean removeBicycle(String barcode);

	public void loadDatabase(String members, String bicycles, String availablePIN, String availableBar);

	public void saveDatabase(String members, String bicycles, String availablePIN, String availableBar);
	
	public boolean isFull();
	
	public boolean setMaxParkingslots();
	
	public int getBicyclesInGarage();
	
	public int getBicycleSize();
	
	public int getMemberSize();
}
