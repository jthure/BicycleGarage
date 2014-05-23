package garage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LimitedHashMap<K, V> extends HashMap<K, V> {
	private int max;

	public LimitedHashMap(int maxCapacity) {
		super();
		max = maxCapacity;
	}

	public LimitedHashMap(int initialCapacity, int maxCapacity) {
		super(initialCapacity);
		max = maxCapacity;
	}

	public LimitedHashMap(int initialCapacity, int maxCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public LimitedHashMap(Map<K, V> m) {
		super(m);
	}


	@Override
	public V put(K key, V value) {
		// if (size() >= max && !containsKey(key)) { Bort med negeringen va?
		if (size() >= max || containsKey(key)) {
			return null;
		} else {
			super.put(key, value);
			return value; // �ndrade till detta tempor�rt, blir fel att skicka
							// tillbaka put. Vi kan inte skilja p� n�r det �r
							// fullt och n�r det inte fanns n�gon tidigare
							// mappning d�
		}
	}

	/** Changes the max capacity of this LimitedHashMap to the specified value if it's size less than the this value.
	 * @param newMax Value to be set as new max.
	 * @return True if the new max capcity was set, false otherwise.
	 */
	public boolean changeMaxCapacity(int newMax) {
		if (newMax >= size()) {
			max = newMax;
			return true;
		}
		return false;
	}

	/**Returns this LimitedHashMap's max Capacity.
	 * @return
	 */
	public int getMaxCapacity() {
		return max;
	}
}
