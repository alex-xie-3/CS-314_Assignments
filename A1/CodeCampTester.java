// CodeCamp.java - CS314 Assignment 1 - Tester class

/*
 * Student information for assignment: 
 * Name: Alex Xie
 * email address: alex.xie@utexas.edu
 * UTEID: ayx72
 * Section 5 digit ID: 50259
 * Grader name: Eliza Bidwell
 * Number of slip days used on this assignment: 0
 */

/*
 * CS314 Students: place results of shared birthdays experiments in this
 * comment.
 * Average pairs of people with shared birthdays: 45 pairs
 * Prediction: 30 people
 * Conclusion: 23 people are required to have a 50% chance of there being at least 1 shared birthday,
 * given a 365 day year.
 * This result does surprise me with how low it is. It is about 25% lower than my prediction.
Num people: 2, number of experiments with one of more shared birthday: 0, percentage: 0.31
Num people: 3, number of experiments with one of more shared birthday: 0, percentage: 0.78
Num people: 4, number of experiments with one of more shared birthday: 4, percentage: 1.65
Num people: 5, number of experiments with one of more shared birthday: 10, percentage: 2.92
Num people: 6, number of experiments with one of more shared birthday: 24, percentage: 4.1
Num people: 7, number of experiments with one of more shared birthday: 35, percentage: 5.73
Num people: 8, number of experiments with one of more shared birthday: 56, percentage: 7.58
Num people: 9, number of experiments with one of more shared birthday: 81, percentage: 9.48
Num people: 10, number of experiments with one of more shared birthday: 110, percentage: 11.67
Num people: 11, number of experiments with one of more shared birthday: 154, percentage: 14.42
Num people: 12, number of experiments with one of more shared birthday: 192, percentage: 16.5
Num people: 13, number of experiments with one of more shared birthday: 247, percentage: 19.94
Num people: 14, number of experiments with one of more shared birthday: 308, percentage: 22.41
Num people: 15, number of experiments with one of more shared birthday: 375, percentage: 25.36
Num people: 16, number of experiments with one of more shared birthday: 448, percentage: 28.12
Num people: 17, number of experiments with one of more shared birthday: 527, percentage: 31.3
Num people: 18, number of experiments with one of more shared birthday: 612, percentage: 34.58
Num people: 19, number of experiments with one of more shared birthday: 722, percentage: 38.07
Num people: 20, number of experiments with one of more shared birthday: 820, percentage: 41.24
Num people: 21, number of experiments with one of more shared birthday: 924, percentage: 44.19
Num people: 22, number of experiments with one of more shared birthday: 1034, percentage: 47.36
Num people: 23, number of experiments with one of more shared birthday: 1150, percentage: 50.24
Num people: 24, number of experiments with one of more shared birthday: 1272, percentage: 53.9
Num people: 25, number of experiments with one of more shared birthday: 1400, percentage: 56.99
Num people: 26, number of experiments with one of more shared birthday: 1560, percentage: 60.24
Num people: 27, number of experiments with one of more shared birthday: 1674, percentage: 62.66
Num people: 28, number of experiments with one of more shared birthday: 1820, percentage: 65.48
Num people: 29, number of experiments with one of more shared birthday: 1972, percentage: 68.12
Num people: 30, number of experiments with one of more shared birthday: 2100, percentage: 70.68
Num people: 31, number of experiments with one of more shared birthday: 2263, percentage: 73.12
Num people: 32, number of experiments with one of more shared birthday: 2400, percentage: 75.45
Num people: 33, number of experiments with one of more shared birthday: 2541, percentage: 77.33
Num people: 34, number of experiments with one of more shared birthday: 2686, percentage: 79.59
Num people: 35, number of experiments with one of more shared birthday: 2835, percentage: 81.81
Num people: 36, number of experiments with one of more shared birthday: 2952, percentage: 82.93
Num people: 37, number of experiments with one of more shared birthday: 3108, percentage: 84.72
Num people: 38, number of experiments with one of more shared birthday: 3268, percentage: 86.4
Num people: 39, number of experiments with one of more shared birthday: 3393, percentage: 87.87
Num people: 40, number of experiments with one of more shared birthday: 3560, percentage: 89.07
Num people: 41, number of experiments with one of more shared birthday: 3690, percentage: 90.5
Num people: 42, number of experiments with one of more shared birthday: 3822, percentage: 91.51
Num people: 43, number of experiments with one of more shared birthday: 3956, percentage: 92.21
Num people: 44, number of experiments with one of more shared birthday: 4048, percentage: 92.99
Num people: 45, number of experiments with one of more shared birthday: 4230, percentage: 94.19
Num people: 46, number of experiments with one of more shared birthday: 4324, percentage: 94.76
Num people: 47, number of experiments with one of more shared birthday: 4465, percentage: 95.44
Num people: 48, number of experiments with one of more shared birthday: 4608, percentage: 96.15
Num people: 49, number of experiments with one of more shared birthday: 4704, percentage: 96.64
Num people: 50, number of experiments with one of more shared birthday: 4850, percentage: 97.04
Num people: 51, number of experiments with one of more shared birthday: 4947, percentage: 97.45
Num people: 52, number of experiments with one of more shared birthday: 5044, percentage: 97.89
Num people: 53, number of experiments with one of more shared birthday: 5194, percentage: 98.14
Num people: 54, number of experiments with one of more shared birthday: 5292, percentage: 98.45
Num people: 55, number of experiments with one of more shared birthday: 5390, percentage: 98.63
Num people: 56, number of experiments with one of more shared birthday: 5488, percentage: 98.82
Num people: 57, number of experiments with one of more shared birthday: 5643, percentage: 99.03
Num people: 58, number of experiments with one of more shared birthday: 5742, percentage: 99.18
Num people: 59, number of experiments with one of more shared birthday: 5841, percentage: 99.29
Num people: 60, number of experiments with one of more shared birthday: 5940, percentage: 99.43
Num people: 61, number of experiments with one of more shared birthday: 6039, percentage: 99.49
Num people: 62, number of experiments with one of more shared birthday: 6138, percentage: 99.56
Num people: 63, number of experiments with one of more shared birthday: 6237, percentage: 99.66
Num people: 64, number of experiments with one of more shared birthday: 6336, percentage: 99.71
Num people: 65, number of experiments with one of more shared birthday: 6435, percentage: 99.77
Num people: 66, number of experiments with one of more shared birthday: 6534, percentage: 99.8
Num people: 67, number of experiments with one of more shared birthday: 6633, percentage: 99.86
Num people: 68, number of experiments with one of more shared birthday: 6732, percentage: 99.86
Num people: 69, number of experiments with one of more shared birthday: 6831, percentage: 99.9
Num people: 70, number of experiments with one of more shared birthday: 6930, percentage: 99.93
Num people: 71, number of experiments with one of more shared birthday: 7029, percentage: 99.9
Num people: 72, number of experiments with one of more shared birthday: 7128, percentage: 99.95
Num people: 73, number of experiments with one of more shared birthday: 7227, percentage: 99.96
Num people: 74, number of experiments with one of more shared birthday: 7326, percentage: 99.98
Num people: 75, number of experiments with one of more shared birthday: 7425, percentage: 99.98
Num people: 76, number of experiments with one of more shared birthday: 7524, percentage: 99.98
Num people: 77, number of experiments with one of more shared birthday: 7623, percentage: 99.98
Num people: 78, number of experiments with one of more shared birthday: 7722, percentage: 99.98
Num people: 79, number of experiments with one of more shared birthday: 7821, percentage: 99.99
Num people: 80, number of experiments with one of more shared birthday: 7920, percentage: 99.99
Num people: 81, number of experiments with one of more shared birthday: 8019, percentage: 100.0
Num people: 82, number of experiments with one of more shared birthday: 8118, percentage: 100.0
Num people: 83, number of experiments with one of more shared birthday: 8217, percentage: 99.99
Num people: 84, number of experiments with one of more shared birthday: 8316, percentage: 99.99
Num people: 85, number of experiments with one of more shared birthday: 8415, percentage: 100.0
Num people: 86, number of experiments with one of more shared birthday: 8514, percentage: 100.0
Num people: 87, number of experiments with one of more shared birthday: 8613, percentage: 100.0
Num people: 88, number of experiments with one of more shared birthday: 8800, percentage: 100.0
Num people: 89, number of experiments with one of more shared birthday: 8900, percentage: 100.0
Num people: 90, number of experiments with one of more shared birthday: 9000, percentage: 100.0
Num people: 91, number of experiments with one of more shared birthday: 9100, percentage: 100.0
Num people: 92, number of experiments with one of more shared birthday: 9200, percentage: 100.0
Num people: 93, number of experiments with one of more shared birthday: 9300, percentage: 100.0
Num people: 94, number of experiments with one of more shared birthday: 9400, percentage: 100.0
Num people: 95, number of experiments with one of more shared birthday: 9500, percentage: 100.0
Num people: 96, number of experiments with one of more shared birthday: 9600, percentage: 100.0
Num people: 97, number of experiments with one of more shared birthday: 9603, percentage: 100.0
Num people: 98, number of experiments with one of more shared birthday: 9800, percentage: 100.0
Num people: 99, number of experiments with one of more shared birthday: 9900, percentage: 100.0
Num people: 100, number of experiments with one of more shared birthday: 10000, percentage: 100.0
*/

