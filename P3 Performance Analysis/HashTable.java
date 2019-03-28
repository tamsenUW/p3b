
// Title:           Josiah Tamsen
// Files:           HashTable.java, HashTableADT.java, HashTableTest.java, DataStructureADT.java, 
//									KeyNotFoundException.java, IllegalNullKeyException.java, DuplicateKeyException.java
// Course:          400, 2, 2019
//
// Author:          Josiah Tamsen
// Email:           tamsen@wisc.edu
// Lecturer's Name: Andrew Kuemmel

import java.util.ArrayList;

/**
 * Hash table class that inserts, removes, and accesses data in a constant time.
 * It utilizes an array that stores arraylists in order to achieve this time.
 * Each arraylist stores node values that hold a key and value complexity.
 * 
 * @author Josiah Tamsen
 *
 * @param <K> is the key generic
 * @param <V> is the value generic
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
	private int numKeys; // number of keys in the hash table
	private double loadFactorThreshold; // max loadfactor the current table can have before creating
																			// a bigger table
	private double loadFactor; // number of elements relative to the table size
	private int capacity; // table size of the hash table
	private ArrayList<Pair<K, V>>[] list; // arraylist of an arraylist which holds the pair array. By
																				// making the array hold arraylists, it works as a bucket so
																				// if there is already a node in the index of the array, it
																				// will simply be added to the arraylist in order to avoid
																				// collisions

	/**
	 * This class is the inner class which allows the Data Structure to have an
	 * object which pairs the value and the key
	 * 
	 * @author Josiah
	 *
	 * @param <T> is the type of objects that will be paired
	 */
	private class Pair<K, V> {
		private K key; // unique key to store object
		private V value; // value which is related to the key

		/**
		 * Constructor which instantiates the instance fields
		 * 
		 * @param key   is the unique data type for the object
		 * @param value is the value which is related to the key
		 */
		private Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	/**
	 * No argument constructor that creates a hash table if no arguments are given.
	 * Initializes fields
	 */
	public HashTable() {
		numKeys = 0;
		loadFactorThreshold = .75;
		loadFactor = 0;
		capacity = 11;
		list = new ArrayList[11];
		for (int i = 0; i < capacity; i++) {
			list[i] = new ArrayList<Pair<K, V>>();
		}
	}

	/**
	 * Constructor that creates the hash table and initializes the instance fields
	 * 
	 * @param initialCapacity     is the starting table size
	 * @param loadFactorThreshold is the largest the loadfactor can be before the
	 *                            hashtable resizes and rehashes
	 */
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		numKeys = 0;
		this.loadFactorThreshold = loadFactorThreshold;
		capacity = initialCapacity;
		loadFactor = 0;
		list = new ArrayList[capacity];
		for (int i = 0; i < capacity; i++) {
			list[i] = new ArrayList<Pair<K, V>>();
		}
	}

	/**
	 * Inserts the pair value node into the hashtable, while checking that the table
	 * is the appropiate size and throws the appropiate exceptions
	 * 
	 * @param key   is the unique number attatched to the pair that will be inserted
	 *              into the hashtable
	 * @param value is the value that is paired with the key to be inserted into the
	 *              table
	 * @throws IllegalNullKeyException if the given key is null
	 * @throws DuplicateKeyException   if it tries to insert a key that already
	 *                                 exists
	 */
	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
		if (key == null)
			throw new IllegalNullKeyException();
		isFull();
		int index = Math.abs(key.hashCode()) % capacity;
		for (int i = 0; i < list[index].size(); i++) {
			if (key.hashCode() == list[index].get(i).key.hashCode())
				throw new DuplicateKeyException();
		}
		list[index].add(new Pair(key, value));
		numKeys++;
		loadFactor = (double) numKeys / capacity;
	}

	/**
	 * Given a key, finds where in the hashtable it could be located, and then
	 * searches through the array list to find the key. If it is found then the node
	 * is removed
	 * 
	 * @param key is the designated key to be removed from the hashtable
	 * @return true if the node is removed, false if it is inconclusive
	 * @throws IllegalNullKeyException if a null key is attempted to be removed
	 */
	@Override
	public boolean remove(K key) throws IllegalNullKeyException {
		if (key == null)
			throw new IllegalNullKeyException();
		int index = Math.abs(key.hashCode()) % capacity;
		for (int i = 0; i < list[index].size(); i++) {
			if (key.hashCode() == list[index].get(i).key.hashCode()) {
				list[index].remove(i);
				numKeys--;
				loadFactor = (double) numKeys / capacity;
				return true;
			}
		}
		return false;
	}

	/**
	 * Given a key, it finds the index where the key would be stored, and if it is
	 * in the table, it retrieves the value
	 * 
	 * @param key is the key that will be searched for in the hash table
	 * @throws IllegalNullKeyException if the given key is null
	 * @throws KeyNotFoundException    if the key is not found in the hashtable
	 * @return the value of the found node given the key in the hashtable
	 */
	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if (key == null)
			throw new IllegalNullKeyException();
		int index = Math.abs(key.hashCode()) % capacity;
		for (int i = 0; i < list[index].size(); i++) {
			if (key.hashCode() == list[index].get(i).key.hashCode())
				return list[index].get(i).value;
		}
		throw new KeyNotFoundException();
	}

	/**
	 * Finds the number of keys in the hash table
	 * 
	 * @return the number of keys in the hash table
	 */
	@Override
	public int numKeys() {
		return numKeys;
	}

	/**
	 * Finds the max loadfactor the hashtable can have before resizing/rehashing
	 * 
	 * @return the max loadfactor the hashtable can have
	 */
	@Override
	public double getLoadFactorThreshold() {
		return loadFactorThreshold;
	}

	/**
	 * Finds the load factor of the hashtable given the table size and number of
	 * keys in the hashtable
	 * 
	 * @return the load factor of the hashtable
	 */
	@Override
	public double getLoadFactor() {
		return loadFactor;
	}

	/**
	 * Finds the table size of the hashtable
	 * 
	 * @returns the table size/capacity of the hashtable
	 */
	@Override
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Shows which Hashtable implementation is used
	 * 
	 * @return 4 because it is an array of an array
	 */
	@Override
	public int getCollisionResolution() {
		return 4;
	}

	/**
	 * Checks if the load factor is greater than or equal to the load factor
	 * threshold. If it is then it resizes and rehashes the hashtable
	 */
	private void isFull() {
		loadFactor = (double) numKeys / capacity;
		if (loadFactor >= loadFactorThreshold) { // checks if the the hashtable needs rehashing
			capacity = capacity * 2 + 1; // updates the table size to make more room for nodes
			ArrayList<Pair<K, V>>[] temp = new ArrayList[capacity]; // temporary array with the new
																															// capacity so the original one can
																															// easily reference all the contents
																															// in the array
			for (int i = 0; i < capacity; i++) {
				temp[i] = new ArrayList<Pair<K, V>>(); // makes sure that every arraylist is initialized/not
																								// null
			}
			for (int i = 0; i < list.length; i++) {
				for (int j = 0; j < list[i].size(); j++) {
					temp[Math.abs(list[i].get(j).key.hashCode()) % capacity].add(list[i].get(j)); // rehashes
																																												// the
																																												// indexes
																																												// of each
																																												// item in
																																												// the old
																																												// array so
																																												// it will
																																												// be able
																																												// to be
																																												// accessed
																																												// by the
																																												// other
																																												// methods
				}
			}
			list = temp;
		}
	}
}
