/*  Student information for assignment:
 *
 *  On our honor, Andrew and Alex,
 *  this programming assignment is our own work
 *  and we have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: adn2225
 *  email address: adn2225@my.utexas.edu
 *  Grader name: Eliza Bidwell
 *  Section number: 50259
 *
 *  Student 2
 *  UTEID: ayx72
 *  email address: alex.xie@utexas.edu
 *
 */


//imports

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Various recursive methods to be implemented.
 * Given shell file for CS314 assignment.
 */
public class Recursive {

    /**
     * Problem 1: convert a base 10 int to binary recursively.
     * <br>pre: n != Integer.MIN_VALUE<br>
     * <br>post: Returns a String that represents N in binary.
     * All chars in returned String are '1's or '0's.
     * Most significant digit is at position 0
     *
     * @param n the base 10 int to convert to base 2
     * @return a String that is a binary representation of the parameter n
     */
    public static String getBinary(int n) {
        // check precondition
        if (n == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "getBinary. n cannot equal "
                    + "Integer.MIN_VALUE. n: " + n);
        }

        // BASE_CASE: n is 1 (average case final recursion ALWAYS passes 1)
        // or 0 (edge case)
        if (n == 1 || n == 0) {
            return Integer.toString(n);
        }

        // client passed a negative number
        // this is only true before any recursions
        if (n < 0) {
            return "-" + getBinary(-1 * n);
        }
        return getBinary(n / 2) + n % 2;
        // RECURSE: (n / 2) to get closer to (n == 1)
        // HIT BASE CASE: append the remainder to the current binary sequence
    }


    /**
     * Problem 2: reverse a String recursively.<br>
     *   pre: stringToRev != null<br>
     *   post: returns a String that is the reverse of stringToRev
     *   @param stringToRev the String to reverse.
     *   @return a String with the characters in stringToRev
     *   in reverse order.
     */
    public static String revString(String stringToRev) {
        // check precondition
        if (stringToRev == null) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "revString. parameter may not be null.");
        }

        // BASE CASE: stringToRev is ""
        if (stringToRev.isEmpty()) {
            return stringToRev;
        }

        return revString(stringToRev.substring(1)) + stringToRev.charAt(0);
        // RECURSE: stringToRev without its first character in order to reach empty state
        // HIT BASE CASE: append that first character to the current reversed String
    }


    /**
     * Problem 3: Returns the number of elements in data
     * that are followed directly by value that is
     * double that element.
     * pre: data != null
     * post: return the number of elements in data
     * that are followed immediately by double the value
     * @param data The array to search.
     * @return The number of elements in data that
     * are followed immediately by a value that is double the element.
     */
    public static int nextIsDouble(int[] data) {
        // check precondition
        if (data == null) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "revString. parameter may not be null.");
        }

        return nextDoubleHelper(data, 0);
    }

    // actually does the recursion; essentially functions like a for loop through an array
    // i denotes the index of the int to check
    private static int nextDoubleHelper(int[] data, int i) {
        // BASE CASE: i points to the final int of data
        // or data is empty (edge case)
        if (i == data.length - 1 || i == data.length) {
            return 0;
        }

        // RECURSE: the next index
        // HIT BASE CASE: if the next int is double, add one to the current count
        // otherwise just send the current count as is to the above stack frame
        if (data[i] * 2 == data[i + 1]) {
            return nextDoubleHelper(data, ++i) + 1;
        } else {
            return nextDoubleHelper(data, ++i);
        }
    }

    /**  Problem 4: Find all combinations of mnemonics
     * for the given number.<br>
     *   pre: number != null, number.length() > 0,
     *   all characters in number are digits<br>
     *   post: see tips section of assignment handout
     *   @param number The number to find mnemonics for
     *   @return An ArrayList<String> with all possible mnemonics
     *   for the given number
     */
    public static ArrayList<String> listMnemonics(String number) {
        // check precondition
        if (number == null ||  number.length() == 0 || !allDigits(number)) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "listMnemonics");
        }

        ArrayList<String> results = new ArrayList<>(); // to store the mnemonics
        recursiveMnemonics(results, "", number);
        return results;
    }


    /*
     * Helper method for listMnemonics
     * mnemonics stores completed mnemonics
     * mneominicSoFar is a partial (possibly complete) mnemonic
     * digitsLeft are the digits that have not been used
     * from the original number.
     */
    private static void recursiveMnemonics(ArrayList<String> mnemonics,
                    String mnemonicSoFar, String digitsLeft) {
        // BASE CASE: there are no more digits to process
        if (digitsLeft.isEmpty()) {
            mnemonics.add(mnemonicSoFar); // duplicates can arise when consecutive numbers are same
        // RECURSIVE BACKTRACKING
        // HIT BASE CASE: mnemonicSoFar is complete
        } else {
            // CHOOSE: a corresponding letter of the first digit in digitsLeft
            String letters = digitLetters(digitsLeft.charAt(0));
            digitsLeft = digitsLeft.substring(1);
            for (int i = 0; i < letters.length(); i++) {
                // EXPLORE: remove the first digit from digitsLeft, add this letter to mnemonicSoFar
                recursiveMnemonics(mnemonics, mnemonicSoFar + letters.charAt(i), digitsLeft);
                // UNCHOOSE: via loop, go to the next letter of the (previously) first digit
            }
        }
    }

    /* Static code blocks are run once when this class is loaded. 
     * Here we create an unmoddifiable list to use with the phone 
     * mnemonics method.
     */
    private static final List<String> LETTERS_FOR_NUMBER;
    static {
        String[] letters = {"0", "1", "ABC",
                "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};
        ArrayList<String> lettersAsList = new ArrayList<>();
        for (String s : letters) {
            lettersAsList.add(s);
        }
        LETTERS_FOR_NUMBER = Collections.unmodifiableList(lettersAsList);

    }
    // used by method digitLetters
     


    /* helper method for recursiveMnemonics
     * pre: ch is a digit '0' through '9'
     * post: return the characters associated with
     * this digit on a phone keypad
     */
    private static String digitLetters(char ch) {
        if (ch < '0' || ch > '9') {
            throw new IllegalArgumentException("parameter "
                    + "ch must be a digit, 0 to 9. Given value = " + ch);
        }
        int index = ch - '0';
        return LETTERS_FOR_NUMBER.get(index);
    }


    /* helper method for listMnemonics
     * pre: s != null
     * post: return true if every character in s is
     * a digit ('0' through '9')
     */
    private static boolean allDigits(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "allDigits. String s cannot be null.");
        }
        boolean allDigits = true;
        int i = 0;
        while (i < s.length() && allDigits) {
            allDigits = s.charAt(i) >= '0' && s.charAt(i) <= '9';
            i++;
        }
        return allDigits;
    }


    /**
     * Problem 5: Draw a Sierpinski Carpet.
     * @param size the size in pixels of the window
     * @param limit the smallest size of a square in the carpet.
     */
    public static void drawCarpet(int size, int limit) {
        DrawingPanel p = new DrawingPanel(size, size);
        Graphics g = p.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,size,size);
        g.setColor(Color.WHITE);
        drawSquares(g, size, limit, 0, 0);
    }


    /* Helper method for drawCarpet
     * Draw the individual squares of the carpet.
     * @param g The Graphics object to use to fill rectangles
     * @param size the size of the current square
     * @param limit the smallest allowable size of squares
     * @param x the x coordinate of the upper left corner of the current square
     * @param y the y coordinate of the upper left corner of the current square
     */
    private static void drawSquares(Graphics g, int size, int limit,
            double x, double y) {
        // IMPORTANT: x, y DO NOT refer to the square cut out
        // they refer to the section THAT a square is cut out of
        final int SECTIONS = 3; // number of sections horizontally or vertically
        size /= SECTIONS;
        // BASE CASE: size of square cut out would be smaller than limit
        if (size >= limit) {
            // RECURSIVE BACKTRACKING
            // CHOOSE: the coordinates within the section
            for (int i = 0; i < SECTIONS; i++) {
                for (int j = 0; j < SECTIONS; j++) {
                    // EXPLORE: recurse into subsection of size at the coordinates
                    // HIT BASE CASE: do nothing
                    if (i != 1 || j != 1) {
                        drawSquares(g, size, limit, x + j * size, y + i * size);
                        // focuses on subsections by taking size-wide steps
                        /*
                         * if base case is hit once, it will be hit seven more times in a row
                         * due to the nature of recursive backtracking
                         *
                         * then the loop is finished, it cuts out a square,
                         * the stack frame is deleted, CHOOSE, EXPLORE, repeat
                         */
                        // we spent an hour debugging because we accidentally did (x + i * size)
                    }
                    // UNCHOOSE: move to the next subsection
                }
            }
            g.fillRect((int) x + size, (int) y + size, size, size);
        }
    }


    /**
     * Problem 6: Determine if water at a given point
     * on a map can flow off the map.
     * <br>pre: map != null, map.length > 0,
     * map is a rectangular matrix, 0 <= row < map.length,
     * 0 <= col < map[0].length
     * <br>post: return true if a drop of water starting at the location
     * specified by row, column can reach the edge of the map,
     * false otherwise.
     * @param map The elevations of a section of a map.
     * @param row The starting row of a drop of water.
     * @param col The starting column of a drop of water.
     * @return return true if a drop of water starting at the location
     * specified by row, column can reach the edge of the map, false otherwise.
     */
    public static boolean canFlowOffMap(int[][] map, int row, int col) {
        // check preconditions
        if (map == null || map.length == 0 || !isRectangular(map)
                || !inbounds(row, col, map)) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "canFlowOffMap");
        }

        // BASE CASE: the cell is on the edge
        if (row == 0 || row == map.length - 1 || col == 0 || col == map[0].length - 1) {
            return true;
        }

        // RECURSIVE BACKTRACKING
        // CHOOSE: valid direction to move
        for (int d = -1; d <= 1; d += 2) {
            // EXPLORE: go that direction
            if (map[row + d][col] < map[row][col] && canFlowOffMap(map, row + d, col)) {
                // HIT BASE CASE: return true
                return true;
                // returning the recursion can lead to incorrect results
                // because the other directions are then failed to be checked

            }
            // UNCHOOSE: check next direction
            if (map[row][col + d] < map[row][col] && canFlowOffMap(map, row, col + d)) {
                return true;
            }
        }
        return false;
    }

    /* helper method for canFlowOfMap - CS314 students you should not have to
     * call this method,
     * pre: mat != null,
     */
    private static boolean inbounds(int r, int c, int[][] mat) {
        if (mat == null) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "inbounds. The 2d array mat may not be null.");
        }
        return r >= 0 && r < mat.length && mat[r] != null
                && c >= 0 && c < mat[r].length;
    }

    /*
     * helper method for canFlowOfMap - CS314 students you should not have to
     * call this method,
     * pre: mat != null, mat.length > 0
     * post: return true if mat is rectangular
     */
    private static boolean isRectangular(int[][] mat) {
        if (mat == null || mat.length == 0) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "inbounds. The 2d array mat may not be null "
                    + "and must have at least 1 row.");
        }
        boolean correct = true;
        final int numCols = mat[0].length;
        int row = 0;
        while (correct && row < mat.length) {
            correct = (mat[row] != null) && (mat[row].length == numCols);
            row++;
        }
        return correct;
    }


    /**
     * Problem 7: Find the minimum difference possible between teams
     * based on ability scores. The number of teams may be greater than 2.
     * The goal is to minimize the difference between the team with the
     * maximum total ability and the team with the minimum total ability.
     * <br> pre: numTeams >= 2, abilities != null, abilities.length >= numTeams
     * <br> post: return the minimum possible difference between the team
     * with the maximum total ability and the team with the minimum total
     * ability.
     * @param numTeams the number of teams to form.
     * Every team must have at least one member
     * @param abilities the ability scores of the people to distribute
     * @return return the minimum possible difference between the team
     * with the maximum total ability and the team with the minimum total
     * ability. The return value will be greater than or equal to 0.
     */
    public static int minDifference(int numTeams, int[] abilities) {
        // check preconditions
        if (numTeams < 2) {
            throw new IllegalArgumentException("Argument numTeams may not be less than 2.");
        } else if (abilities == null) {
            throw new IllegalArgumentException("Argument abilities may not be null.");
        } else if (abilities.length < numTeams) {
            throw new IllegalArgumentException("Argument abilities may not be shorter than" +
                    " argument numTeams.");
        }

        return minDiffHelper(abilities, 0, new int[2][numTeams]);
    }

    private static int minDiffHelper(int[] abilities, int pplIndex, int[][] teams) {
        // teams[0] is sums ; teams[1] is number of people
        // BASE CASE: there are no more people to put into teams
        if (pplIndex == abilities.length) {
            if (searchArray(teams[1], 0)) {
                return Integer.MAX_VALUE;
            }
            return diffMaxMin(teams[0]);
        }

        int min = Integer.MAX_VALUE;
        // RECURSIVE BACKTRACKING
        // CHOOSE: the team the current person will go to
        for (int i = 0; i < teams[0].length; i++) {
            teams[0][i] += abilities[pplIndex];
            teams[1][i]++;
            // EXPLORE: point to the next person
            /*
             * HIT BASE CASE: if an invalid team exists, get an unreasonable difference
             * otherwise, get the difference between the highest and lowest teams
             * compare this value with the lowest difference from this and newer stack frames
             */
            int diff = minDiffHelper(abilities, pplIndex + 1, teams);
            // lowest possible difference
            if (diff == 0) {
                return diff;
            } else if (diff < min) {
                min = diff;
            }

            // UNCHOOSE: decrement the number of people in the team and remove the person
            teams[1][i]--;
            teams[0][i] -= abilities[pplIndex];
        }
        return min;
    }

    // searches in an int array for query
    // returns true if it exists, false otherwise
    private static boolean searchArray(int[] arr, int query) {
        for (int i : arr) {
            if (i == query) {
                return true;
            }
        }
        return false;
    }

    // finds the difference between the highest and lowest value in an int array
    // there can be an issue regarding overflow
    private static int diffMaxMin(int[] arr) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i : arr) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
        }
        return max - min;
    }


    /**
     * Problem 8: Maze solver.
     * <br>pre: board != null
     * <br>pre: board is a rectangular matrix
     * <br>pre: board only contains characters 'S', 'E', '$', 'G', 'Y', and '*'
     * <br>pre: there is a single 'S' character present
     * <br>post: rawMaze is not altered as a result of this method.
     * Return 2 if it is possible to escape the maze after
     * collecting all the coins.
     * Return 1 if it is possible to escape the maze
     * but without collecting all the coins.
     * Return 0 if it is not possible
     * to escape the maze. More details in the assignment handout.
     * @param rawMaze represents the maze we want to escape.
     * rawMaze is not altered as a result of this method.
     * @return per the post condition
     */
    public static int canEscapeMaze(char[][] rawMaze) {
        // check preconditions
        if (rawMaze == null) {
            throw new IllegalArgumentException("Argument rawMaze may not be null.");
        } else if (!isRectangular(rawMaze)) {
            throw new IllegalArgumentException("Argument rawMaze must be rectangular.");
        } else if (!isValidMaze(rawMaze)) {
            throw new IllegalArgumentException("Argument rawMaze is invalid.");
        }

        int[] scan = scanMaze(rawMaze);
        char[][] copy = new char[rawMaze.length][];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = Arrays.copyOf(rawMaze[i], rawMaze[0].length);
        }
        return mazeHelper(rawMaze, copy, scan[0], scan[1], scan[2]);
    }

    // PRECONDITION: check if the 2D array is rectangular
    private static boolean isRectangular(char[][] mat) {
        if (mat == null || mat.length == 0) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "inbounds. The 2d array mat may not be null "
                    + "and must have at least 1 row.");
        }
        boolean correct = true;
        final int numCols = mat[0].length;
        int row = 0;
        while (correct && row < mat.length) {
            correct = (mat[row] != null) && (mat[row].length == numCols);
            row++;
        }
        return correct;
    }

    // PRECONDITION: check if the maze is valid
    // (only contains characters 'S', 'E', '$', 'G', 'Y', and '*' and only has one 'S')
    private static boolean isValidMaze(char[][] rawMaze) {
        int numS = 0;
        final String VALID_CHARS = "SE$GY*";
        for (int i = 0; i < rawMaze.length; i++) {
            for (int j = 0; j < rawMaze[0].length; j++) {
                if (rawMaze[i][j] == 'S') {
                    numS++;
                }
                if (VALID_CHARS.indexOf(rawMaze[i][j]) == -1) {
                    return false;
                }
            }
        }
        return numS == 1;
    }

    /*
     * returns an int array that consists as follows
     * int[0] = y coord of 'S'
     * int[1] = x coord of 'S'
     * int[2] = number of coins
     */
    private static int[] scanMaze(char[][] rawMaze) {
        final int SIZE = 3;
        int[] result = new int[SIZE];
        for (int i = 0; i < rawMaze.length; i++) {
            for (int j = 0; j < rawMaze[0].length; j++) {
                if (rawMaze[i][j] == '$') {
                    result[2]++;
                } else if (rawMaze[i][j] == 'S') {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    // the actual method ; recursion happens here
    private static int mazeHelper(char[][] rawMaze, char[][] maze, int y, int x, int coinsLeft) {
        // BASE CASE: position is invalid
        if (y < 0 || y == maze.length || x < 0 || x == maze[0].length || maze[y][x] == '*') {
            return 0;
        }
        // BASE CASE: position is at an exit
        // and there or there is not any remaining coins
        if (rawMaze[y][x] == 'E') {
            if (coinsLeft == 0) {
                return 2;
            }
            return 1;
        }

        int highest = 0;
        coinsLeft += changeCell(maze, y, x); // change cell before b/c it's role is complete
        // RECURSIVE BACKTRACKING
        // CHOOSE: a direction to move in
        for (int d = -1; d <= 1; d += 2) {
            // EXPLORE: go in that direction
            int temp = mazeHelper(rawMaze, maze, y + d, x, coinsLeft);
            // HIT BASE CASE: record the result of the path and check if its the best so far
            if (temp > highest) {
                highest = temp;
            }
            // UNCHOOSE: go the next direction
            temp = mazeHelper(rawMaze, maze, y, x + d, coinsLeft);
            // 2 is the best result
            if (temp == 2) {
                return temp;
            } else if (temp > highest) {
                highest = temp;
            }
        }
        coinsLeft += unchooseCell(rawMaze, maze, y, x); // when backtracking, revert cell
        return highest;
    }

    /*
     * change the current cell down a status
     * (e.g., 'G' -> 'Y')
     *
     * returns the number to "increment" coinsLeft by
     * -1 if changing a '$', and 0 otherwise
     */
    private static int changeCell(char[][] maze, int y, int x) {
        switch (maze[y][x]) {
            case 'Y':
                maze[y][x] = '*';
                break;
            case 'G':
                maze[y][x] = 'Y';
                break;
            case '$':
                maze[y][x] = 'Y';
                return -1;
            case 'S':
                maze[y][x] = 'G';
                break;
        }
        return 0;
    }

    /*
     * revert the current cell up a status
     * (e.g., 'G' -> 'S')
     *
     * returns the number to increment coinsLeft by
     * 1 if the cell was previously a '$', and 0 otherwise
     */
    private static int unchooseCell(char[][] rawMaze, char[][] maze, int y, int x) {
        switch (maze[y][x]) {
            case '*':
                maze[y][x] = 'Y';
                break;
            case 'Y':
                if (rawMaze[y][x] == '$') {
                    maze[y][x] = '$';
                    return 1;
                }
                maze[y][x] = 'G'; // covers both 'G' and 'S'
                break;
            // the only case where one lower status is 'G' is when the cell was 'S'
            case 'G':
                maze[y][x] = 'S';
                break;
        }
        return 0;
    }
}