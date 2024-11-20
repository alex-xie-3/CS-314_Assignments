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
        for (int i = 0; i < arr.size(); i++)
        {
            TreeNode node = arr.get(i);
            // only insert at i if we know the node frequency is greater than item freq.
            // This is to do the tie breaker.
            // TODO: compareTo
            if (node.getFrequency() > item.getFrequency())
            {
                return i;
            }
        }
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

    public String toString()
    {
        return myCon.toString();
    }
}