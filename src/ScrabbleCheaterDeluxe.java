import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ScrabbleCheaterDeluxe {
	private Dictionary[] dictionaries;
	private File file = new File("CollinsScrabbleWords2019.txt");
	
	public ScrabbleCheaterDeluxe() throws FileNotFoundException {
		dictionaries = new Dictionary[8];
		dictionaries[2] = new Dictionary(new File("2LetterScrabbleWords.txt"));
		dictionaries[3] = new Dictionary(new File("3LetterScrabbleWords.txt"));
		dictionaries[4] = new Dictionary(new File("4LetterScrabbleWords.txt"));
		dictionaries[5] = new Dictionary(new File("5LetterScrabbleWords.txt"));
		dictionaries[6] = new Dictionary(new File("6LetterScrabbleWords.txt"));
		dictionaries[7] = new Dictionary(new File("7LetterScrabbleWords.txt"));
	}
	
	/**
	 * Fills a char array with seven characters from A - Z.
	 * @return letters The char array.
	 */
	public String generateStartingLetters() {
		Random r = new Random();
		char[] letters = new char[7];
		for(int i = 0; i < 7; i++) {
			letters[i] = (char) (r.nextInt(26) + 65);
		}
		String s = String.valueOf(letters);
		return s;
	}
	
	/**
	 * Checks if two strings are permutations of one another.
	 */
	public boolean isPermutation(String a, String b) {
		ScrabbleWord aSW = new ScrabbleWord(a.toUpperCase());
		ScrabbleWord bSW = new ScrabbleWord(b.toUpperCase());
		
		if(a.length() != b.length()) return false;
		if(aSW.hashCode() != bSW.hashCode()) return false;
		
		char[] aC = a.toUpperCase().toCharArray();
		char[] bC = b.toUpperCase().toCharArray();
		Arrays.sort(aC);
		Arrays.sort(bC);
		
		for (int i = 0; i < a.length(); i++) {
			if (aC[i] != bC[i]) return false;
		}
		
		return true;
	}
	
	/**
	 * Finds all scrabble words that can be played with the given letters.
	 * @param s A string of letters.
	 * @return possibleWords An ArrayList of words that can be played.
	 */
	public ArrayList<String> getPossibleWords(String s) {
		ArrayList<String> possibleWords = new ArrayList<>();
		Substrings letters = new Substrings(s);
		
		for(int i = 2; i <= s.length(); i++) {
			Set<String> t = letters.getSubstrings(i);
			for(String word : t) {
				ArrayList<String> words = dictionaries[i].lookup(word);
				words.removeIf(e -> !(isPermutation(e, word)));
				possibleWords.addAll(words);
			}
		}
		
		return possibleWords;		
	}
	
	/**
	 * Prints the ArrayList of possible words.
	 * @param words The ArrayList containing all words that can be played.
	 * @param longestWord The length of the longest words to print.
	 */
	public void printPossibleWords(ArrayList<String> words, int longestWord) {
		if(words.isEmpty()) {
			System.out.println("No scrabble word with these letters.");
			return;
		}
		
		for(int i = longestWord; i >= 2; i--) {
			System.out.print("Scrabble words with " + i + " letters: ");
			int j = i;
			words.stream().filter(e -> e.length() == j).forEach(e -> System.out.print(e + " "));;
			System.out.println();
		}
	}
	
	/**
	 * Reads in letters from the console and outputs the words that can be played with them.
	 */
	public void fromConsole() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your letters: ");
		String a = in.nextLine();
		if(a.length() > 7) {
			System.out.println("Too many letters entered.");
			in.close();
			return;
		}
		String input = a.toUpperCase();
		
		ArrayList<String> possibleWords = getPossibleWords(input);
		printPossibleWords(possibleWords, input.length());
		in.close();
	}

	public static void main(String[] args) throws FileNotFoundException {
		ScrabbleCheaterDeluxe cheater = new ScrabbleCheaterDeluxe();
		
		String startingLetters = cheater.generateStartingLetters();
		System.out.println("Starting letters: " + startingLetters);
		cheater.printPossibleWords(cheater.getPossibleWords(startingLetters), 7);
		
		System.out.println();
		cheater.fromConsole();
	}

}
