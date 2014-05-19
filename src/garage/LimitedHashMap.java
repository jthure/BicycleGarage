package garage;

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
		if (size() >= max && !containsKey(key)) {
			return null;
		} else {
			return super.put(key, value);
		}
	}
	
	public boolean changeMaxCapacity(int newMax) {
		if (newMax >= size()) {
			max = newMax;
			return true;
		}
		return false;
	}
	
	public int getMaxCapacity() {
		return max;
	}
}
