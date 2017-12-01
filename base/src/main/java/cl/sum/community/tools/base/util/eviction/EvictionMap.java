package cl.sum.community.tools.base.util.eviction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * EvictionMap wrap java.util.Map, adding an eviction policy ( based on max time
 * ) to automatically drop invalid items
 * 
 * @author mauronunez
 * 
 *
 * @param <K>
 * @param <V>
 */
public class EvictionMap<K, V> {

	private long validity;
	private Map<K, Item<V>> map = new ConcurrentHashMap<>();

	private static class Item<V> {

		private V value;
		private long timestamp;

		public Item(V value) {
			this.value = value;
			this.timestamp = System.currentTimeMillis();
		}

		public V value() {
			return this.value;
		}

		public long getTimestamp() {
			return this.timestamp;
		}

	}

	public EvictionMap(long validity) {
		this.validity = validity;
	}

	public V put(K key, V value) {
		Item<V> item = map.put(key, new Item<V>(value));
		return item == null ? null : item.value();
	}
	
	private boolean isValid(Item<V> item) {
		return (System.currentTimeMillis()-item.getTimestamp()) < validity;
	}

	public V get(K key) {
		Item<V> item = map.get(key);
		V value = null;
		if (item != null && isValid(item)) {
			value = item.value();
		}
		return value;
	}

}
