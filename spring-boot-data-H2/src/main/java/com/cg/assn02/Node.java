package com.cg.assn02;

public interface Node<K, V> {
	
	public K getKey();
	
	public V getValue();
	
	public Node<K, V> getNext();
	
	public int hashCode();
	
	public boolean equals(Object o);

}
