import java.util.ArrayList;

public class HashTableClass implements HashTable {
	
	private Node[] buckets;
	private int size;
	private int capacity;
	
	public HashTableClass(int capacity) {
		this.capacity = capacity;
		buckets = new Node[capacity];
		size = 0;
	}
	
	@Override
	public void add(Object element) {
		if(contains(element)) {
			return;
		}
	
		int h = element.hashCode(); 		//compute the hash code of the element
		int index = h % buckets.length;		//divide it modulo the size of the buckets array
		Node newNode;
		if(buckets[index] != null) { 		//bucket already contains one or more elements
			newNode = new Node(element, buckets[index]);
			newNode.nodesAfter = buckets[index].nodesAfter + 1; 
		}else{ 							    //no elements in the bucket yet
			newNode = new Node(element, null);
		}
		buckets[index] = newNode;
		size++;
	}

	@Override
	/**
	 * @param element The element to lookup in the hash table.
	 * @return an ArrayList of all the objects at the given hash location
	 */
	public ArrayList<Object> lookup(Object element) {
		
		ArrayList<Object> objects = new ArrayList<>();
		if(!contains(element)) {
			return objects;
		}
		int index = element.hashCode() % buckets.length;
		Node n = buckets[index];
		while(n.next != null) {
			objects.add(n.data);
			n = n.next;
		}
		objects.add(n.data); //to add the last node's data
		return objects;
	}

	@Override
	public boolean contains(Object element) {
		int hash = element.hashCode();
		int index = hash % buckets.length;
		if(buckets[index] == null) {
			return false;
		}
		Node n = buckets[index];
		while(n.next != null) {
			if(n.data.equals(element)) {
				return true;
			}
			n = n.next;
		}
		if(n.data.equals(element)) { //check for equality with the last node
			return true; 
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < capacity; i++) {
			s += "[index:" + i + "; ";
			if (buckets[i] != null) {
				s += "entries: " + buckets[i].data;
				Node n =  buckets[i];
				while(n.next != null) {
					n = n.next;
					s+= "," + n.data;
				}
			}
			s+= "]\n";
		}
		return s;
	}
	
	/**
	 * Returns the length of the longest chain in the hash table.
	 * @return longestChain the longest chain
	 */
	public int longestChain() {
		int longestChain = 0;
		for (int i = 0; i < buckets.length; i++) {
			if(buckets[i] != null && buckets[i].nodesAfter > longestChain) {
				longestChain = buckets[i].nodesAfter;
				longestChain = longestChain + 1;
			}
		}
		return longestChain;
	}
	
	/**
	 * Returns the number of empty spaces in the hash table.
	 * @return spaces An integer representing the number of empty spaces
	 */
	public int emptySpaces() {
		int spaces = 0;
		for (int i = 0; i < buckets.length; i++) {
			if(buckets[i] == null) {
				spaces++;
			}
		}
		return spaces;
	}
	
	class Node {
		public Object data;
		public Node next;
		public int nodesAfter;
		
		public Node(Object data, Node next) {
			this.data = data;
			this.next = next;
			nodesAfter = 0;
		}
	}
	

}
