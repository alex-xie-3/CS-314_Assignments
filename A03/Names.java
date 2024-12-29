/*  Student information for assignment:
*
*  On my honor, Alex Xie, this programming assignment is my own work
*  and I have not provided this code to any other student.
*
*  UTEID: ayx72
*  email address: alex.xie@utexas.edu
*  Number of slip days I am using: 1
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
// for my interesting method
import java.util.TreeMap;

/**
* A collection of NameRecords.
* Stores NameRecord objects and provides methods to select
* NameRecords based on various criteria.
*/
public class Names {

    // instance variables
    private ArrayList<NameRecord> nameRecords;

    /**
     * Construct a new Names object based on the data source the Scanner
     * sc is connected to. Assume the first two lines in the
     * data source are the base year and number of decades to use.
     * Any lines without the correct number of decades are discarded
     * and are not part of the resulting Names object.
     * Any names with ranks of all 0 are discarded and not
     * part of the resulting Names object.
     * @param sc Is connected to a data file with baby names
     * and positioned at the start of the data source.
     */
    public Names(Scanner sc) {
        nameRecords = new ArrayList<NameRecord>();
        // line 1 = base year
        int base = Integer.parseInt(sc.nextLine());
        // line 2 = number of decades in each dataset
        int numDecades = Integer.parseInt(sc.nextLine());
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            // to count number of values
            String[] parts = line.split(" ");
            NameRecord nr = new NameRecord(line, base);
            // check if correct num of decades and if it contains all 0s
            if (parts.length - 1 == numDecades && !nr.all0s()) {
                nameRecords.add(nr);
            }
        }
        // sort by alphabetical order
        Collections.sort(nameRecords);
    }

    /**
    * Returns an ArrayList of NameRecord objects that contain a
    * given substring, ignoring case.  The names must be in sorted order based
    * on the names of the NameRecords.
    * @param partialName != null, partialName.length() > 0
    * @return an ArrayList of NameRecords whose names contains
    * partialName. If there are no NameRecords that meet this
    * criteria returns an empty list.
    */
    public ArrayList<NameRecord> getMatches(String partialName) {
        ArrayList<NameRecord> matchingNameRecords = new ArrayList<NameRecord>();
        for (NameRecord nameRecord : nameRecords) {
            // check if name contains partial name (case-insensitive)
            if (nameRecord.getName().toLowerCase().contains(partialName.toLowerCase())) {
                matchingNameRecords.add(nameRecord);
            }
        }
        return matchingNameRecords;
    }

    /**
    * Returns an ArrayList of Strings of names that have been ranked in the
    * top 1000 or better for every decade. The Strings  must be in sorted
    * order based on the name of the NameRecords.
    * @return A list of the names that have been ranked in the top
    * 1000 or better in every decade. The list is in sorted ascending
    * order. If there are no NameRecords that meet this
    * criteria returns an empty list.
    */
    public ArrayList<String> rankedEveryDecade() {
        ArrayList<String> rankedEveryDecade = new ArrayList<String>();
        for (NameRecord nameRecord : nameRecords) {
            if (nameRecord.top1000EveryDecade()) {
                rankedEveryDecade.add(nameRecord.getName());
            }
        }
        return rankedEveryDecade;
    }

    /**
    * Returns an ArrayList of Strings of names that have been ranked in the
    * top 1000 or better in exactly one decade. The Strings must be in sorted
    * order based on the name of the NameRecords.
    * @return A list of the names that have been ranked in the top
    * 1000 or better in exactly one decade. The list is in sorted ascending
    * order. If there are no NameRecords that meet this
    * criteria returns an empty list.
    */
    public ArrayList<String> rankedOnlyOneDecade() {
        ArrayList<String> rankedOnlyOneDecade = new ArrayList<String>();
        for (NameRecord nameRecord : nameRecords) {
            if (nameRecord.top1000OneDecade()) {
                rankedOnlyOneDecade.add(nameRecord.getName());
            }
        }
        return rankedOnlyOneDecade;
    }

    /**
    * Returns an ArrayList of Strings of names that have been getting more
    * popular every decade. The Strings  must be in sorted
    * order based on the name of the NameRecords.
    * @return A list of the names that have been getting more popular in
    * every decade. The list is in sorted ascending
    * order. If there are no NameRecords that meet this
    * criteria returns an empty list.
    */
    public ArrayList<String> alwaysMorePopular() {
        ArrayList<String> alwaysMorePopular = new ArrayList<String>();
        for (NameRecord nameRecord : nameRecords) {
            if (nameRecord.isImproving()) {
                alwaysMorePopular.add(nameRecord.getName());
            }
        }
        return alwaysMorePopular;
    }

    /**
    * Returns an ArrayList of Strings of names that have been getting less
    * popular every decade. The Strings  must be in sorted order based
    * on the name of the NameRecords.
    * @return A list of the names that have been getting less popular in
    * every decade. The list is in sorted ascending
    * order. If there are no NameRecords that meet this
    * criteria returns an empty list.
    */
    public ArrayList<String> alwaysLessPopular() {
        ArrayList<String> alwaysLessPopular = new ArrayList<String>();
        for (NameRecord nameRecord : nameRecords) {
            if (nameRecord.isDeclining()) {
                alwaysLessPopular.add(nameRecord.getName());
            }
        }
        return alwaysLessPopular;
    }

    /**
     * Finds all derivatives in a dataset of names. The completely arbritary definition
     * for a derivative is a 4-7 character name that acts as the "base" for other names.
     * For example, Alex would be a derivative of Alexa as well as Alexander.
     * @return A TreeMap mapping names to the number of times they occur for all names
     * matching derivative criteria
     */
    public TreeMap<String, Integer> derivatives() {
        // creates tree map mapping derivatives to number of times it appears
        TreeMap<String, Integer> topDerivs = new TreeMap<String, Integer>();
        for (int i = 0; i < nameRecords.size() - 1; i++) {
            String name = nameRecords.get(i).getName();
            // arbritary length bounds and check for not being a form of a preexisting derivative
            if (name.length() >= 4 && name.length() <= 7 && !alreadyDeriv(name, topDerivs)) {
                topDerivs.put(name, 1);
                int j = i + 1;
                // should be case sensitive in order to find derivative from front
                String test = nameRecords.get(j).getName();
                // loops through remainder of list while derivative is applicable
                while (test.contains(name)) {
                    test = nameRecords.get(j).getName();
                    topDerivs.put(name, topDerivs.get(name) + 1);
                    j++;
                }
            }
        }
        return topDerivs;
    }

    /**
     * Helper method to check whether a potential derivative has already been included
     * within the derivs TreeSet. For example, Christ matches the rules for being a 
     * derivative but Chris futher simplifies that name. We don't count Christ to avoid 
     * uncessary double counting.
     * @param name to be checked for simplest form
     * @param derivs current list of derivatives to check through
     * @return true if a derivative of the checked one already exists, false otheriwise
     */
    private boolean alreadyDeriv(String name, TreeMap<String, Integer> derivs) {
        for (String n : derivs.keySet()) {
            if (name.contains(n)) {
                return true;
            }
        }
        return false;
    }

    /**
    * Return the NameRecord in this Names object that matches the given String ignoring case.
    * <br>
    * <tt>pre: name != null</tt>
    * @param name The name to search for.
    * @return The name record with the given name or null if no NameRecord in this Names
    * object contains the given name.
    */
    public NameRecord getName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("The parameter name cannot be null");
        }
        // implementing binary search algorithm
        int low = 0;
        int high = nameRecords.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int check = name.compareToIgnoreCase(nameRecords.get(mid).getName());
            if (check == 0) {
                return nameRecords.get(mid);
            } else if (check > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }
}