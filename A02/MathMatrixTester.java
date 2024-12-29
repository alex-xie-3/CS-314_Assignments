import java.util.Random;

/*  Student information for assignment:
 *  UTEID: ayx72
 *  email address: alex.xie@utexas.edu
 *  Grader name: Eliza Bidwell
 *  Number of slip days I am using: 0
*/

/* CS314 Students. Put your experiment results and
 * answers to questions here.
 * 
 * --------------------
 * Experiment 1 Results
 * --------------------
 * Adding two 1000 x 1000 matrices 1x: 0.025393292 seconds
 * Adding two 1000 x 1000 matrices 1000x: 60.812131083 seconds
 * Adding two 200 x 200 matrices 1000x: 2.085519 seconds
 * Adding two 400 x 400 matrices 1000x: 9.539037416 seconds
 * Adding two 800 x 800 matrices 1000x: 35.901375166 seconds
 * 
 * --------------------
 * Experiment 2 Results
 * --------------------
 * Multiplying 250 x 250 matrices 100x: 1.399121666 seconds
 * Multiplying 500 x 500 matrices 100x: 9.738447834 seconds
 * Multiplying 1000 x 1000 matrices 100x: 123.838908 seconds
 * 
 * ---------
 * Questions
 * ---------
 * Q1: The time would most likely quadruple, i.e. ~72s
 * Q2: O(N^2). Yes, because doubling size squared would be equivalent to multiplying by 4.
 * Q3: The time would most likely octuple, i.e. ~720s
 * Q4: O(N^3). Yes, because doubling size cubed would be equivalent to multiplying by 8.
 * Q5: 16357 x 16357 matrix. Estimate-1GB. ~4% of total RAM
 */

/*
 * A class to run tests on the MathMatrix class
 */
public class MathMatrixTester {

