import java.util.Arrays;

// MathMatrix.java - CS314 Assignment 2

/*  Student information for assignment:
*
*  On my honor, Alex Xie, this programming assignment is my own work
*  and I have not provided this code to any other student.
*
*  UTEID: ayx72
*  email address: alex.xie@utexas.edu
*  Unique section number: 50259
*  Number of slip days I am using: 0
*/

/**
 * A class that models systems of linear equations (Math Matrices)
 * as used in linear algebra.
 */
public class MathMatrix {

    // instance variables
    private int[][] values;
    private int LONGEST_DIGIT_LENGTH;

    /**
     * create a MathMatrix with cells equal to the values in mat.
     * A "deep" copy of mat is made.
     * Changes to mat after this constructor do not affect this
     * Matrix and changes to this MathMatrix do not affect mat
     * @param  mat  mat !=null, mat.length > 0, mat[0].length > 0,
     * mat is a rectangular matrix
     */
    public MathMatrix(int[][] mat) {
        if (!rectangularMatrix(mat)) {
            throw new IllegalArgumentException("Violation of precondition: " +
                        "MathMatrix. Matrix must be rectangular and non-null.");
        }
        values = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                // sets value of each element
                values[i][j] = mat[i][j];
                // checks for longest digit length (for later use in spacing)
                int digitLength = ("" + values[i][j]).length();
                if (digitLength > LONGEST_DIGIT_LENGTH) {
                    LONGEST_DIGIT_LENGTH = digitLength;
                }
            }
        }
    }

    /**
     * create a MathMatrix of the specified size with all cells set to the intialValue.
     * <br>pre: numRows > 0, numCols > 0
     * <br>post: create a matrix with numRows rows and numCols columns.
     * All elements of this matrix equal initialVal.
     * In other words after this method has been called getVal(r,c) = initialVal
     * for all valid r and c.
     * @param numRows numRows > 0
     * @param numCols numCols > 0
     * @param initialVal all cells of this Matrix are set to initialVal
     */
    public MathMatrix(int numRows, int numCols, int initialVal) {
        if (numRows <= 0 || numCols <= 0) {
            throw new IllegalArgumentException("Violation of precondition: " +
                        "MathMatrix. Matrix must be rectangular and non-null.");
        }
        values = new int[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // sets each element to set value
                values[i][j] = initialVal;
            }
        }
        // longest digit length must be initialVal length
        LONGEST_DIGIT_LENGTH = ("" + initialVal).length();
    }

    /**
     * Get the number of rows.
     * @return the number of rows in this MathMatrix
     */
    public int getNumRows() {

        return values.length;
    }

    /**
     * Get the number of columns.
     * @return the number of columns in this MathMatrix
     */
    public int getNumColumns() {
        return values[0].length;
    }

    /**
     * get the value of a cell in this MathMatrix.
     * <br>pre: row  0 <= row < getNumRows(), col  0 <= col < getNumColumns()
     * @param  row  0 <= row < getNumRows()
     * @param  col  0 <= col < getNumColumns()
     * @return the value at the specified position
     */
    public int getVal(int row, int col) {
        if (row < 0 || row >= getNumRows() || col < 0 || col >= getNumColumns()) {
            throw new IllegalArgumentException("Violation of precondition: " +
                        "getVal. row and col must be greater than or equal to 0.");
        }
        return values[row][col];
    }

    /**
     * implements MathMatrix addition, (this MathMatrix) + rightHandSide.
     * <br>pre: rightHandSide != null, rightHandSide.getNumRows() = getNumRows(),
     * rightHandSide.getNumColumns() = getNumColumns()
     * <br>post: This method does not alter the calling object or rightHandSide
     * @param rightHandSide rightHandSide.getNumRows() = getNumRows(),
     * rightHandSide.getNumColumns() = getNumColumns()
     * @return a new MathMatrix that is the result of adding this Matrix to rightHandSide.
     * The number of rows in the returned Matrix is equal to the number of rows in this MathMatrix.
     * The number of columns in the returned Matrix is equal to the number of columns
     * in this MathMatrix.
     */
    public MathMatrix add(MathMatrix rightHandSide) {
        // check for null is unecessary due to isRectangular check in initialization
        if (rightHandSide.getNumRows() != getNumRows() || 
        rightHandSide.getNumColumns() != getNumColumns()) {
            throw new IllegalArgumentException("Violation of precondition: " +
                        "add. matrices must have equal number of rows and cols.");
        }
        final int ROWS = getNumRows();
        final int COLS = getNumColumns();
        int[][] sumMat = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // add corresponding elements
                sumMat[i][j] = getVal(i, j) + rightHandSide.getVal(i, j);
            }
        }
        return new MathMatrix(sumMat);
    }

    /**
     * implements MathMatrix subtraction, (this MathMatrix) - rightHandSide.
     * <br>pre: rightHandSide != null, rightHandSide.getNumRows() = getNumRows(),
     * rightHandSide.getNumColumns() = getNumColumns()
     * <br>post: This method does not alter the calling object or rightHandSide
     * @param rightHandSide rightHandSide.getNumRows() = getNumRows(),
     * rightHandSide.getNumColumns() = getNumColumns()
     * @return a new MathMatrix that is the result of subtracting rightHandSide
     * from this MathMatrix. The number of rows in the returned MathMatrix is equal to the number
     * of rows in this MathMatrix.The number of columns in the returned MathMatrix is equal to
     * the number of columns in this MathMatrix.
     */
    public MathMatrix subtract(MathMatrix rightHandSide) {
        if (rightHandSide.getNumRows() != getNumRows() || 
        rightHandSide.getNumColumns() != getNumColumns()) {
            throw new IllegalArgumentException("Violation of precondition: " +
                    "subtract. matrices must have equal number of rows and cols.");
        }
        final int ROWS = getNumRows();
        final int COLS = getNumColumns();
        int[][] subMat = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // subtract corresponding elements
                subMat[i][j] = getVal(i, j) - rightHandSide.getVal(i, j);
            }
        }
        return new MathMatrix(subMat);
    }

    /**
     * implements matrix multiplication, (this MathMatrix) * rightHandSide.
     * <br>pre: rightHandSide != null, rightHandSide.getNumRows() = getNumColumns()
     * <br>post: This method should not alter the calling object or rightHandSide
     * @param rightHandSide rightHandSide.getNumRows() = getNumColumns()
     * @return a new MathMatrix that is the result of multiplying
     * this MathMatrix and rightHandSide.
     * The number of rows in the returned MathMatrix is equal to the number of rows
     * in this MathMatrix. The number of columns in the returned MathMatrix is equal to the number
     * of columns in rightHandSide.
     */
    public MathMatrix multiply(MathMatrix rightHandSide) {
        if (rightHandSide.getNumRows() != getNumColumns() || 
            rightHandSide.getNumRows() != getNumColumns()) {
                throw new IllegalArgumentException("Violation of precondition: multiply. " +
                "matrix must have equal number of rows as cols on rightHandSide and vice versa.");
        }
        final int ROWS = getNumRows();
        final int COLS = rightHandSide.getNumColumns();
        final int SHARE = getNumColumns();

        int[][] multiMat = new int[ROWS][COLS];
        // loops through columns of rightHandSide
        for (int col = 0; col < COLS; col++) {
            // loops through current matrix's rows (which match above columns)
            for (int row = 0; row < ROWS; row++) {
                // loops through the shared length (matrix cols & rHS rows)
                for (int x = 0; x < SHARE; x++) {
                    multiMat[row][col] += getVal(row, x) * rightHandSide.getVal(x, col);
                }
            }
        }
        return new MathMatrix(multiMat);
    }

    /**
     * Create and return a new Matrix that is a copy
     * of this matrix, but with all values multiplied by a scale
     * value.
     * <br>pre: none
     * <br>post: returns a new Matrix with all elements in this matrix
     * multiplied by factor.
     * In other words after this method has been called
     * returned_matrix.getVal(r,c) = original_matrix.getVal(r, c) * factor
     * for all valid r and c.
     * @param factor the value to multiply every cell in this Matrix by.
     * @return a MathMatrix that is a copy of this MathMatrix, but with all
     * values in the result multiplied by factor.
     */
    public MathMatrix getScaledMatrix(int factor) {
        final int ROWS = getNumRows();
        final int COLS = getNumColumns();
        int[][] scaledMat = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // multiply each element by factor
                scaledMat[i][j] = getVal(i, j) * factor;
            }
        }
        return new MathMatrix(scaledMat);
    }

    /**
     * accessor: get a transpose of this MathMatrix.
     * This Matrix is not changed.
     * <br>pre: none
     * @return a transpose of this MathMatrix
     */
    public MathMatrix getTranspose() {
        final int ROWS = getNumRows();
        final int COLS = getNumColumns();
        // initialize new array with switched cols/rows
        int[][] transMat = new int[COLS][ROWS];
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                // add each element in a reversed coordinate
                transMat[i][j] = getVal(j, i);
            }
        }
        return new MathMatrix(transMat);
    }

    /**
     * override equals.
     * @return true if rightHandSide is the same size as this MathMatrix and all values in the
     * two MathMatrix objects are the same, false otherwise
     */
    public boolean equals(Object rightHandSide){
        /* CS314 Students. The following is standard equals
         * method code. We will learn about in the coming weeks.
         *
         * We use getClass instead of instanceof because we only want a MathMatrix to equal
         * another MathMatrix as opposed to any possible sub classes. We would
         * use instance of if we were implementing am interface and wanted to equal
         * other objects that are instances of that interface but not necessarily
         * MathMatrix objects.
         */

        if (rightHandSide == null || this.getClass() != rightHandSide.getClass()) {
            return false;
        }
        // We know rightHandSide refers to a non-null MathMatrix object, safe to cast.
        // MathMatrix otherMathMatrix = (MathMatrix) rightHandSide;
        // Now we can access the private instance variables of otherMathMatrix
        // and / or call MathMatrix methods on otherMathMatrix.

        return true; // CS314 students, alter as necessary
    }

    /**
     * override toString.
     * @return a String with all elements of this MathMatrix.
     * Each row is on a separate line.
     * Spacing based on longest element in this Matrix.
     */
    public String toString() {
        // create string builder
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < getNumRows(); i++) {
            result.append("|");
            for (int j = 0; j < getNumColumns(); j++) {
                int digitLength = ("" + getVal(i, j)).length();
                // add difference in number of spaces + 1
                // this way, longest digit still gets a space
                for (int k = 0; k < LONGEST_DIGIT_LENGTH-digitLength+1; k++) {
                    result.append(" ");
                }
                // add value at curren
                result.append(getVal(i, j));
            }
            // if not last row, add new line character
            if (i != getNumRows()-1) {
                result.append("|\n");
            } 
        }
        return result.toString();
    }

    /**
     * Return true if this MathMatrix is upper triangular. To
     * be upper triangular all elements below the main
     * diagonal must be 0.<br>
     * pre: this is a square matrix. getNumRows() == getNumColumns()
     * @return <tt>true</tt> if this MathMatrix is upper triangular,
     * <tt>false</tt> otherwise.
     */
    public boolean isUpperTriangular(){
        if (getNumRows() != getNumColumns()) {
            throw new IllegalArgumentException("Violation of precondtion " +
                                "isUpperTriangular. matrix must be square.");
        }
        // DIM = dimension of square
        final int DIM = getNumRows();
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                // if row val is larger than col val, it must be below main diagonal
                // if also a non-zero value, MathMatrix is not an upper triangular
                if (i > j && getVal(i, j) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Ensure mat is a rectangular matrix. Method is public so
     * client classes can also use it.
     * @param mat mat != null, mat must have at least one row,
     * there must be at least one column in the first row, and
     * all rows in mat must have the same number of columns.
     * @return true if mat is rectangular, false otherwise.
     */
    public static boolean rectangularMatrix(int[][] mat) {
        if (mat == null || mat.length == 0) {
            throw new IllegalArgumentException("argument mat may not be null and must "
                    + " have at least one row. mat = " + Arrays.toString(mat));
        } else if (mat[0] == null) {
            throw new IllegalArgumentException("argument may not have null rows. "
                    + "mat = " + Arrays.toString(mat));
        } else if (mat[0].length == 0) {
            throw new IllegalArgumentException("argument mat must have at least "
                    + "one column per row.");
        }
        boolean isRectangular = true;
        int row = 1;
        final int COLUMNS = mat[0].length;
        while (isRectangular && row < mat.length) {
            // Odd to put this here. Maybe do a two pass approach?
            if (mat[row] == null) {
                throw new IllegalArgumentException("argument may not have null rows. "
                        + "mat = " + Arrays.toString(mat));
            }
            isRectangular = (mat[row].length == COLUMNS);
            row++;
        }
        return isRectangular;
    }

}