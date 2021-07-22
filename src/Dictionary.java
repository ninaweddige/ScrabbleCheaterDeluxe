import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Dictionary {
	HashTableClass words;
	int entries;
	
	/**
	 * Constructs a Dictionary.
	 * @param aFile The file containing all words that will be added to the dictionary.
	 * @throws FileNotFoundException 
	 */
	public Dictionary(File aFile) throws FileNotFoundException {
		entries = countWords(aFile);
		words = readFile(aFile, entries);
	}
	
	/**
	 * Calculates the size of the hash table
	 * @param entries The number of entries.
	 * @return The size for the hash table.
	 */
	public int calculateSize(int entries) {
		int size = (int) (entries * .7);
		return getNextPrime(size);
	}
	
	/**
	 * Checks if a number is prime
	 */
	public boolean isPrime(int n) {
		boolean prime = true;
		if(n != 2 && n % 2 == 0) {
			return prime = false;
		}
		
		for (int i = 3; i <= Math.sqrt(n); i+= 2) {
			if(i != n && n % i == 0) {
				return prime = false;
			}
		}
		return prime = true;	
	}
	
	/**
	 * Finds the next biggest prime number.
	 */
	public int getNextPrime(int n) {
		for (int i = n; true; i++) {
			if(isPrime(i)) {
				return i;
			}
		}
	}
	
	/**
	 * Counts the words in the file.
	 * @throws FileNotFoundException 
	 */
	public int countWords(File aFile) throws FileNotFoundException {
		Scanner in = new Scanner(aFile);
		int words = 0;
		while(in.hasNext()) {
			words++;
			in.next();
		}
		return words;
	}
	
	/**
	 * Reads a file and adds each word to a hash table. Also counts the words.
	 * @param aFile The file to read from.
	 * @return table A hash table containing all unique words in the file. 
	 * @throws FileNotFoundException 
	 */
	public HashTableClass readFile(File aFile, int size) throws FileNotFoundException {
		HashTableClass table = new HashTableClass(calculateSize(size));
		Scanner in = new Scanner(aFile);
		while(in.hasNext()) {
			String s = in.next();
			ScrabbleWord sw = new ScrabbleWord(s);
			table.add(sw);
		}
		in.close();
		return table;
	}
	
	/**
	 * Reads from the file containing all scrabble words and writes the ones with 
	 * the specified length to a file.
	 * @param aFile
	 * @throws FileNotFoundException
	 */
	public static void getXLengthWords(File aFile, int wordLength) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(wordLength + "LetterScrabbleWords.txt");
		Scanner in = new Scanner(aFile);
		while(in.hasNext()) {
			String s = in.next();
			if(s.length() == wordLength) {
				out.println(s);
			}
		}
		out.close();
	}
	
	/**
	 * Looks up the given word in the hash table and returns an ArrayList of the words at that location.
	 * @param aString The word to look up in the hash table.
	 * @return lookup An ArrayList containing all words at the hash location.
	 */
	public ArrayList<String> lookup(String aString){
		ArrayList<Object> l = words.lookup(new ScrabbleWord(aString));
		ArrayList<String> lookup = new ArrayList<>();
		for(Object o : l) {
			ScrabbleWord sw = (ScrabbleWord) o;
			lookup.add(sw.getWord());
		}
		return lookup;
	}
	
//	public static void main(String[] args) throws FileNotFoundException {
//		File f = new File("2LetterScrabbleWords.txt");
//		Dictionary d = new Dictionary(f);
//		
//		PrintWriter p = new PrintWriter("hashtable2.txt");
//		p.println(d.words.toString());
//		p.close();
//		
//		System.out.println("Entries: " + d.entries);
//		System.out.println("Longest chain: " + d.words.longestChain());
//		System.out.println("Array size: " + d.calculateSize(d.entries));
//		System.out.println("Spaces in array: " + d.words.emptySpaces());
//		
//	}
}
