import java.util.ArrayList;

public interface HashTable {
	
	void add(Object a);
	
	ArrayList<Object> lookup(Object a); 
	
	boolean contains(Object a);
	
	String toString();
	
	int size();
}