    /**
     * main method that runs simple test on the MathMatrix class
     *
     * @param args not used
     */
    public static void main(String[] args) {

        // test 1-2, getNumRows
        int[][] data1 = new int[][] { {1, 5, 3}, {2, -3, 3}, {5, 0, 9}, {10, 4, -2} };
        MathMatrix mat1 = new MathMatrix(data1);
        int expected = 4;
        int actual = mat1.getNumRows();
        System.out.println("Test 1 getNumRows: expected value: " + expected
            + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed test 1, getNumRows");
        } else {
            System.out.println(" ***** FAILED ***** test 1, getNumRows");
        }
        data1 = new int[][] { {0} };
        mat1 = new MathMatrix(data1);
        expected = 1;
        actual = mat1.getNumRows();
        System.out.println("Test 2 getNumRows: expected value: " + expected
            + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed test 2, getNumRows");
        } else {
            System.out.println(" ***** FAILED ***** test 2, getNumRows");
        }

        // test 3-4, getNumColumns
        data1 = new int[][] { {1, 5, 3}, {2, -3, 3}, {5, 0, 9}, {10, 4, -2} };
        mat1 = new MathMatrix(data1);
        expected = 3;
        actual = mat1.getNumColumns();
        System.out.println("Test 3 getNumColumns: expected value: " + expected
            + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed test 3, getNumColumns");
        } else {
            System.out.println(" ***** FAILED ***** test 3, getNumColumns");
        }
        data1 = new int[][] { {0} };
        mat1 = new MathMatrix(data1);
        expected = 1;
        actual = mat1.getNumColumns();
        System.out.println("Test 4 getNumColumns: expected value: " + expected
            + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed test 4, getNumColumns");
        } else {
            System.out.println(" ***** FAILED ***** test 4, getNumColumns");
        }

        data1 = new int[][] { {1, 5}, {17, 3} };
        int[][] data2 = new int[][] { {5, 6}, {5, 2} };

        // test 5-6, getVal
        mat1 = new MathMatrix(data1);
        expected = 17;
        actual = mat1.getVal(1, 0);
        System.out.println("Test 5 getVal: expected value: " + expected
            + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed test 5, getVal");
        } else {
            System.out.println(" ***** FAILED ***** test 5, getVal");
        }
        mat1 = new MathMatrix(data2);
        expected = 2;
        actual = mat1.getVal(1, 1);
        System.out.println("Test 6 getVal: expected value: " + expected
            + ", actual value: " + actual);
        if (expected == actual) {
            System.out.println(" passed test 6, getVal");
        } else {
            System.out.println(" ***** FAILED ***** test 6, getVal");
        }

        // tests 7-8, add
        int[][] data3;
        int[][] e1;
        mat1 = new MathMatrix(data1);
        MathMatrix mat2 = new MathMatrix(data2);
        MathMatrix mat3 = mat1.add(mat2);
        e1 = new int[][] { {6, 11}, {22, 5} };
        printTestResult(get2DArray(mat3), e1, 7, 
        "add method. Testing mat3 correct answer");
        data3 = new int[2][2];
        mat3 = new MathMatrix(data3);
        mat3 = mat1.add(mat3);
        e1 = new int[][] { {1, 5}, {17, 3} };
        printTestResult(get2DArray(mat3), e1, 8, 
        "add method. Testing mat3 unchanged");

        // tests 9-10, subtract
        mat3 = mat1.subtract(mat2);
        e1 = new int[][] { {-4, -1}, {12, 1} };
        printTestResult(get2DArray(mat3), e1, 9, 
        "subtract method. Testing mat3 correct answer");
        data3 = new int[2][2];
        mat3 = new MathMatrix(data3);
        mat3 = mat1.subtract(mat3);
        e1 = new int[][] { {1, 5}, {17, 3} };
        printTestResult(get2DArray(mat3), e1, 10, 
        "subtract method. Testing mat3 unchanged");

        // tests 11-12, multiply
        data1 = new int[][] { {2, 4}, {5, 2}, {1, 5} };
        data2 = new int[][] { {3, 4, 1}, {1, 8, 4} };
        mat1 = new MathMatrix(data1);
        mat2 = new MathMatrix(data2);
        mat3 = mat1.multiply(mat2);
        e1 = new int[][] { {10, 40, 18}, {17, 36, 13}, {8, 44, 21} };
        printTestResult(get2DArray(mat3), e1, 11, 
        "multiply method. Testing mat3 correct answer");
        data3 = new int[][] { {0, 0, 0}, {0, 0, 0} };
        mat1 = new MathMatrix(data1);
        mat2 = new MathMatrix(data3);
        mat3 = mat1.multiply(mat2);
        e1 = new int[][] { {0, 0, 0}, {0, 0, 0}, {0, 0, 0} };
        printTestResult(get2DArray(mat3), e1, 12, 
        "multiply method. Testing mat3 correct answer");

        // tests 13-14, getTranspose
        data3 = new int[][] { {2, 4, 0}, {5, 2, 12} };
        mat1 = new MathMatrix(data3);
        mat3 = mat1.getTranspose();
        e1 = new int[][] { {2, 5}, {4, 2}, {0, 12} };
        printTestResult(get2DArray(mat3), e1, 13, 
        "getTranspose method. Testing mat3 correct answer");
        data3 = new int[][] { {1, 0}, {0, 1} };
        mat1 = new MathMatrix(data3);
        mat3 = mat1.getTranspose();
        e1 = new int[][] { {1, 0}, {0, 1} };
        printTestResult(get2DArray(mat3), e1, 14, 
        "getTranspose method. Testing mat3 correct answer");

        // tests 15-16, isUpperTriangular
        data1 = new int[][] { {0, 0, 0}, {0, 0, 0}, {0, 0, 0} };
        mat1 = new MathMatrix(data1);
        boolean e = true;
        boolean a = mat1.isUpperTriangular();
        System.out.println("Test 15 isUpperTriangular: expected value: " + e
            + ", actual value: " + a);
        if (expected == actual) {
            System.out.println(" passed test 15, isUpperTriangular");
        } else {
            System.out.println(" ***** FAILED ***** test 15, isUpperTriangular");
        }
        data1 = new int[][] { {0} };
        mat1 = new MathMatrix(data1);
        e = true;
        a = mat1.isUpperTriangular();
        System.out.println("Test 16 isUpperTriangular: expected value: " + e
            + ", actual value: " + a);
        if (expected == actual) {
            System.out.println(" passed test 16, isUpperTriangular");
        } else {
            System.out.println(" ***** FAILED ***** test 16, isUpperTriangular");
        }
        
        // tests 17-18, getScaledMatrix
        data3 = new int[][] { {2, 4, 0}, {5, 2, 12} };
        mat1 = new MathMatrix(data3);
        mat3 = mat1.getScaledMatrix(2);
        e1 = new int[][] { {4, 8, 0}, {10, 4, 24} };
        printTestResult(get2DArray(mat3), e1, 15, 
        "getScaledMatrix method. Testing mat3 correct answer");
        data3 = new int[][] { {1, 0}, {0, 1} };
        mat1 = new MathMatrix(data3);
        mat3 = mat1.getScaledMatrix(5);
        e1 = new int[][] { {5, 0}, {0, 5} };
        printTestResult(get2DArray(mat3), e1, 16, 
        "getScaledMatrix method. Testing mat3 correct answer");

        // tests 19-20, toString
        data1 = new int[][] { {1, 2, 3}, {4, 5, 6}, {7, 8, 9} };
        mat1 = new MathMatrix(data1);
        String exp = "| 1 2 3|\n| 4 5 6|\n| 7 8 9|";
        String act = mat1.toString();
        System.out.println("Test 19 toString: \nexpected value:\n" + exp
            + "\nactual value:\n" + act);
        if (expected == actual) {
            System.out.println(" passed test 19, toString");
        } else {
            System.out.println(" ***** FAILED ***** test 19, toString");
        }
        data1 = new int[][] { {1000, 1}, {-213, 5} };
        mat1 = new MathMatrix(data1);
        exp = "| 1000    1|\n| -213    5|";
        act = mat1.toString();
        System.out.println("Test 20 toString: \nexpected value:\n" + exp
            + "\nactual value:\n" + act);
        if (expected == actual) {
            System.out.println(" passed test 20, toString");
        } else {
            System.out.println(" ***** FAILED ***** test 20, toString");
        }

        // tests 21-22, equals
        data1 = new int[][] { {100, -1}, {20, 01} };
        data2 = new int[][] { {100, -1}, {10, 1} };
        e = true;
        a = data1.equals(data2);
        System.out.println("Test 21 equals: expected value: " + e
            + ", actual value: " + a);
        if (expected == actual) {
            System.out.println(" passed test 21, equals");
        } else {
            System.out.println(" ***** FAILED ***** test 21, equals");
        }
        data1 = new int[][] { {0} };
        data2 = new int[][] { {0, 1} };
        e = false;
        a = data1.equals(data2);
        System.out.println("Test 22 equals: expected value: " + e
            + ", actual value: " + a);
        if (expected == actual) {
            System.out.println(" passed test 22, equals");
        } else {
            System.out.println(" ***** FAILED ***** test 22, equals");
        }

        // experiment1(800, 1000);
        // experiment2(1000, 100);
        // q5(16457);

    }

