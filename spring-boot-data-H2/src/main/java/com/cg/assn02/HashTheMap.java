package com.cg.assn02;

/**
 * This is custom hashmap to provide limited functionality. It can be used to
 * store, retrieve, delete and get teh size of the map.
 * 
 * @author mageyh
 *
 * @param <K>
 * @param <V>
 */
public class HashTheMap <K, V> implements IMap<K, V> {

	public Entry<K, V> baskets[];

	static int defaultbucketSize = 20;
	
	int actualMapSize = 0;
	/**
	 * Instantiate map with default size
	 */
	public HashTheMap (){
		this (defaultbucketSize);
	}
	/**
	 * Instantiate map with defined size
	 * @param bucketSize
	 */
	public HashTheMap (int bucketSize){
		baskets = new Entry [bucketSize];
	}
	
	
	@Override
	public int size() {
		return actualMapSize;
	}
	
	@Override
	public Boolean isEmpty() {
		return size() == 0;
	}
	/**
	 *This method is used to store a key value pair. 
	 */
	@Override
	public Boolean put(K key, V values) {
		
		checkNullKey(key);
		 
        int bucketIndex = basketsIndexForKey(key);
        Entry<K, V> entry = baskets[bucketIndex];
 
        if (null != entry) {
            boolean hasAdded = false;
            while (!hasAdded) {
                if (isKeyEqual(key, entry)) {
                	// Duplicate key. So, replacing the value for the existing key.
                    entry.setValue(values);
                    hasAdded = true;
                } else if (entry.getNext() == null) {
                	//Collision happened. So, linking this entry to the previous entry in the same bucket.
                    entry.setNext(new Entry<K, V>(key, values));
                    hasAdded = true;
                }
                entry = entry.getNext();
            }
        } else {
            // no entry exist for the given key. have this as a new entry.
        	baskets[bucketIndex] = new Entry<K, V>(key, values);
        }
		actualMapSize++;
		return Boolean.TRUE;
	}
	//Retrieves corresponding value for the key.
	@Override
	public V get(K key) {
		checkNullKey(key);
		Entry<K, V> entry = baskets[basketsIndexForKey(key)];
		while (entry != null && !isKeyEqual(key, entry))
			entry = entry.getNext();
		return entry != null ? entry.getValue() : null;
	}
	
	// deletes the entry for the key.
	@Override
	public Boolean delete(K key) {
		checkNullKey(key);

		Entry<K, V> currEntry = baskets[basketsIndexForKey(key)];

		Entry<K, V> prevEntry = null;

		Boolean done = false;
		
		while (currEntry != null && !done) {

			if (isKeyEqual(key, currEntry)) {
				// found the key match
				System.out.println("Found Key..");
				if (hasNextEntry(currEntry)) {
					// e -> e.next -> next
					// collision case; bucket has next entry
					prevEntry = currEntry.getNext();
					baskets[basketsIndexForKey(prevEntry.getKey())] = prevEntry;
				}else{
					System.out.println("--- removing --- key = "+key);
					baskets[basketsIndexForKey(key)] = null;
				}
				done = true;
				actualMapSize--;

			} else {
				if (hasNextEntry(currEntry)) {
					prevEntry = currEntry;
					currEntry = currEntry.getNext();
				} else {
					return done;
				}
			}
		}

		return done;
	}

	
	public boolean hasNextEntry(Entry<K, V> entry){
    	if(entry.getNext() != null){
    		return true;
    	}else{
    		return false;
    	}
    }

	private boolean isKeyEqual(K key, Entry<K, V> entry) {
		return key.equals(entry.getKey());
	}
	
	private int basketsIndexForKey(K key) {
        int bucketIndex = key.hashCode() % baskets.length;
        return bucketIndex;
    }
 
    private void checkNullKey(K k) {
        if (k == null) {
            throw new NullPointerException("key may not be null");
        }
    }
    
    public void display(){
    	for (int i = 0 ; i < size(); i++){
    		System.out.println("Key = "+baskets[i].getKey() + " Value = "+baskets[i].getValue());
    	}
    }

}
