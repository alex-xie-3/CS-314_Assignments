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


import java.util.ArrayList;
import java.util.Collections;

/**
 * Tester class for the methods in Recursive.java
* @author scottm
*
*/
public class RecursiveTester {

    // run the tests
    public static void main(String[] args) {
        studentTests();
    }

    // post: run student test
    private static void studentTests() {
        // Test 1, 2 - getBinary
        doBinaryTests();
        // Test 3, 4 - revString
        doReverseTests();
        // Test 5, 6 - nextIsDouble
        doNextIsDoubleTests();
        // Test 7, 8 - recursiveMnemonics
        doListMnemonicsTests();
        // Test 9, 10- canFlowOffMap
        doFlowOffMapTests();
        // Test 11, 12- fairTeams
        doFairTeamsTests();
        // Test 13, 14- canEscapeMaze
        doMazeTests();
    }

    private static void doMazeTests() {
        runMazeTest("$GSGE", 5, 2, 13);

        runMazeTest("******S**E******", 4, 0, 14);
    }

    private static void runMazeTest(String rawMaze, int rows, int expected, int num) {
        char[][] maze = makeMaze(rawMaze, rows);
        System.out.println("Can escape maze test number " + num);
        printMaze(maze);
        int actual = Recursive.canEscapeMaze(maze);
        if (expected == actual) {
            System.out.println("passed test " + num);
        } else {
            System.out.println("FAILED TEST " + num);
            System.out.println("Expected result: " + expected);
            System.out.println("Actual result  : " + actual);
        }
        System.out.println();
    }

