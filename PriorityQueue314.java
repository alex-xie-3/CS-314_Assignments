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
        int pos = binarySearch(myCon, item);
        // traverse to last matching freq element
        while (pos < myCon.size() && myCon.get(pos).getFrequency() == item.getFrequency()) {
            pos++;
        }
        myCon.add(pos, item);
        return true;
    }

    /**
     * Helper method for enqueue. Performs binary search to find an
     * item in the priority queue.
     */
    private int binarySearch(ArrayList<TreeNode> arr, TreeNode item) {
        int low = 0;
        int high = arr.size() - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr.get(mid).getFrequency() > item.getFrequency()) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
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
}