package garage;

public class _Test {
	public static void test(Database db){
		User user1 = db.addUser("Jonas Thuresson");
		System.out.println(user1.getName() + ": " + user1.getPin());
		Bicycle bicycle1 = db.addBicycle(user1);
		System.out.println(bicycle1.getId());
	}
}
