import java.util.TreeMap;

public class HuffTree {

    /**
     * Helper method for preprocessComplete. Iteratively creates the coding tree using
     * Huffman encoding.
     */
    public static TreeNode buildCodingTree(PriorityQueue314<TreeNode> pq, TreeMap<Integer, Integer> freqs) {
        // enqueue all elements into priority queue
        for (Integer i : freqs.keySet()) {
            pq.enqueue(new TreeNode(i, freqs.get(i)));
        }
        System.out.println(pq.toString());
        // tracks root
        TreeNode node = null;
        // coding tree creation
        while (pq.size() >= 2) {
            // dequeue 2 highest priority (lowest frequency) TreeNodes
            TreeNode temp1 = pq.dequeue();
            TreeNode temp2 = pq.dequeue();
            // -1 is placeholder value
            node = new TreeNode(temp1, -1, temp2);
            // add internal node
            pq.enqueue(node);
        }
        return node;
    }

    /**
     * Helper method for buildCodingTree. Recursively performs an in-order traversal for 
     * binary search tree, adding leaf nodes to coding map. 
     */
    public static int[] inOrderTraversal(TreeNode root, String path, TreeMap<Integer, String> map, int[] count) {
        if (root != null)  {
            inOrderTraversal(root.getLeft(), path + "0", map, count);
            if (root.isLeaf()) {
                map.put(root.getValue(), path);
                // keeps count of leaf nodes
                count[0]++;
            }
            count[1]++;
            inOrderTraversal(root.getRight(), path + "1", map, count); 
        }
        return count;
    }
}
