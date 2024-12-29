/*  Student information for assignment:
 *
 *  On my honor, Alex Xie, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  Name: Alex Xie
 *  email address: alex.xie@utexas.edu
 *  UTEID: ayx72
 *  Section 5 digit ID: 50259
 *  Grader name: Eliza Bidwell
 *  Number of slip days used on this assignment: 0
 */

import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.lang.Comparable;
import java.util.Collections;

/**
 * Manages the details of EvilHangman. This class keeps
 * tracks of the possible words from a dictionary during
 * rounds of hangman, based on guesses so far.
 *
 */
public class HangmanManager {

    private Set<String> dictionary;
    private ArrayList<String> activeWords;
    private Set<Character> guessedLetters;
    private int guessesLeft;
    private int guessNum;
    private String pattern;
    private boolean debugOn;
    private HangmanDifficulty difficulty;

    final char WILDCARD = '-';

    /**
     * Create a new HangmanManager from the provided set of words and phrases.
     * pre: words != null, words.size() > 0
     * @param words A set with the words for this instance of Hangman.
     * @param debugOn true if we should print out debugging to System.out.
     */
    public HangmanManager(Set<String> words, boolean debugOn) {
        if (words == null || words.size() <= 0) {
            throw new IllegalArgumentException("Dictionary must have words.");
        }
        dictionary = new HashSet<>(words);
        this.debugOn = debugOn;
    }

    /**
     * Create a new HangmanManager from the provided set of words and phrases.
     * Debugging is off.
     * pre: words != null, words.size() > 0
     * @param words A set with the words for this instance of Hangman.
     */
    public HangmanManager(Set<String> words) {
        this(words, false);
    }

    /**
     * Get the number of words in this HangmanManager of the given length.
     * pre: none
     * @param length The given length to check.
     * @return the number of words in the original Dictionary
     * with the given length
     */
    public int numWords(int length) {
        int numWords = 0;
        // for-each loop necessary to traverse a set
        for (String word : dictionary) {
            if (word.length() == length) {
                numWords++;
            }
        }
        return numWords;
    }

    /**
     * Get for a new round of Hangman. Think of a round as a
     * complete game of Hangman.
     * @param wordLen the length of the word to pick this time.
     * numWords(wordLen) > 0
     * @param numGuesses the number of wrong guesses before the
     * player loses the round. numGuesses >= 1
     * @param diff The difficulty for this round.
     */
    public void prepForRound(int wordLen, int numGuesses, HangmanDifficulty diff) {
        if (numWords(wordLen) <= 0 || numGuesses < 1) {
            throw new IllegalArgumentException("Invalid argument: word length must be" + 
            " greater than 0 and number of available guesses must be greater than 0.");
        }
        // reset instance variables
        guessedLetters = new TreeSet<>();
        guessNum = 0;
        // reset pattern
        StringBuilder initial = new StringBuilder();
        for (int i = 0; i < wordLen; i++) {
            initial.append("-");
        }
        pattern = initial.toString();
        // set instance variables
        guessesLeft = numGuesses;
        difficulty = diff;
        // adds valid length words to list
        activeWords = new ArrayList<>();
        for (String word : dictionary) {
            if (word.length() == wordLen) {
                activeWords.add(word);
            }
        }
    }

    /**
     * The number of words still possible (live) based on the guesses so far.
     *  Guesses will eliminate possible words.
     * @return the number of words that are still possibilities based on the
     * original dictionary and the guesses so far.
     */
    public int numWordsCurrent() {
        return activeWords.size();
    }

    /**
     * Get the number of wrong guesses the user has left in
     * this round (game) of Hangman.
     * @return the number of wrong guesses the user has left
     * in this round (game) of Hangman.
     */
    public int getGuessesLeft() {
        return guessesLeft;
    }

