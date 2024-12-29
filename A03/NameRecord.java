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

public class NameRecord implements Comparable<NameRecord> {

    // instance variables
    private String name;
    private int base;
    private ArrayList<Integer> ranks;
    private final static int NUM_YEARS_IN_DECADE = 10;
    private final static int ZERO_VALUE = 1001;

    /**
     * Create a NameRecord object that contains a name and a list of ranks across specified
     * number of decades. Rankings are out of a 1000 and decades that don't make the top 1000
     * are indicated with 0. The base year is the first ranking in the list and preceding ranks 
     * represent succeding decades. 
     * @param line line != null
     * @param base base % 10 == 0, base >= 0
     */
    public NameRecord(String line, int base) {
        // parse the string into a string array
        String[] parsedString = line.split(" ");
        // first elem is the name
        this.name = parsedString[0];
        this.base = base;
        ranks = new ArrayList<Integer>();
        // rest are ranks
        for (int i = 1; i < parsedString.length; i++) {
            int val = Integer.parseInt(parsedString[i]);
            if (val == 0) {
                // change 0 to 1001 to make sorting easier
                ranks.add(ZERO_VALUE);
            } else {
                ranks.add(val);
            }
        }
    }

    /**
     * Gets the name of the NameRecord
     * @return name of the NameRecord
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the base year of the NameRecord
     * @return base year of the NameRecord
     */
    public int getBase() {
        return base;
    }

    /**
     * Gets the number of decades
     * @return number of decades in NameRecord
     */
    public int numDecades() {
        return ranks.size();
    }

    /**
     * Helper method to determine if a NameRecord is valid.
     * Specifically if the all decades are completeley unranked.
     * @return true if completely unranked through all decades, false otherwise
     */
    public boolean all0s() {
        for (int r : ranks) {
            if (r != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the rank of the given decade
     * @param decade decade >= 0, decade < baseYear + ranks.size() * 
     * NUM_YEARS_IN_DECADE, decade % 10 == 0
     * @return rank of specified year
     */
    public int decadeRank(int decade) {
        // finds index of decade in array
        int index = (decade - base) / NUM_YEARS_IN_DECADE;
        // convert the temporary ZERO_VALUE back to 0
        if (ranks.get(index) == ZERO_VALUE) {
            return 0;
        }
        return ranks.get(index);
    }

    /**
     * Gets the best ranking decade i.e. smallest rank that is != 0 within list
     * @return smallest decade in the ArrayList of ranks
     */
    public int bestDecade() {
        // index of bestDecade
        int index = 0;
        // worst possible rank is 0
        int MIN = ZERO_VALUE;
        for (int i = 0; i < ranks.size(); i++) {
            int rank = ranks.get(i);
            if (rank <= MIN) {
                index = i;
                MIN = rank;
            }
        }
        // converts index to year
        return base + index * NUM_YEARS_IN_DECADE;
    }

    /**
     * Returns the number of decades a name has broken the 
     * top 1000 in the list of recorded decades
     * @return number of decades within recorded list with a rank >= 1000
     */
    public int numDecadesTop1000() {
        int numDecades = 0;
        for (int i = 0; i < ranks.size(); i++) {
            // top 1000 means a rank of less than/equal to 1000
            if (ranks.get(i) <= 1000) {
                numDecades++;
            }
        }
        return numDecades;
    }

    /**
     * Checks if every decade in the ranks list has surpassed at least 1000
     * @return true if every decade surpasses 1000 in rank, false otherwise
     */
    public boolean top1000EveryDecade() {
        return numDecadesTop1000() == ranks.size();
    }

    /**
     * Checks if exactly one decade in the ranks has surpassed at least 1000
     * @return true if exactly one decade surpasses 1000 in rank, false otherwise
     */
    public boolean top1000OneDecade() {
        return numDecadesTop1000() == 1;
    }

    /**
     * Checks if the ranks in each decade are strictly improving i.e. getting smaller
     * @return true if ranks is strictly decreasing, false otherwise
     */
    public boolean isImproving() {
        for (int i = 1; i < ranks.size(); i++) {
            // checks for declining/equal trends
            if (ranks.get(i) >= ranks.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the ranks in each decade are strictly declining i.e. getting larger
     * @return true if ranks is strictly increasing, false otherwise
     */
    public boolean isDeclining() {
        for (int i = 1; i < ranks.size(); i++) {
            // checks for improving/equal trends
            if (ranks.get(i) <= ranks.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns name of NameRecord and decades with their
     * respective ranks in a nice, formatted list.
     * @return name and rankings of NameRecord in a list
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        // prints name
        str.append(name + "\n");
        // prints all ranks
        for (int i = 0; i < ranks.size(); i++) {
            str.append(base + i * NUM_YEARS_IN_DECADE + ": ");
            // converts the temporary ZERO_VALUE back to 0
            if (ranks.get(i) == ZERO_VALUE) {
                str.append(0);
            } else {
                str.append(ranks.get(i));
            }
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * Returns the lexicographical differences between the current and given NameRecord
     * @param other other != null
     * @return difference between current NameRecord and given NameRecord
     */
    public int compareTo(NameRecord other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
