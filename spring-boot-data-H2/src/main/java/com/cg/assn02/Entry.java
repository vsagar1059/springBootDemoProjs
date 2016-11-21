package com.cg.assn02;

import java.util.Map;
import java.util.Objects;

public class Entry<K, V> implements Node<K, V>{
    
    private Entry<K, V> next;
    private final K key;
    private V value;
 
    public Entry(K key, V value) {
        this.key = key;
        this.setValue(value);
    }
 
    public K getKey() {
        return key;
    }
 
    public void setValue(V value) {
        this.value = value;
    }
 
    public V getValue() {
        return value;
    }
 
    public void setNext(Entry<K, V> next) {
        this.next = next;
    }
 
    public Entry<K, V> getNext() {
        return next;
    }
    
    
    @Override
    public int hashCode(){
    	
		return Objects.hashCode(key) ^ Objects.hashCode(value);
    	
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof Map.Entry) {
            Node<K,V> e = (Node<K, V>)o;
            if (Objects.equals(key, e.getKey()) &&
                Objects.equals(value, e.getValue()))
                return true;
        }
        return false;
    }
}