    /**
     * Return a String that contains the letters the user has guessed
     * so far during this round.
     * The characters in the String are in alphabetical order.
     * The String is in the form [let1, let2, let3, ... letN].
     * For example [a, c, e, s, t, z]
     * @return a String that contains the letters the user
     * has guessed so far during this round.
     */
    public String getGuessesMade() {
        return guessedLetters.toString();
    }

    /**
     * Check the status of a character.
     * @param guess The characater to check.
     * @return true if guess has been used or guessed this round of Hangman,
     * false otherwise.
     */
    public boolean alreadyGuessed(char guess) {
        return guessedLetters.contains(guess);
    }

    /**
     * Get the current pattern. The pattern contains '-''s for
     * unrevealed (or guessed) characters and the actual character 
     * for "correctly guessed" characters.
     * @return the current pattern.
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Update the game status (pattern, wrong guesses, word list),
     * based on the give guess.
     * @param guess pre: !alreadyGuessed(ch), the current guessed character
     * @return return a tree map with the resulting patterns and the number of
     * words in each of the new patterns.
     * The return value is for testing and debugging purposes.
     */
    public TreeMap<String, Integer> makeGuess(char guess) {
        // check preconditions
        if (alreadyGuessed(guess)) {
            throw new IllegalStateException("Letter has already been guessed.");
        }
        // add guess to guessedLetters and increment guessNumber (tracked for difficulty)
        guessedLetters.add(guess);
        guessNum++;
        Map<String, ArrayList<String>> fams = sortFamilies(guess);
        // helper method finds appropriately hard family and continues game with such
        difficultyManagement(fams, guess);
        // for debugging
        TreeMap<String, Integer> result = new TreeMap<>();
        for (Map.Entry<String, ArrayList<String>> entry : fams.entrySet()) {
            result.put(entry.getKey(), entry.getValue().size());
        }
        if (debugOn) {
            System.out.println("\nDEBUGGING: Picking hardest list.");
            System.out.println("DEBUGGING: New pattern is: " + pattern + ". New family has " +
                                fams.get(pattern).size() + " words.");
        }
        return result;
    }

    /**
     * Helper method for makeGuess.
     * Sorts all active words into different patterns in a map. Each word that matches an
     * existing pattern is put into the family's list.
     * @param guess User's new guess
     * @return TreeMap with every possible family containing user's guess
     */
    private Map<String, ArrayList<String>> sortFamilies(char guess) {
        Map<String, ArrayList<String>> fams = new TreeMap<>();
        for (String word : activeWords) {
            // get general pattern of given word
            String wordPattern = sortFamily(word, guess);
            ArrayList<String> matchWords = fams.get(wordPattern);
            // if family doesn't exist, create new key
            if (matchWords == null) {
                matchWords = new ArrayList<>();
            }
            // add word to matching family
            matchWords.add(word);
            fams.put(wordPattern, matchWords);
        }
        return fams;
    }

    /**
     * Helper method for sortFamilies.
     * Returns the general pattern of a word given a guessed character.
     * @param word Word to be sorted
     * @param guess Character to be added to current pattern
     * @return new pattern containing user's guess at appropriate spots
     */
    private String sortFamily(String word, char guess) {
        StringBuilder family = new StringBuilder();
        // loop through each character in the word
        for (int i = 0; i < word.length(); i++) {
            // if contained, add
            if (guess == word.charAt(i)) {
                family.append(word.charAt(i));
            // if aligned with current pattern, add
            } else if (pattern.charAt(i) != -1) {
                family.append(pattern.charAt(i));
            // otherwise, add a dash, meaning wildcard
            } else {
                family.append(WILDCARD);
            }
        }
        return family.toString();
    }

    /**
     * Allows easy sorting of patterns/families by difficulty. This subclass
     * determines the difficulty of a pattern by first checking length, then 
     * the number of letters revealed, and lastly lexicographical order.
     */
    private class Pattern implements Comparable<Pattern> {
        String pattern;
        ArrayList<String> words;
        int revealedLetters;
        
