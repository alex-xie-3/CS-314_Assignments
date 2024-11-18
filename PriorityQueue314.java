import java.util.ArrayList;

public class PriorityQueue314<E extends Comparable<? super E>> {
    private ArrayList<TreeNode> myCon;

    public PriorityQueue314() {
        myCon = new ArrayList<>();
    }

    public boolean add(TreeNode item) {
        int pos = binarySearch(myCon, item);
        while (pos < myCon.size() && myCon.get(pos).getFrequency() == item.getFrequency()) {
            pos++;
        }
        myCon.add(pos, item);
        return true;
    }

    // Helper method 
    private int binarySearch(ArrayList<TreeNode> arr, TreeNode item) {
        int low = 0;
        int high = arr.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr.get(mid).getFrequency() < item.getFrequency()) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public boolean dequeue() {
        return false;
    }
}