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

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.Collections;

public class AnagramSolver {

    private Map<String, LetterInventory> inventories;
    /*
    * pre: list != null
    * @param list Contains the words to form anagrams from.
    */
    public AnagramSolver(Set<String> dictionary) {
        // check preconditions
        if (dictionary == null) {
            throw new IllegalArgumentException("Dictionary cannot be null.");
        }
        this.inventories = new TreeMap<>();
        // fills dictionary with LetterInventories of each word
        for (String word : dictionary) {
            inventories.put(word, new LetterInventory(word));
        }
    }

    /*
    * pre: maxWords >= 0, s != null, s contains at least one 
    * English letter.
    * Return a list of anagrams that can be formed from s with
    * no more than maxWords, unless maxWords is 0 in which case
    * there is no limit on the number of words in the anagram
    */
    public List<List<String>> getAnagrams(String s, int maxWords) {
        // check preconditions
        if (s == null || maxWords < 0) {
            throw new IllegalArgumentException("Invalid parameters.");
        }
        // LetterInventory handles one English letter condition
        LetterInventory targetWord = new LetterInventory(s);
        if (targetWord.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameters.");
        }
        List<String> smallDict = new ArrayList<String>();
        // loops through all word's LetterInventories to find possible anagram candidates
        for (String word : inventories.keySet()) {
            LetterInventory checkWord = inventories.get(word);
            // if checked word's characters are <=  target word
            if (targetWord.subtract(checkWord) != null) {
                smallDict.add(word); // add to small dictionary
            }
        }
        List<List<String>> anagrams = new ArrayList<>();
        List<String> anagram = new ArrayList<>();
        // unlimited number of words is just the number of characters in the word
        if (maxWords == 0) {
            maxWords = s.length();
        }
        // run helper to modify the reference to anagrams
        getAnagramsHelper(anagrams, maxWords, targetWord, anagram, smallDict, 0);
        // sort anagrams with private class AnagramComparator
        Collections.sort(anagrams, new AnagramComparator());
        return anagrams;
    }

    /**
     * Private helper method for getAnagrams. Recursively goes through all possibly anagrams
     * and modifies the empty list of anagram lists.
     */
    private void getAnagramsHelper(List<List<String>> anagrams, int maxWords, 
                                    LetterInventory anagramSoFar, List<String> anagram, 
                                        List<String> smallDict, int index) {
        // only continue adding to anagram if less than maxWords
        if (anagram.size() <= maxWords) {
            // if used all characters in original string, add to list
            if (anagramSoFar.isEmpty()) {
                // gets deep copy of anagram
                ArrayList<String> toAdd = new ArrayList<String>();
                for (String str : anagram) {
                    toAdd.add(str);
                }
                anagrams.add(toAdd);
            } else if (anagram.size() != maxWords) {
                // loops through valid words starting from last working word's index
                for (int i = index; i < smallDict.size(); i++) {
                    String word = smallDict.get(i);
                    // subtract letter inventories to see if possible
                    LetterInventory diff = anagramSoFar.subtract(inventories.get(word));
                    if (diff != null) { // if valid
                        anagram.add(word); // add word and check with recursive function
                        getAnagramsHelper(anagrams, maxWords, diff, anagram, smallDict, i);
                        anagram.remove(word); // backtrack: remove word if added or doesn't work
                    }
                }
            }
        }
    }

    /**
     * Private class that implements Comparator in order to compare Lists(anagrams).
     */
    private static class AnagramComparator implements Comparator<List<String>> {

        /**
         * Defines how to compare two lists. Shorter lists have higher precedence.
         * Afterwards, sort by lexigraphical order.
         */
        public int compare(List<String> a1, List<String> a2) {
            // compares length of lists
            if (a1.size() != a2.size()) {
                return a1.size() - a2.size();
            }
            for (int i = 0; i < a1.size(); i++) {
                if (!a1.get(i).equals(a2.get(i))) {
                    // immediately returns if finds a difference
                    return a1.get(i).compareTo(a2.get(i));
                }
            }
            // otherwise the same (shouldn't be possible with implementation)
            return 0;
        }
    }
}