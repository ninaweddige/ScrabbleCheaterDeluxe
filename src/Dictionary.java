import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
	HashTableClass words;
	int entries;
	
	/**
	 * Constructs a Dictionary.
	 * @param aFile the file containing all words that will be added to the dictionary.
	 * @throws FileNotFoundException 
	 */
	public Dictionary(File aFile) throws FileNotFoundException {
		entries = countWords(aFile);
		words = readFile(aFile, entries);
	}
	
	/**
	 * Calculates the size of the hashtable
	 * @param the number of entries
	 * @return the size for the hash table
	 */
	public int calculateSize(int entries) {
		int size = (int) (entries * .5);
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
	
	public static void getSevenLetterWords(File aFile) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("seven letter scrabble words.txt");
		Scanner in = new Scanner(aFile);
		while(in.hasNext()) {
			String s = in.next();
			if(s.length() == 7) {
				out.println(s);
			}
		}
		out.close();
	}
	
	public void fromConsole() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter seven letters: ");
		String a = in.nextLine();
		String input = a.toUpperCase();
		ArrayList<ScrabbleWord> permutations = getValidPermutations(input);
		System.out.println("Valid words with these letters: ");
		if(permutations.isEmpty()) {
			System.out.println("No valid scrabble word with these letters.");
		}
		for(ScrabbleWord s : permutations) {
			System.out.print(s + " ");
		}
		in.close();
	}
	
	/**
	 * @author Cay Horstmann, Big Java 6 p. 575
	 */
	public ArrayList<String> getPermutations(String word){
		ArrayList<String> permutations = new ArrayList<String>();
		
		if(word.length() == 0) {
			permutations.add(word);
			return permutations;
		}
		
		for (int i = 0; i < word.length(); i++) {
			String shorterWord = word.substring(0, i) + word.substring(i + 1);
			ArrayList<String> shorterWordPermutations = getPermutations(shorterWord);
			for(String s : shorterWordPermutations) {
				permutations.add(word.charAt(i) + s);
			}
		}
		
		return permutations;
	}
	
	public ArrayList<ScrabbleWord> getValidPermutations(String word){
		ArrayList<String> permutations = getPermutations(word);
		
		ArrayList<ScrabbleWord> validPermutations = new ArrayList<>();
		
		for(String p : permutations) {
			ScrabbleWord sw = new ScrabbleWord(p);
			if(words.contains(sw) && !validPermutations.contains(sw)) {
				validPermutations.add(sw);
			}
		}
		return validPermutations;
	}


	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("seven letter scrabble words.txt");
		Dictionary test = new Dictionary(file);
			
		//System.out.println(test.words.toString());
		
		//PrintWriter out = new PrintWriter("Hashtable.txt");
		//out.println(test.words.toString());
		//out.close();
		
		//System.out.println("Entries: " + test.entries);
		//System.out.println("Longest chain: " + test.words.longestChain());
		//System.out.println("Array size: " + test.calculateSize(test.entries));
		//System.out.println("Spaces in array: " + test.words.emptySpaces());
		
		//test.fromConsole();
		
		//ArrayList<Object> lookup = test.words.lookup(new ScrabbleWord("ZILLION"));
		//System.out.println(lookup);
		
		ArrayList<ScrabbleWord> permutations = test.getValidPermutations("SPELLER");
		System.out.println(permutations);
	}
}
