import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Substrings {
	private Set<String> substrings;
	
	public Substrings(String aWord) {
		substrings = getSubstrings(aWord);
	}
	
	/**
	 * Returns a set containing all substrings of the given String.
	 */
	public Set<String> getSubstrings(String aWord){
		Set<String> substrings = new HashSet<>();
		
		substrings.add(aWord);
		
		if(aWord.length() == 2) {
			substrings.add(aWord);
			return substrings;
		}
		
		Set<String> shorterWords = new HashSet<>();
		for(int i = 0; i < aWord.length(); i++) {
			String shorterWord = aWord.substring(0, i) + aWord.substring(i + 1);
			shorterWords.add(shorterWord);
		}
		
		for(String word : shorterWords) {
			substrings.addAll(getSubstrings(word));
		}
		
		return substrings;
	}
	
	/**
	 * Returns all substrings of the given length.
	 */
	public Set<String> getSubstrings(int length) {
		Set<String> xLengthSubstrings = substrings.stream()
				                                  .filter(w -> w.length() == length)
				                                  .collect(Collectors.toSet());
		return xLengthSubstrings;
	}
	
}
