/**
 * Filename:   MyProfiler.java
 * Project:    p3b-201901     
 * Authors:    Josiah Tamsen Lecture 4
 *
 * Semester:   Spring 2019
 * Course:     CS400
 * 
 * Due Date:   TODO: March 28 10PM
 * Version:    1.0
 */

// Used as the data structure to test our hash table against
import java.util.TreeMap;

/**
 * Class method which constructs a profile with a hashtable and treepmap. It
 * then calls the insert/get methods and inserts/gets them x times, x given by
 * the user
 * 
 * @author Josiah Tamsen
 *
 * @param <K> Generic for the key
 * @param <V> Generic for the value
 */
public class MyProfiler<K extends Comparable<K>, V> {

	HashTableADT<K, V> hashtable; // implemented hashtable
	TreeMap<K, V> treemap; // java treemap object

	/**
	 * No argument constructor that instantiates the hashtable and treemap
	 */
	public MyProfiler() {
		hashtable = new HashTable<K, V>();
		treemap = new TreeMap<K, V>();
	}

	/**
	 * Inserts a key and value using both the treemap and hashtable implementation
	 * 
	 * @param key   is the key to be implemented
	 * @param value is the value to be implemented
	 */
	public void insert(K key, V value) {
		treemap.put(key, value);
		try {
			hashtable.insert(key, value);
		} catch (IllegalNullKeyException e) {
		} catch (DuplicateKeyException e) {
		}
	}

	/**
	 * Retrieves a key using both the treemap and hashtable implementation
	 * 
	 * @param key   is the key to be implemented
	 * @param value is the value to be implemented
	 */
	public void retrieve(K key) {
		treemap.get(key);
		try {
			hashtable.get(key);
		} catch (IllegalNullKeyException e) {
		} catch (KeyNotFoundException e) {
		}
	}

	public void remove(K key) {
		treemap.remove(key);
		try {
			hashtable.remove(key);
		} catch (IllegalNullKeyException e) {
		}
	}

	/**
	 * Main class that creates a profile and then insert/retrieves from the
	 * hashtable/treemap x times given by the user
	 * 
	 * @param args the number of elements to be inserted/retreived will be passed as
	 *             an argument
	 */
	public static void main(String[] args) {
		try {
			int numElements = Integer.parseInt(args[0]);
			MyProfiler<Integer, Integer> profile = new MyProfiler<Integer, Integer>();
			for (int i = 0; i < numElements; i++) {
				profile.insert(i, i);
			}
			for (int i = 0; i < numElements; i++) {
				profile.retrieve(i);
			}
			for (int i = 0; i < numElements; i++) {
				profile.remove(i);
			}
			String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
			System.out.println(msg);
		} catch (Exception e) {
			System.out.println("Usage: java MyProfiler <number_of_elements>");
			System.exit(1);
		}
	}
}
