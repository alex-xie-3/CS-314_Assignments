/*  Student information for assignment:
 *
 *  On OUR honor, Alex Xie and Kevin Nguyen, this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 2
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: ayx72
 *  email address: alex.xie@utexas.edu
 *  Grader name: Eliza Bidwell
 *
 *  Student 2
 *  UTEID: kmn2562
 *  email address: kevinnguyen4221@gmail.com
 *
*/

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PriorityQueue314<E extends Comparable<? super E>> {
    private ArrayList<TreeNode> myCon;

    public PriorityQueue314() {
        myCon = new ArrayList<>();
    }

    /**
     * Enqueues a TreeNode to PriorityQueue314. Adds in FIFO order i.e. duplicate
     * priorities (frequencies) will be added after existing priorites.
     * @param item item != null
     * @return true if myCon was modified, false otherwise
     */
    public boolean enqueue(TreeNode item) {
        // check preconditions
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        int pos = findPos(myCon, item);
        // traverse to last matching freq element
        myCon.add(pos, item);
        return true;
    }

    /**
     * Helper method for enqueue. Performs LINEAR search to find an
     * item in the priority queue.
     */
    private int findPos(ArrayList<TreeNode> arr, TreeNode item) {
        // linear search
        for (int i = 0; i < arr.size(); i++) {
            TreeNode curr = arr.get(i);
            // if curr freq is greater than item freq, add at that position
            if (curr.compareTo(item) > 0) {
                return i;
            }
        }
        // otherwise item freq is biggest
        return arr.size();
    }

    /**
     * Dequeues the first element in PriorityQueue314.
     * pre: size() > 0
     * @return the TreeNode removed
     */
    public TreeNode dequeue() {
        // check preconditions
        if (size() == 0) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        
        return myCon.remove(0);
    }

    /**
     * Returns size of PriorityQueue314.
     * @return size of myCon
    */
    public int size() {
        return myCon.size();
    }

    /**
     * Returns if PriorityQueue314 is empty i.e. has no elements.
     * @return true if myCon.size() == 0, false otherwise
     */
    public boolean isEmpty() {
        return myCon.size() == 0;
    }

    /**
     * Returns myCon. To help with debugging.
     */
    public String toString() {
        return myCon.toString();
    }
}