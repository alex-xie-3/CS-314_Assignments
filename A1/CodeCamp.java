//  CodeCamp.java - CS314 Assignment 1

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

 public class CodeCamp {

    // Birthday Experiment Methods
    /** 
     * Finds average number of pairs after running a random birthday generator for
     * numPeople people numTests times
     * pre: numPeople >= 2, numTests >= 0, 
     * numDaysInYear = 365 (given by instruction)
     * post: returns average number of birthday pairs 
     * for numPeople people after running numTests tests
     * @param numPeople number of people per test
     * @param numTests number of tests run with numPeople
     * @return average number of birthday pairs for 
     * numPeople people after running numTests tests
     */
    public static long birthdayAverageTest(int numPeople, int numTests) {
        long totalPairs = 0;
        for (int i = 0; i < numTests; i++) {
            int pairs = CodeCamp.sharedBirthdays(numPeople, 365);
            totalPairs += pairs;
        }
        return totalPairs / numTests;
    }

    /** 
     * Finds percentage of tests with at least one pair after running
     * a random birthday generator for numPeople people numTests times
     * numPeople people numTests times
     * pre: numPeople >= 2, numTests >= 0, numDaysInYear = 365 (given by instruction)
     * post: returns percentage of tests with at least one pair
     * @param numPeople number of people per test
     * @param numTests number of tests run with numPeople
     * @return percentage of tests with at least one pair
     */
    public static double birthdayPercentTest(int numPeople, int numTests) {
        int hasPair = 0;
        for (int i = 0; i < numTests; i++) {
            int pairs = CodeCamp.sharedBirthdays(numPeople, 365);
            if (pairs > 0) {
                hasPair++;
            }
        }
        return (double) hasPair / numTests * 100;
    }
    
    /**
      * Determine the Hamming distance between two arrays of ints.
      * Neither the parameter <tt>aData</tt> or
      * <tt>bData</tt> are altered as a result of this method.<br>
      * @param aData != null, aData.length == bData.length
      * @param bData != null
      * @return the Hamming Distance between the two arrays of ints.
      */
    public static int hammingDistance(int[] aData, int[] bData) {
        // check preconditions
        if (aData == null || bData == null || aData.length != bData.length) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "hammingDistance. neither parameter may equal null, arrays" +
                    " must be equal length.");
        }
        int hammingDistance = 0;
        for (int i = 0; i < aData.length; i++) {
            // add one to distance if two values at an indice are mismatched
            if (aData[i] != bData[i]) hammingDistance++;
        }
        return hammingDistance;
    }
 
    /**
      * Determine if one array of ints is a permutation of another.
      * Neither the parameter <tt>aData</tt> or
      * the parameter <tt>bData</tt> are altered as a result of this method.<br>
      * @param aData != null
      * @param bData != null
      * @return <tt>true</tt> if aData is a permutation of bData,
      * <tt>false</tt> otherwise
      *
      */
    public static boolean isPermutation(int[] aData, int[] bData) {
        // check preconditions
        if (aData == null || bData == null) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "isPermutation. neither parameter may equal null.");
        }
        // different lengths are automatically not a permutation
        if (aData.length != bData.length) return false;
        int length = aData.length;
        int index = 0;
        while (index < length) {
            int aCount = 0;
            int bCount = 0;
            // count each list for number of observed element (looping through aData)
            for (int i = 0; i < length; i++) {
                if (aData[i] == aData[index]) {
                    aCount++;
                }
                if (bData[i] == aData[index]) {
                    bCount++;
                }
            }
            // compare counts
            if (aCount != bCount) return false;
            index++;
        }
        return true;
    }
 
    /**
      * Determine the index of the String that
      * has the largest number of vowels.
      * Vowels are defined as <tt>'A', 'a', 'E', 'e', 'I', 'i', 'O', 'o',
      * 'U', and 'u'</tt>.
      * The parameter <tt>arrayOfStrings</tt> is not altered as a result of this method.
      * <p>pre: <tt>arrayOfStrings != null</tt>, <tt>arrayOfStrings.length > 0</tt>,
      * there is an least 1 non null element in arrayOfStrings.
      * <p>post: return the index of the non-null element in arrayOfStrings that has the
      * largest number of characters that are vowels.
      * If there is a tie return the index closest to zero.
      * The empty String, "", has zero vowels.
      * It is possible for the maximum number of vowels to be 0.<br>
      * @param arrayOfStrings the array to check
      * @return the index of the non-null element in arrayOfStrings that has
      * the largest number of vowels.
      */
    public static int mostVowels(String[] arrayOfStrings) {
        // check preconditions
        if (arrayOfStrings == null || arrayOfStrings.length == 0
                || !atLeastOneNonNull(arrayOfStrings)) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "mostVowels. parameter may not equal null and must contain " +
                    "at least one none null value.");
        }
        String vowels = "aeiou";
        int index = 0;
        // skip if String = null at beginning
        while (arrayOfStrings[index] == null) {
            index++;
        }
        // lowest index is first non-null String
        int maxIndex = index;
        int maxVowels = 0;
        for (int i = 0; i < arrayOfStrings.length; i++) {
            if (arrayOfStrings[i] != null) {
                int vowelCount = 0;
                String word = arrayOfStrings[i].toLowerCase();
                // check each letter for a vowel
                for (int j = 0; j < word.length(); j++) {
                    if (vowels.contains(word.substring(j, j+1))) vowelCount++;
                }
                // replaces maxVowels if counts more vowels
                if (vowelCount > maxVowels) {
                    maxVowels = vowelCount;
                    maxIndex = i;
                }
            }
        }
        return maxIndex;
    }
 
    /**
      * Perform an experiment simulating the birthday problem.
      * Pick random birthdays for the given number of people.
      * Return the number of pairs of people that share the
      * same birthday.<br>
      * @param numPeople The number of people in the experiment.
      * This value must be > 0
      * @param numDaysInYear The number of days in the year for this experiement.
      * This value must be > 0
      * @return The number of pairs of people that share a birthday
      * after running the simulation.
      */
    public static int sharedBirthdays(int numPeople, int numDaysInYear) {
        // check preconditions
        if (numPeople <= 0 || numDaysInYear <= 0) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "sharedBirthdays. both parameters must be greater than 0. " +
                    "numPeople: " + numPeople +
                    ", numDaysInYear: " + numDaysInYear);
        }
        int[] bDays = new int[numPeople];
        // initialize each person to a random birthday from 1-numDaysInYear
        for (int i = 0; i < bDays.length; i++) {
            bDays[i] = (int) (Math.random() * numDaysInYear + 1);
        }
        int numPairs = 0;
        // loops through all birthdays
        for (int i = 0; i < numPeople; i++) {
            int numSame = 1;
            for (int j = i + 1; j < numPeople; j++) {
                // if matches current birthday...
                if (bDays[i] != -1 && bDays[i] == bDays[j]) {
                    // add to match count
                    numSame++;
                    // set birthday to -1 to avoid double counting
                    bDays[j] = -1;
                }
            }
            // formula for number of pair combinations
            numPairs += numSame * (numSame-1) / 2;
        }
        return numPairs;
    }
 
    /**
      * Determine if the chess board represented by board is a safe set up.
      * <p>pre: board != null, board.length > 0, board is a square matrix.
      * (In other words all rows in board have board.length columns.),
      * all elements of board == 'q' or '.'. 'q's represent queens, '.'s
      * represent open spaces.<br>
      * <p>post: return true if the configuration of board is safe,
      * that is no queen can attack any other queen on the board.
      * false otherwise.
      * the parameter <tt>board</tt> is not altered as a result of
      * this method.<br>
      * @param board the chessboard
      * @return true if the configuration of board is safe,
      * that is no queen can attack any other queen on the board.
      * false otherwise.
      */
    public static boolean queensAreSafe(char[][] board) {
        char[] validChars = {'q', '.'};
        // check preconditions
        if (board == null || board.length == 0 || !isSquare(board)
                || !onlyContains(board, validChars)) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "queensAreSafe. The board may not be null, must be square, " +
                    "and may only contain 'q's and '.'s");
        }
        boolean safe = true;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] == 'q') {
                    if (!(check(board, r, c, 0, 1) && check(board, r, c, 0, -1) && 
                    check(board, r, c, 1, 0) && check(board, r, c, -1, 0) && 
                    check(board, r, c, 1, 1) && check(board, r, c, 1, -1) && 
                    check(board, r, c,-1, 1) && check(board, r, c, -1, -1))) return false;
                }
            }
        }
        return safe;
    }

    /**
     * Helper method that checks in any direction from a location on the board
     * pre: same as queensAreSafe Method, dr and dc should be limited from [-1,1]
     * to avoid skipping spaces
     * post: return true if queen's path is unobstructed aka safe
     * false otherwise
     * @param board the chessboard
     * @param row row location to begin checking from
     * @param col col location to begin checking from
     * @param dr change in row
     * @param dc change in col
     * @return true if row, col, or diagonal is safe (one queen)
     * false otherwise
     */

    private static boolean check(char[][] board, int row, int col, int dr, int dc) {
        row += dr;
        col += dc;
        if (row < 0 || row >= board.length || col < 0 || col >= board.length) {
            return true;
        }
        if (board[row][col] == 'q') {
            return false;
        }
        return check(board, row, col, dr, dc);
    }
 
    /**
      * Given a 2D array of ints return the value of the
      * most valuable contiguous sub rectangle in the 2D array.
      * The sub rectangle must be at least 1 by 1.
      * <p>pre: <tt>mat != null, mat.length > 0, mat[0].length > 0,
      * mat</tt> is a rectangular matrix.
      * <p>post: return the value of the most valuable contiguous sub rectangle
      * in <tt>city</tt>.<br>
      * @param city The 2D array of ints representing the value of
      * each block in a portion of a city.
      * @return return the value of the most valuable contiguous sub rectangle
      * in <tt>city</tt>.
      */
    public static int getValueOfMostValuablePlot(int[][] city) {
        // check preconditions
        if (city == null || city.length == 0 || city[0].length == 0
                || !isRectangular(city) ) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "getValueOfMostValuablePlot. The parameter may not be null," +
                    " must value at least one row and at least" +
                    " one column, and must be rectangular.");
        }
        // make sure MVP (most valuable plot) is valid element and NOT 0 because of negatives
        int MVP = city[0][0];
        // generate top-left corners
        for (int startRow = 0; startRow < city.length; startRow++) {
            for (int startCol = 0; startCol < city[0].length; startCol++) {
                // generate bottom-right corners
                for (int endRow = 0; endRow < city.length; endRow++) {
                    for (int endCol = 0; endCol < city[0].length; endCol++) {
                        int plotWorth = 0;
                        // from start to end row/col, add all plots
                        for (int newRow = startRow; newRow <= endRow; newRow++) {
                            for (int newCol = startCol; newCol <= endCol; newCol++) {
                                plotWorth += city[newRow][newCol];
                                // check is greater
                                if (plotWorth > MVP) MVP = plotWorth;
                            }
                        }
                    }
                }
            }
        }
        return MVP;
    }
 
    /*
    * Other Helper Methods
    */

    /*
    * pre: arrayOfStrings != null
    * post: return true if at least one element in arrayOfStrings is
    * not null, otherwise return false.
    */
    private static boolean atLeastOneNonNull(String[] arrayOfStrings) {
        // check precondition
        if (arrayOfStrings == null) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "atLeastOneNonNull. parameter may not equal null.");
        }
        boolean foundNonNull = false;
        int i = 0;
        while( !foundNonNull && i < arrayOfStrings.length ) {
            foundNonNull = arrayOfStrings[i] != null;
            i++;
        }
        return foundNonNull;
    }

    /*
    * pre: mat != null
    * post: return true if mat is a square matrix, false otherwise
    */
    private static boolean isSquare(char[][] mat) {
        if (mat == null) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "isSquare. Parameter may not be null.");
        }
        final int numRows = mat.length;
        int row = 0;
        boolean isSquare = true;
        while (isSquare && row < numRows) {
            isSquare = ( mat[row] != null) && (mat[row].length == numRows);
            row++;
        }
        return isSquare;
    }


    /*
    * pre: mat != null, valid != null
    * post: return true if all elements in mat are one of the
    * characters in valid
    */
    private static boolean onlyContains(char[][] mat, char[] valid) {
        // check preconditions
        if (mat == null || valid == null) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "onlyContains. Parameters may not be null.");
        }
        String validChars = new String(valid);
        int row = 0;
        boolean onlyContainsValidChars = true;
        while (onlyContainsValidChars && row < mat.length) {
            int col = 0;
            while(onlyContainsValidChars && col < mat[row].length) {
                int indexOfChar = validChars.indexOf(mat[row][col]);
                onlyContainsValidChars = indexOfChar != -1;
                col++;
            }
            row++;
        }
        return onlyContainsValidChars;
    }


    /*
    * pre: mat != null, mat.length > 0
    * post: return true if mat is rectangular
    */
    private static boolean isRectangular(int[][] mat) {
        // check preconditions
        if (mat == null || mat.length == 0) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "isRectangular. Parameter may not be null and must contain" +
                    " at least one row.");
        }
        boolean correct = mat[0] != null;
        int row = 0;
        while(correct && row < mat.length) {
            correct = (mat[row] != null)
                    && (mat[row].length == mat[0].length);
            row++;
        }
        return correct;
    }

    // make constructor private so no instances of CodeCamp can not be created
    private CodeCamp() {

    }
 }