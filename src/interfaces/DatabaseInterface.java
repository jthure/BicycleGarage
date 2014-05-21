package interfaces;

import java.util.Date;
import java.util.LinkedList;

import entities.Bicycle;
import entities.Member;

public interface DatabaseInterface {
	public boolean addMember(String fName, String lName, String PIDNbr, String telNbr);

	public Member getMember(String PIN);
	
	public LinkedList<Member> findMembersByName(String name);
	
<<<<<<< HEAD
	public boolean removeMember(String PIN);
	
	public boolean suspendMember(String PIN, Date suspendUntil);
	
	public boolean unsuspendMember(String PIN);
=======
	public boolean removeMember(String PIDNbr);
>>>>>>> 724125b4b33aa2adb1884afd76443cc79c8faa2d

	public boolean suspendMember (String PIDNbr);
	public boolean addBicycle(Member m, BarcodePrinter p);

	public Bicycle getBicycle(String barcode);
	
	public boolean removeBicycle(String barcode);

	public void loadDatabase(String members, String bicycles, String availablePIN, String availableBar, String stats);

//	public void saveDatabase(String members, String bicycles, String availablePIN, String availableBar);
	
	public boolean setMaxParkingslots();
	
	public int getBicyclesInGarage();
	
	public int getBicycleSize();
	
	public int getMemberSize();
}