        /**
         * Create a new Pattern object from the general pattern and
         * words that can be generalized into said pattern.
         * @param pattern General pattern or family
         * @param words Words that match above pattern
         */
        public Pattern(String pattern, ArrayList<String> words) {
            this.pattern = pattern;
            this.words = words;
            this.revealedLetters = countRevealedLetters(pattern);
        }

        /**
         * Compares patterns with each other to determine  
         * difficulty, specified by subclass description.
         * @param other Other Pattern object to be compared to
         * @return positive int if more difficult, negative int if less difficult
         */
        @Override
        public int compareTo(Pattern other) {
            // first compares size
            if (words.size() != other.words.size()) {
                return other.words.size() - words.size();
            }
            // then compares number of revealed letters
            if (revealedLetters != other.revealedLetters) {
                return revealedLetters - other.revealedLetters;
            }
            // lastly compares lexicographical order
            return pattern.compareTo(other.pattern);
        }
    }

    /**
     * Helper method for makeGuess.
     * Finds the hardest pattern and sets pattern and active words appropriately.
     * If user's guess isn't contained, decrease the number of guesses left by 1.
     * @param patterns List of all possible patterns
     * @param guess User's guess
     */
    private void difficultyManagement(Map<String, ArrayList<String>> fams, char guess) {
        // adds all patterns to an ArrayList for easy sorting
        ArrayList<Pattern> patterns = new ArrayList<>();
        for (String fam : fams.keySet()) {
            patterns.add(new Pattern(fam, fams.get(fam)));
        }
        Collections.sort(patterns);
        Pattern hardestPattern = findHardest(patterns);
        // sets instance variables
        pattern = hardestPattern.pattern;
        activeWords = hardestPattern.words;
        // if guess isn't in hardest pattern, lose a guess
        if (pattern.indexOf(guess) == -1) {
            guessesLeft--;
        }
    }

    /**
     * Helper method for makeGuess.
     * Finds "hardest" pattern in the map of potential families of active words (see below).
     * Preset EASY difficulty gets second hardest family every other guess if it exists.
     * Preset MEDIUM difficulty gets second hardest family every fourth guess if it exists.
     * Preset HARD difficulty gets hardest family every guess.
     * @param patterns List of patterns to search through
     * @return the "hardest" (based on the algorithm) Pattern object found
     */
    private Pattern findHardest(ArrayList<Pattern> patterns) {
        // default variables set to HARD
        int everyX = 1; // on every Xth iteration...
        int xHardest = 1; // return the Xth hardest pattern
        // adjust everyX and xHardest based on difficulty
        if (difficulty == HangmanDifficulty.EASY) {
            everyX = 2;
            xHardest = 2;
        } else if (difficulty == HangmanDifficulty.MEDIUM) {
            everyX = 4;
            xHardest = 2;
        }
        // if on specified iteration and xHardest pattern exists, return it
        if (guessNum % everyX == 0 && patterns.size() >= xHardest) {
            return patterns.get(xHardest - 1);
        }
        // otherwise, return hardest
        return patterns.get(0);
    }

    /**
     * Helper method for findHardest.
     * Counts the number of revealed letters in a given pattern.
     * @param pattern general pattern/family to be counted
     * @return number of non-wildcard aka revealed characters in a pattern
     */
    private int countRevealedLetters(String pattern) {
        int count = 0;
        for (char c : pattern.toCharArray()) {
            if (c != WILDCARD) {
                count++;
            }
        }
        return count;
    }

    /**
     * Return the secret word this HangmanManager finally ended up
     * picking for this round.
     * If there are multiple possible words left one is selected at random.
     * <br> pre: numWordsCurrent() > 0
     * @return return the secret word the manager picked.
     */
    public String getSecretWord() {
        // check preconditions
        if (numWordsCurrent() <= 0) {
            throw new IllegalStateException("No words left in active list");
        }
        // gets word at random index within bounds of activeWords
        return activeWords.get((int) (Math.random() * activeWords.size()));
    }
}