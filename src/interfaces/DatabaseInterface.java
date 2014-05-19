package interfaces;

import entities.Bicycle;
import entities.Member;

public interface DatabaseInterface {
	public boolean addMember(Member m);

	public Member getMember(String PIDNbr);
	
	public boolean removeMember(String PIDNbr);
	
//	public boolean suspendMember (String PIDNbr);

	public boolean addBicycle(Member m);

	public Bicycle getBicycle(String barcode);
	
	public boolean removeBicycle(String barcode);

	public void loadDatabase();

	public void saveDatabase();
}