    private static void experiment1(int dimension, int times) {
        MathMatrix mat1;
        MathMatrix mat2;
        Stopwatch s = new Stopwatch();
        s.start();
        for (int i = 0; i < times; i++) {
            mat1 = createMat(new Random(), dimension, dimension, 100);
            mat2 = createMat(new Random(), dimension, dimension, 100);
            mat1.add(mat2);
        }
        s.stop();
        System.out.println(s);
    }

    private static void experiment2(int dimension, int times) {
        MathMatrix mat1;
        MathMatrix mat2;
        Stopwatch s = new Stopwatch();
        s.start();
        for (int i = 0; i < times; i++) {
            mat1 = createMat(new Random(), dimension, dimension, 100);
            mat2 = createMat(new Random(), dimension, dimension, 100);
            mat1.multiply(mat2);
        }
        s.stop();
        System.out.println(s);
    }

    private static void q5(int size) {
        MathMatrix test = new MathMatrix(new int[size][size]);
    }

    // create a matrix with random values
    // pre: rows > 0, cols > 0, randNumGen != null
    public static MathMatrix createMat(Random randNumGen, int rows,
            int cols, final int LIMIT) {

        if (randNumGen == null) {
            throw new IllegalArgumentException("randomNumGen variable may no be null");
        } else if(rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("rows and columns must be greater than 0. " +
                    "rows: " + rows + ", cols: " + cols);
        }

        int[][] temp = new int[rows][cols];
        final int SUB = LIMIT / 4;
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                temp[r][c] = randNumGen.nextInt(LIMIT) - SUB;
            }
        }

        return new MathMatrix(temp);
    }

    private static void printTestResult(int[][] data1, int[][] data2, int testNum, 
            String testingWhat) {
        System.out.print("Test number " + testNum + " tests the " + testingWhat +". The test ");
        String result = equals(data1, data2) ? "passed" : "failed";
        System.out.println(result);
    }

    // pre: m != null, m is at least 1 by 1 in size
    // return a 2d array of ints the same size as m and with 
    // the same elements
    private static int[][] get2DArray(MathMatrix m) {
        //check precondition
        if  ((m == null) || (m.getNumRows() == 0) 
                || (m.getNumColumns() == 0)) {
            throw new IllegalArgumentException("Violation of precondition: get2DArray");
        }

        int[][] result = new int[m.getNumRows()][m.getNumColumns()];
        for(int r = 0; r < result.length; r++) {
            for(int c = 0; c < result[0].length; c++) {
                result[r][c] = m.getVal(r,c);
            }
        }
        return result;
    }

    // pre: data1 != null, data2 != null, data1 and data2 are at least 1 by 1 matrices
    //      data1 and data2 are rectangular matrices
    // post: return true if data1 and data2 are the same size and all elements are
    //      the same
    private static boolean equals(int[][] data1, int[][] data2) {
        //check precondition
        if((data1 == null) || (data1.length == 0) 
                || (data1[0].length == 0) || !rectangularMatrix(data1)
                ||  (data2 == null) || (data2.length == 0)
                || (data2[0].length == 0) || !rectangularMatrix(data2)) {
            throw new IllegalArgumentException( "Violation of precondition: " + 
                    "equals check on 2d arrays of ints");
        }
        boolean result = (data1.length == data2.length) && (data1[0].length == data2[0].length);
        int row = 0;
        while (result && row < data1.length) {
            int col = 0;
            while (result && col < data1[0].length) {
                result = (data1[row][col] == data2[row][col]);
                col++;
            }
            row++;
        }

        return result;
    }

    // method to ensure mat is rectangular
    // pre: mat != null, mat is at least 1 by 1
    private static boolean rectangularMatrix( int[][] mat ) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            throw new IllegalArgumentException("Violation of precondition: "
                    + " Parameter mat may not be null" 
                    + " and must be at least 1 by 1");
        }
        return MathMatrix.rectangularMatrix(mat);
    }
}