    // print the given maze
    // pre: none
    private static void printMaze(char[][] maze) {
        if (maze == null) {
            System.out.println("NO MAZE GIVEN");
        } else {
            for (char[] row : maze) {
                for (char c : row) {
                    System.out.print(c);
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }

    // generate a char[][] given the raw string
    // pre: rawMaze != null, rawMaze.length() % rows == 0
    private static char[][] makeMaze(String rawMaze, int rows) {
        if (rawMaze == null || rawMaze.length() % rows != 0) {
            throw new IllegalArgumentException("Violation of precondition in makeMaze."
                            + "Either raw data is null or left over values: ");
        }
        int cols = rawMaze.length() / rows;
        char[][] result = new char[rows][cols];
        int rawIndex = 0;
        for (int r = 0; r < result.length; r++) {
            for (int c = 0; c < result[0].length; c++) {
                result[r][c] = rawMaze.charAt(rawIndex);
                rawIndex++;
            }
        }
        return result;
    }

    // Test the Sierpinski carpet method.
    private static void doCarpetTest() {
        Recursive.drawCarpet(729, 9);
        Recursive.drawCarpet(729, 1);
    }

    private static void doFairTeamsTests() {
        int[] abilities = {1, 100};
        showFairTeamsResults(Recursive.minDifference(2, abilities), 99, 11);

        abilities = new int[] {0, 0, 0, 0};
        showFairTeamsResults(Recursive.minDifference(3, abilities), 0, 12);
    }

    // Show the results of a fair teams test by comparing actual and expected result.
    private static void showFairTeamsResults(int actual, int expected, int testNum) {
        if (actual == expected) {
            System.out.println("Test " + testNum + " passed. min difference.");
        } else {
            System.out.println("Test " + testNum + " failed. min difference.");
            System.out.println("Expected result: " + expected);
            System.out.println("Actual result  : " + actual);
        }
    }

    private static void doFlowOffMapTests() {
        int testNum = 9;
        int[][] world = {{5}};
        doOneFlowTest(world, 0, 0, true, testNum++);

        world = new int[][]
        {{10, 10, 10, 10, 10, 10, 10},
         {10, 10, 10,  5, 10, 10, 10},
         {10,  6, 10,  6, 10,  6, 10},
         {10, 10,  7,  7,  7, 10, 10},
         {10,  6,  7,  8,  7,  6, 10},
         {10, 10,  7,  7,  7, 10, 10},
         {10,  6, 10,  6, 10,  6, 10},
         {10, 10, 10,  5, 10, 10, 10},
         {10, 10, 10, 10, 10, 10, 10}};

        doOneFlowTest(world, 3, 4, false, testNum++);
    }

    private static void doOneFlowTest(int[][] world, int r, int c,
            boolean expected, int testNum) {

        System.out.print("Test " + testNum);
        boolean actual = Recursive.canFlowOffMap(world, r, c);
        if (expected == actual) {
            System.out.println(" passed. " + "can flow off map.");
        } else {
            System.out.println(" FAILED TEST " + testNum + " can flow off map.");
            System.out.println("Expected result: " + expected);
            System.out.println("Actual result  : " + actual);
        }
    }

    private static void doListMnemonicsTests() {
        ArrayList<String> mnemonics = Recursive.listMnemonics("2");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        if (mnemonics.equals(expected)) {
            System.out.println("Test 7 passed. Phone mnemonics.");
        } else {
            System.out.println("Test 7 failed. Phone mnemonics.");
            System.out.println("Expected result: " + expected);
            System.out.println("Actual result  : " + mnemonics);
        }

        mnemonics = Recursive.listMnemonics("830");
        Collections.sort(mnemonics);
        expected.clear();
        expected.add("TD0");
        expected.add("TE0");
        expected.add("TF0");
        expected.add("UD0");
        expected.add("UE0");
        expected.add("UF0");
        expected.add("VD0");
        expected.add("VE0");
        expected.add("VF0");
        Collections.sort(expected);
        if (mnemonics.equals(expected)) {
            System.out.println("Test 8 passed. Phone mnemonics.");
        } else {
            System.out.println("Test 8 failed. Phone mnemonics.");
            System.out.println("Expected result: " + expected);
            System.out.println("Actual result  : " + mnemonics);
        }
    }

    private static void doNextIsDoubleTests() {
        int[] numsForDouble = {0, 0, 0, 5, 0, 0, 0};
        int actualDouble = Recursive.nextIsDouble(numsForDouble);
        int expectedDouble = 4;
        if (actualDouble == expectedDouble) {
            System.out.println("Test 5 passed. next is double.");
        } else {
            System.out.println("Test 5 failed. next is double. expected: "
                    + expectedDouble + ", actual: " + actualDouble);
        }

        numsForDouble = new int[] {};
        actualDouble = Recursive.nextIsDouble(numsForDouble);
        expectedDouble = 0;
        if (actualDouble == expectedDouble) {
            System.out.println("Test 6 passed. next is double.");
        } else {
            System.out.println("Test 6 failed. next is double. expected: "
                    + expectedDouble + ", actual: " + actualDouble);
        }
    }

    private static void doReverseTests() {
        String actualRev = Recursive.revString("swagalicious");
        String expectedRev = "suoicilagaws";
        if (actualRev.equals(expectedRev)) {
            System.out.println("Test 3 passed. reverse string.");
        } else {
            System.out.println("Test 3 failed. reverse string. expected: " +
                    expectedRev + ", actual: " + actualRev);
        }

        actualRev = Recursive.revString("");
        expectedRev = "";
        if (actualRev.equals(expectedRev)) {
            System.out.println("Test 4 passed. reverse string.");
        } else {
            System.out.println("Test 4 failed. reverse string. expected: "
                    + expectedRev + ", actual: " + actualRev);
        }
    }

    private static void doBinaryTests() {
        String actualBinary = Recursive.getBinary(69);
        String expectedBinary = "1000101";
        if (actualBinary.equals(expectedBinary)) {
            System.out.println("Test 1 passed. get binary.");
        } else {
            System.out.println("Test 1 failed. get binary. expected: "
                    + expectedBinary + ", actual: " + actualBinary);
        }

        actualBinary = Recursive.getBinary(-420);
        expectedBinary = "-110100100";
        if (actualBinary.equals(expectedBinary)) {
            System.out.println("Test 2 passed. get binary.");
        } else {
            System.out.println("Test 2 failed. get binary. expected: "
                    + expectedBinary + ", actual: " + actualBinary);
        }
    }
}