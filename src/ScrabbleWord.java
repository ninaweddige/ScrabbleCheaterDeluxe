
public class ScrabbleWord {
	private String word;
	private int length;
	
	/**
	 * Constructs a ScrabbleWord.
	 * @param aWord the word
	 */
	public ScrabbleWord(String aWord) {
		word = aWord;
		length = word.length();
	}
	
	public String getWord() {
		return word;
	}
	
	/**
	 * Computes the hash code of the ScrabbleWord.
	 */
	public int hashCode() {
		//final int HASH_MULTIPLIER = 43;
		final int HASH_MULTIPLIER = 7;
		int hash = 0;
		for(int i = 0; i < word.length(); i++) {
			//hash += HASH_MULTIPLIER * word.charAt(i); //for permutations to end up in the same bucket
			hash += Math.pow(HASH_MULTIPLIER, word.length() - 1 - i) * word.charAt(i);
		}
		return hash;
	}
	
	public boolean equals(Object a) {
		if(a == null) return false;
		
		if(a.getClass() != this.getClass()) {
			return false;
		}
		
		ScrabbleWord a1 = (ScrabbleWord)a;
		
		if(a1.hashCode() != this.hashCode()) {
			return false;
		}

		if(!a1.word.equals(this.word)|| a1.length != this.length) {
			return false;
		}
		
		return true;
		
	}
	
	public String toString() {
		return word;
	}
}
