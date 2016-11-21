package com.cg.assn02;

public interface IMap<K,V > {
	
	public int size();
	
	public Boolean put(K k, V v );
	
	public Boolean isEmpty();
	
	public V get(K k);
	
	public Boolean delete(K k);
	
}
