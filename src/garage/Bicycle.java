package garage;

public class Bicycle {
	private static final int ID_LENGTH = 5;
	private String id;
	private User owner;
	private boolean inGarage;

	/**
	 * Creates new bicycle with id, not parked in garage.
	 * 
	 * @param id
	 *            : Barcode associated with the bicycle.
	 */
	public Bicycle(String id) {
		if(id.length()!=ID_LENGTH){
			throw new IllegalArgumentException("Wrong length of bicycle ID");
		}
		this.id = id;
		inGarage = false;
	}

	/**
	 * Returns the bicycles barcode.
	 * 
	 * @return Returns the bicycles barcode.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the bicyles barcode.
	 * 
	 * @param id
	 *            : The barcode which should be set.
	 */
	public boolean setID(String id) {
		if (id.length() == ID_LENGTH) {
			this.id = id;
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Returns wether the bicycle is in the garage or not.
	 * 
	 * @return true if the bicycle is in the garage, false otherwise.
	 */
	public boolean isInGarage() {
		return inGarage;
	}

	/**
	 * Parks the bicycle in the garage (sets inGarage to true).
	 */
	public void parkInGarage() {
		this.inGarage = true;
	}

	/**
	 * Unparks the bicycle in the garage (sets inGarage to false).
	 */
	public void unParkInGarage() {
		this.inGarage = false;
	}

	/**
	 * Returns the bicycles owner.
	 * 
	 * @return the bicycles owner.
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * Sets the owner to this bike to owner.
	 * 
	 * @param owner
	 *            to be set.
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

}
