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

/**
 * QUESTIONS.TXT ANSWERS
 * Question 1:
 * Min Tree Height
 * 1000    9
 * 2000    10
 * 4000    11
 * 8000    12
 * 16000   13
 * 32000   14
 * 64000   15
 * 128000  16
 * 256000  17
 * 512000  18
 * 1024000 19
 * 
 * Questions 2:
1000 Elements: 0.0011412459
2000 Elements: 0.0010091332
4000 Elements: 0.0010344417
8000 Elements: 0.0026830457
16000 Elements: 0.0081328875
32000 Elements: 0.0222534126
64000 Elements: 0.021910174799999998
128000 Elements: 0.0440027
256000 Elements: 0.1031784459
512000 Elements: 0.2501079793
1024000 Elements: 0.6249547668
 * Comparison: My BST class operates at almost exactly the same efficiency as the TreeSet 
 * when adding RANDOMLY.
 * 
 * Question 3: My BST class operates exponentially slower than TreeSet. 
 * This is most likely due to TreeSet creating red-black trees for balance versus degenerate ones.
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

/**
 * Some test cases for CS314 Binary Search Tree assignment.
*/
public class BSTTester {

    /**
     * The main method runs the tests.
    * @param args Not used
    */
    public static void main(String[] args) {
        // studentTests();
        // experimentTests();
    }

    public static void studentTests() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        showTestResults(bst.add(1), 1);
        showTestResults(bst.isPresent(1), 2);
        showTestResults(bst.size() == 1, 3);
        List<Integer> l = new ArrayList<>();
        l.add((Integer) 1);
        showTestResults(l.equals(bst.getAll()), 4);
        showTestResults(l.equals(bst.getAllLessThan(2)), 5);
        showTestResults(l.equals(bst.getAllGreaterThan(0)), 6);
        showTestResults(bst.height() == 0, 7);
        showTestResults(bst.max() == 1, 8);
        showTestResults(bst.min() == 1, 9);

        showTestResults(!bst.add(1), 10);
        showTestResults(bst.add(2), 11);
        showTestResults(!bst.isPresent(3), 12);
        showTestResults(bst.size() == 2, 13);
        l.add((Integer) 2);
        showTestResults(l.equals(bst.getAll()), 14);
        l.remove((Integer) 2);
        showTestResults(l.equals(bst.getAllLessThan(2)), 15);
        l.remove((Integer) 1);
        l.add((Integer) 2);
        showTestResults(l.equals(bst.getAllGreaterThan(1)), 16);
        showTestResults(bst.height() == 1, 17);
        showTestResults(bst.max() == 2, 18);
        showTestResults(bst.min() == 1, 19);

        showTestResults(bst.remove((Integer) 1), 20);
        showTestResults(bst.remove((Integer) 2), 21);
        showTestResults(bst.iterativeAdd((Integer) 3), 22);
        showTestResults(bst.iterativeAdd((Integer) 5), 23);
        showTestResults(bst.numNodesAtDepth(0) == 1, 24);
        showTestResults(bst.numNodesAtDepth(1) == 1, 25);
        showTestResults(bst.get(0) == 3, 26);
        showTestResults(bst.get(1) == 5, 27);
    }

    public static void experimentTests() {
        System.out.println("****************************");
        System.out.println("******RANDOM ADD TESTS******");
        System.out.println("****************************");
        for (int i = 0; i <= 10; i++) {
            int n = (int) Math.pow(2, i) * 1000;
            BSTTests(n);
            TreeSetTests(n);
        }
        System.out.println("****************************");
        System.out.println("******SORTED ADD TESTS******");
        System.out.println("****************************");
        for (int i = 0; i <= 6; i++) {
            int n = (int) Math.pow(2, i) * 1000;
            TreeSetSortedTests(n);
            BSTSortedTests(n);
        }
    }

    public static void BSTTests(int n) {
        double tTotal = 0;
        int hTotal = 0;
        int sTotal = 0;
        System.out.println(n + " Elements");
        for (int j = 0; j < 10; j++) {
            Stopwatch s = new Stopwatch();
            Random r = new Random();
            BinarySearchTree<Integer> b = new BinarySearchTree<>();
            s.start();
            for(int i = 0; i < n; i++) {
                b.add((Integer) r.nextInt());
            }
            s.stop();
            double time = s.time();
            int height = b.height();
            int size = b.size();
            tTotal += time;
            hTotal += height;
            sTotal += size;
            // System.out.println("Test " + n + 1);
            // System.out.println("Time: " + time);
            // System.out.println("Height: " + height);
            // System.out.println("Size: " + size);
        //}
        System.out.print("BST Average Time: " + tTotal / 10 + " | ");
        System.out.print("Average Height: " + hTotal / 10 + " | ");
        System.out.println("Average Size: " + sTotal / 10);
        }
    }
    
    public static void TreeSetTests(int n) {
        double tTotal = 0;
        System.out.print(n + " Elements: ");
        for (int j = 0; j < 10; j++) {
            Stopwatch s = new Stopwatch();
            Random r = new Random();
            TreeSet<Integer> b = new TreeSet<>();
            s.start();
            for(int i = 0; i < n; i++) {
                b.add((Integer) r.nextInt());
            }
            s.stop();
            tTotal += s.time();
        }
        System.out.println(tTotal / 10);
    }

    public static void BSTSortedTests(int n) {
        double tTotal = 0;
        System.out.println(n + " Elements");
        for (int j = 0; j < 10; j++) {
            Stopwatch s = new Stopwatch();
            BinarySearchTree<Integer> b = new BinarySearchTree<>();
            s.start();
            for(int i = 0; i < n; i++) {
                b.iterativeAdd((Integer) i);
            }
            s.stop();
            tTotal += s.time();
        }
        System.out.println("BST Average Time: " + tTotal / 10 + " | ");
        System.out.println("Average Height: " + (n + 1) + " | ");
        System.out.println("Average Size: " + n + " | ");
    }

    public static void TreeSetSortedTests(int n) {
        double tTotal = 0;
        for (int j = 0; j < 10; j++) {
            Stopwatch s = new Stopwatch();
            TreeSet<Integer> b = new TreeSet<>();
            s.start();
            for(int i = 0; i < n; i++) {
                b.add((Integer) i);
            }
            s.stop();
            tTotal += s.time();
        }
        System.out.println("TreeSet Average Time: " + tTotal / 10);
    }
    
    private static void showTestResults(boolean passed, int testNum) {
        if (passed) {
            System.out.println("Test " + testNum + " passed.");
        } else {
            System.out.println("TEST " + testNum + " FAILED.");
        }
    }
}