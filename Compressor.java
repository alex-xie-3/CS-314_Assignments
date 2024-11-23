import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class Compressor extends HuffTree {
    private int[] myCounts;
    private int ogBitSize;
    private int treeSize;
    private int numLeaves;
    private final int BITS_PER_TREE_LEAF = 9;


    public Compressor(int HEADER, BitInputStream bis) throws IOException {
        super(HEADER, bis);
        this.myCounts = new int[ALPH_SIZE];
        this.ogBitSize = countFreqs(bis, freqMap, myCounts);
        this.root = buildCodingTree();
        int[] sizes = inOrderTraversal(root, "", new int[2], codeMap);
        this.treeSize = sizes[0];
        this.numLeaves = sizes[1];
    }

    /**
     * Helper method for preprocessComplete. Counts frequencies for each char and stores in
     * a HashMap passed by reference.
     */
    private int countFreqs(BitInputStream bis, TreeMap<Integer,Integer> freqMap,
                                                            int[] myCounts) throws IOException {
        
        int inbits = bis.readBits(BITS_PER_WORD);
        int ogSize = 0;
        // read through all streamed bits
        while (inbits != -1) {
            // if new, create key with count of 1
            if (!freqMap.containsKey(inbits)) {
                freqMap.put(inbits, 1);
            // otherwise, add 1 to current count
            } else {
                freqMap.put(inbits, freqMap.get(inbits) + 1);
            }
            // updates myCounts (used for SCF later)
            myCounts[inbits]++;
            // add standard ASCII encoding size
            ogSize += BITS_PER_WORD;
            inbits = bis.readBits(BITS_PER_WORD);
        }
        // add psuedo EOF value to priority queue
        freqMap.put(PSEUDO_EOF, 1);
        // closes stream to prevent data leaks
        bis.close();
        return ogSize;
    }

    /**
     * Helper method for preprocessComplete. Iteratively creates the coding tree using
     * Huffman encoding.
     */
    private TreeNode buildCodingTree() {
        // enqueue all elements into priority queue
        for (Integer i : freqMap.keySet()) {
            pq.enqueue(new TreeNode(i, freqMap.get(i)));
        }
        // tracks root
        // coding tree creation
        while (pq.size() >= 2) {
            // dequeue 2 highest priority (lowest frequency) TreeNodes
            TreeNode temp1 = pq.dequeue();
            TreeNode temp2 = pq.dequeue();
            // -1 is placeholder value
            // add internal node
            pq.enqueue(new TreeNode(temp1, -1, temp2));
        }
        return pq.dequeue();
    }

    /**
     * Helper method for buildCodingTree. Recursively performs an in-order traversal for 
     * binary search tree, adding leaf nodes to coding map. 
     */
    private int[] inOrderTraversal(TreeNode root, String path, int[] count, TreeMap<Integer, String> codeMap) {
        if (root != null)  {
            inOrderTraversal(root.getLeft(), path + "0", count, codeMap);
            if (root.isLeaf()) {
                codeMap.put(root.getValue(), path);
                count[1]++;
            }
            count[0]++;
            inOrderTraversal(root.getRight(), path + "1", count, codeMap); 
        }
        return count;
    }

    public int calculateDiff() {
        int compSize = 0;
        for (Integer val : freqMap.keySet()) {
            compSize += codeMap.get(val).length() * freqMap.get(val);
        }
        // add magic number, header format, and header content
        compSize += BITS_PER_INT * 2;
        // header content
        if (HEADER == STORE_COUNTS) {
            compSize += ALPH_SIZE * BITS_PER_INT;
        } else if (HEADER == STORE_TREE) {
            // size of tree
            compSize += BITS_PER_INT;
            compSize += numLeaves * BITS_PER_TREE_LEAF + treeSize;
        }
        return ogBitSize - compSize;
    }

    public int getOGBitSize() {
        return ogBitSize;
    }

    // FOR COMPRESSING

    private void encodeMap(BitOutputStream out) {
        for (int i = 0; i < ALPH_SIZE; i++) {
            out.writeBits(BITS_PER_INT, myCounts[i]);
        }
    }

    private void encodeTree(BitOutputStream out) {
        int bitSize = numLeaves * BITS_PER_TREE_LEAF + treeSize;
        out.writeBits(BITS_PER_INT, bitSize);
        ArrayList<TreeNode> preOrder = new ArrayList<>();
        preOrderTraversal(root, preOrder);
        for (TreeNode t : preOrder) {
            if (t.getValue() == -1) {
                out.writeBits(1, 0);
            } else {
                out.writeBits(1, 1);
                out.writeBits(BITS_PER_WORD + 1, t.getValue());
            }
        }
    }

    private void preOrderTraversal(TreeNode node, ArrayList<TreeNode> result) {
        if (node != null) {
            result.add(node);
            preOrderTraversal(node.getLeft(), result);
            preOrderTraversal(node.getRight(), result);
        }
    }

    public int encode(BitInputStream inStream, BitOutputStream outStream) throws IOException {
        // writes magic number indicating huffman file
        outStream.writeBits(BITS_PER_INT, MAGIC_NUMBER);
        // writes header format
        outStream.writeBits(BITS_PER_INT, this.HEADER);
        // writes header content
        if (HEADER == STORE_COUNTS) {
            encodeMap(outStream); // SCF
        } else if (HEADER == STORE_TREE) {
            encodeTree(outStream); // STF
        }
        // converts every char in file to huffman encoded binary
        int totalBits = 0;
        int inbits = inStream.readBits(BITS_PER_WORD);
        while (inbits != -1) {
            outStream.writeBits(codeMap.get(inbits).length(), Integer.parseInt(codeMap.get(inbits), 2));
            totalBits += codeMap.get(inbits).length();
            inbits = inStream.readBits(BITS_PER_WORD);
        }
        outStream.writeBits(codeMap.get(PSEUDO_EOF).length(), Integer.parseInt(codeMap.get(PSEUDO_EOF), 2));
        // close streams
        inStream.close();
        outStream.close();
        return totalBits;
    }
}