public class CodeCampTester {

    public static void main(String[] args) {

        final String newline = System.getProperty("line.separator");

        // test 1, hamming distance
        int[] h1 = {1, 2, 3};
        int[] h2 = {1, 2, 3};
        int expected = 0;
        int actual = CodeCamp.hammingDistance(h1, h2);
        System.out.println("Test 1 hamming distance: expected value: " + expected
                + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed test 1, hamming distance");
        } else {
            System.out.println(" ***** FAILED ***** test 1, hamming distance");
        }
        
        // test 2, hamming distance
        h1 = new int[] {5, 4, 3, 2, 1};
        h2 = new int[] {1, 2, 3, 4, 5};
        expected = 4;
        actual = CodeCamp.hammingDistance(h1, h2);
        System.out.println(newline + "Test 2 hamming distance: expected value: " + expected
                + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed test 2, hamming distance");
        } else {
            System.out.println(" ***** FAILED ***** test 2, hamming distance");
        }
        
        // test 3, isPermutation
        int[] a = {1, 3};
        int[] b = {1, 2};
        boolean expectedBool = false;
        boolean actualBool = CodeCamp.isPermutation(a, b);
        System.out.println(newline + "Test 3 isPermutation: expected value: " + expectedBool
                + ", actual value: " + actualBool);
        if (expectedBool == actualBool) {
            System.out.println(" passed test 3, isPermutation");
        } else {
            System.out.println(" ***** FAILED ***** test 3, isPermutation");
        }
        
        // test 4, isPermutation
        a = new int[] {1, 2};
        b = new int[] {5, 2, 4, 5, 3};
        expectedBool = false;
        actualBool = CodeCamp.isPermutation(a, b);
        System.out.println(newline + "Test 4 isPermutation: expected value: " + expectedBool
                + ", actual value: " + actualBool);
        if (expectedBool == actualBool) {
            System.out.println(" passed test 4, isPermutation");
        } else {
            System.out.println(" ***** FAILED ***** test 4, isPermutation");
        }
        
        // test 5, mostVowels
        String[] arrayOfStrings = { "AeIoU", "aAaaAAaAa" , null};
        int expectedResult = 1;
        int actualResult = CodeCamp.mostVowels(arrayOfStrings);
        System.out.println(newline + "Test 5 mostVowels: expected result: " + expectedResult
                + ", actual result: " + actualResult);
        if (actualResult == expectedResult) {
            System.out.println("passed test 5, mostVowels");
        } else {
            System.out.println("***** FAILED ***** test 5, mostVowels");
        }
        
        // test 6, mostVowels
        arrayOfStrings = new String[] { "waeouhfg", null, "", "sdiouhf", "i HaTe mY lIfE", 
        "ajas;kdf", "!@#$%^&*(()(*&^%$%^&))"};
        expectedResult = 4;
        actualResult = CodeCamp.mostVowels(arrayOfStrings);
        System.out.println(newline + "Test 6 mostVowels: expected result: " + expectedResult
                + ", actual result: " + actualResult);
        if (actualResult == expectedResult) {
            System.out.println("passed test 6, mostVowels");
        } else {
            System.out.println("***** FAILED ***** test 6, mostVowels");
        }
        
        // test 7, sharedBirthdays
        int pairs = CodeCamp.sharedBirthdays(10, 1);
        System.out.println(newline + "Test 7 shared birthdays: expected: 45, actual: " + pairs);
        int expectedShared = 45;
        if (pairs == expectedShared) {
            System.out.println("passed test 7, shared birthdays");
        } else {
            System.out.println("***** FAILED ***** test 7, shared birthdays");
        }
        
        // test 8, sharedBirthdays
        pairs = CodeCamp.sharedBirthdays(3, 2);
        System.out.println(newline + "Test 8 shared birthdays: expected: "
                + "a value of 1 or more, actual: " + pairs);
        if (pairs > 0) {
            System.out.println("passed test 8, shared birthdays");
        } else {
            System.out.println("***** FAILED ***** test 8, shared birthdays");
        }
        
        // test 9, queensAreASafe
        char[][] board = { { 'q', '.', '.' }, 
                           { 'q', '.', '.' }, 
                           { '.', '.', 'q' } };

        expectedBool = false;
        actualBool = CodeCamp.queensAreSafe(board);
        System.out.println(newline + "Test 9 queensAreSafe: expected value: " + expectedBool
                + ", actual value: " + actualBool);
        if (expectedBool == actualBool) {
            System.out.println(" passed test 9, queensAreSafe");
        } else {
            System.out.println(" ***** FAILED ***** test 9, queensAreSafe");
        }
        
        // test 10, queensAreASafe
        board = new char[][] { { 'q', '.', '.', '.', '.', '.', '.' },
                               { '.', '.', '.', '.', 'q', '.', '.' }, 
                               { '.', 'q', '.', '.', '.', '.', '.' },
                               { '.', '.', '.', '.', '.', 'q', '.' }, 
                               { '.', '.', 'q', '.', '.', '.', '.' },
                               { '.', '.', '.', '.', '.', '.', 'q' }, 
                               { '.', '.', '.', 'q', '.', '.', '.' } };
        expectedBool = true;
        actualBool = CodeCamp.queensAreSafe(board);
        System.out.println(newline + "Test 10 queensAreSafe: expected value: " + expectedBool
                + ", actual value: " + actualBool);
        if (expectedBool == actualBool) {
            System.out.println(" passed test 10, queensAreSafe");
        } else {
            System.out.println(" ***** FAILED ***** test 10, queensAreSafe");
        }

        // test 11, getValueOfMostValuablePlot
        int[][] city = new int[][] { { -1, 2, -3 },
                                     { 2, -3, 4 },
                                     { -2, 3, -4},
                                     { 1, -2, 3} };
        expected = 4;
        actual = CodeCamp.getValueOfMostValuablePlot(city);
        System.out.println(newline + "Test 37 getValueOfMostValuablePlot: expected value: "
                + expected + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed test 37, getValueOfMostValuablePlot");
        } else {
            System.out.println(" ***** FAILED ***** test 37, getValueOfMostValuablePlot");
        }
        
        // test 12, getValueOfMostValuablePlot
        city = new int[][] { {-10, 5, 5, -5}, 
                             {-21, 15, -10, 100}, 
                             {-5, -10, -28, -5},
                             {24, -5, 12, -20 }};

        expected = 110;
        actual = CodeCamp.getValueOfMostValuablePlot(city);
        System.out.println("\nTest 38 getValueOfMostValuablePlot: expected value: " + expected
                + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed test 38, getValueOfMostValuablePlot");
        } else {
            System.out.println(" ***** FAILED ***** test 38, getValueOfMostValuablePlot");
        }

    } // end of main method
}