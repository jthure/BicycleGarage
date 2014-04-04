package garage;

public class Bicycle {
	private String id;
	private boolean inGarage;

	public Bicycle(String id) {
		this.id = id;
		inGarage = false;
	}
	
	public String getId(){
		return id;
	}
	public void setID(String id){
		this.id = id;
	}
	
	public boolean isInGarage(){
		return inGarage;
	}
	public void setInGarage(boolean inGarage){
		this.inGarage = inGarage;
	}

}
