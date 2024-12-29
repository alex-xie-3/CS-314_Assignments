/* 
 * Student information for assignment:
 *
 *  On my honor, Alex Xie, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ayx72
 *  email address: alex.xie@utexas.edu
 *  TA name: Eliza Bidwell
 *  Number of slip days I am using: 0
 */

import java.lang.StringBuilder;

public class LetterInventory {
    
    private int[] letterCount;
    private int size;

    private final int NUM_LETTERS = 26;
    private final int LOWER_HIGHER_DIFFERENCE = 32; // ascii diff between upper/lower case


    /**
     * Class tracks number of times each letter occurs in a string, 
     * disregarding other symbols and case-sensitivities.
     * @param string string != null
     */
    public LetterInventory(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String cannot be null");
        }
        String temp = string.toLowerCase(); // sets to lowercase for easy manipulation
        letterCount = new int[NUM_LETTERS];
        // loops through each character in the string
        for (int i = 0; i < string.length(); i++) {
            char c = temp.charAt(i);
            // checks if character is letter
            if ('a' <= c && c <= 'z') {
                // converts ASCII value to corresponding array index
                letterCount[(int) c - 'a']++;
                size++;
            }
        }
    }

    /**
     * Accepts a char and returns the frequency of that letter in this LetterInventory.
     * pre: char must be an English letter
     * @param c upper or lowercase letter in English dictionary
     * @return frequency of given letter in this LetterInventory.
     */
    public int get(char c) {
        // check preconditions
        if (c < 'A' || c > 'z') {
            throw new IllegalArgumentException("Character must be a letter");
        }
        // if upper case
        if (c < 'a' ) {
            // converts to lower case
            c += LOWER_HIGHER_DIFFERENCE;
        }
        // converts ASCII value to corresponding array index
        return letterCount[(int) c - 'a'];
    }

    /**
     * Returns the total number of letters in this LetterInventory.
     * @return total number of letters in this LetterInventory.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the size of this LetterInventory is 0, false otherwise.
     * @return true if the size of this LetterInventory is 0, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a String representation of this LetterInventory. All letters in the inventory 
     * are listed in alphabetical order, each for number of times appearing in original String.
     * @return String representation of this LetterInventory
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        // loops through all letters
        for (int i = 0; i < NUM_LETTERS; i++) {
            for (int j = 0; j < letterCount[i]; j++) {
                // adds character associated with index, specified number of times
                result.append(Character.toString((char) i + 'a'));
            }
        }
        return result.toString();
    }

    /**
     * Returns a new LetterInventory created by adding the frequencies from
     * the other LetterInventory object to the frequencies of this' letters.
     * @param other other != null
     * @return new LetterInventory created by adding together the frequencies from
     * this and the other LetterInventory objects
     */
    public LetterInventory add(LetterInventory other) {
        if (other == null) {
            throw new IllegalArgumentException("Other LetterInventory cannot be null.");
        }
        LetterInventory result = new LetterInventory("");
        for (int i = 0; i < NUM_LETTERS; i++) {
            // sums letter counts at each letter
            result.letterCount[i] = this.letterCount[i] + other.letterCount[i];
            result.size += result.letterCount[i];
        }

        return result;
    }

    /**
     * Returns a new LetterInventory created by subtracting the frequencies of
     * the other LetterInventory object from the frequencies of this' letters.
     * @param other other != null
     * @return new LetterInventory created by subtracting the frequencies of
     * the other LetterInventory object from the frequencies of this' letters.
     */
    public LetterInventory subtract(LetterInventory other) {
        if (other == null) {
            throw new IllegalArgumentException("Other LetterInventory cannot be null.");
        }
        LetterInventory result = new LetterInventory("");
        for (int i = 0; i < NUM_LETTERS; i++) {
            // subtracts letter counts at each letter
            int diff = this.letterCount[i] - other.letterCount[i];
            if (diff < 0) {
                return null; // early return if other inventory has more characters
            }
            result.letterCount[i] = diff;
            result.size += diff;
        }
        return new LetterInventory(result.toString());
    }

    /**
     * Returns if this LetterInventory is equivalent to the other LetterInventory.
     * @return true if frequency for each letter is the same, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        // chcek for safe cast
        if (other == null || !(other instanceof LetterInventory)) {
            return false;
        }
        LetterInventory o = (LetterInventory) other; // safe to cast now
        for (int i = 0; i < NUM_LETTERS; i++) {
            if (letterCount[i] != o.letterCount[i]) {
                return false; // early return if not equal
            }
        }
        return true;
    }

}
