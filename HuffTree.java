import java.io.IOException;
import java.util.TreeMap;

public class HuffTree {
    private int HEADER;
    private TreeMap<Integer, Integer> freqMap; // maps ASCII of char to frequency
    private int[] myCounts;
    private PriorityQueue314<TreeNode> pq;
    private TreeNode root;
    private TreeMap<Integer, String> codeMap; // maps ASCII of char to string rep of tree binary path
    private int ogBitSize;
    private int treeSize;
    private int numLeaves;
    private final int BITS_PER_TREE_LEAF = 9;

    public HuffTree(int HEADER, BitInputStream bis) throws IOException {
        this.HEADER = HEADER;
        this.freqMap = new TreeMap<>();
        this.myCounts = new int[IHuffConstants.ALPH_SIZE];
        this.ogBitSize = countFreqs(bis, freqMap, myCounts);
        this.pq = new PriorityQueue314<>();
        this.root = null;
        this.numLeaves = buildCodingTree(root);
        this.codeMap = new TreeMap<>();
        this.treeSize = inOrderTraversal(root, "", new int[1], codeMap);
    }

    /**
     * Helper method for preprocessComplete. Counts frequencies for each char and stores in
     * a HashMap passed by reference.
     */
    private int countFreqs(BitInputStream bits, TreeMap<Integer,Integer> freqMap,
                                                            int[] myCounts) throws IOException {
        
        int inbits = bits.readBits(IHuffConstants.BITS_PER_WORD);
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
            ogSize += IHuffConstants.BITS_PER_WORD;
            inbits = bits.readBits(IHuffConstants.BITS_PER_WORD);
        }
        // add psuedo EOF value to priority queue
        freqMap.put(IHuffConstants.PSEUDO_EOF, 1);
        // closes stream to prevent data leaks
        bits.close();
        System.out.println(freqMap);
        return ogSize;
    }

    /**
     * Helper method for preprocessComplete. Iteratively creates the coding tree using
     * Huffman encoding.
     */
    private int buildCodingTree(TreeNode root) {
        int numLeaves = 0;
        // enqueue all elements into priority queue
        for (Integer i : freqMap.keySet()) {
            pq.enqueue(new TreeNode(i, freqMap.get(i)));
            numLeaves++;
        }
        System.out.println(pq.toString());
        // tracks root
        // coding tree creation
        while (pq.size() >= 2) {
            // dequeue 2 highest priority (lowest frequency) TreeNodes
            TreeNode temp1 = pq.dequeue();
            TreeNode temp2 = pq.dequeue();
            // -1 is placeholder value
            root = new TreeNode(temp1, -1, temp2);
            // add internal node
            pq.enqueue(root);
        }
        this.root = pq.dequeue();
        return numLeaves;
    }

    /**
     * Helper method for buildCodingTree. Recursively performs an in-order traversal for 
     * binary search tree, adding leaf nodes to coding map. 
     */
    private int inOrderTraversal(TreeNode root, String path, int[] count, TreeMap<Integer, String> codeMap) {
        if (root != null)  {
            inOrderTraversal(root.getLeft(), path + "0", count, codeMap);
            if (root.isLeaf()) {
                codeMap.put(root.getValue(), path);
            }
            count[0]++;
            inOrderTraversal(root.getRight(), path + "1", count, codeMap); 
        }
        return count[0];
    }

    public int calculateDiff() {
        int compSize = 0;
        for (Integer val : freqMap.keySet()) {
            compSize += codeMap.get(val).length() * freqMap.get(val);
        }
        // add magic number, header format, and header content
        compSize += IHuffConstants.BITS_PER_INT * 2;
        // header content
        if (HEADER == IHuffConstants.STORE_COUNTS) {
            compSize += IHuffConstants.ALPH_SIZE * IHuffConstants.BITS_PER_INT;
        } else if (HEADER == IHuffConstants.STORE_TREE) {
            // size of tree
            compSize += IHuffConstants.BITS_PER_INT;
            System.out.println(numLeaves);
            System.out.println(treeSize);
            compSize += numLeaves * BITS_PER_TREE_LEAF + treeSize;
        }
        System.out.println(ogBitSize);
        System.out.println(compSize);
        return ogBitSize - compSize;
    }

    public void encodeMap(BitOutputStream out) {
        for (int i = 0; i < IHuffConstants.ALPH_SIZE; i++) {
            out.writeBits(IHuffConstants.BITS_PER_INT, myCounts[i]);
        }
    }

    public void encodeTree(BitOutputStream out) {
        BitOutputStream bos = new BitOutputStream(out);
        int bitSize = numLeaves * BITS_PER_TREE_LEAF + treeSize;
        bos.writeBits(IHuffConstants.BITS_PER_INT, bitSize);
        preOrderTraversal(root, bos);
        bos.close();
    }

    private void preOrderTraversal(TreeNode node, BitOutputStream bos) {
        if (node != null) {
            if (node.isLeaf()) {
                bos.writeBits(IHuffConstants.BITS_PER_WORD + 1, node.getValue());
            }
            if (node.getLeft() != null) {
                bos.writeBits(1, 0);
                preOrderTraversal(node.getLeft(), bos);
            }
            if (node.getRight() != null) {
                bos.writeBits(1, 1);
                preOrderTraversal(node.getRight(), bos);
            }
        }
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeMap<Integer, String> getCodeMap() {
        return codeMap;
    }

    public int getTreeSize() {
        return treeSize;
    }

    public int getNumLeaves() {
        return numLeaves;
    }

    /**
     * Uncompress a previously compressed stream in, writing the
     * uncompressed bits/data to out.
     * @param in is the previously compressed data (not a BitInputStream)
     * @param out is the uncompressed file/stream
     * @return the number of bits written to the uncompressed file/stream
     * @throws IOException if an error occurs while reading from the input file or
     * writing to the output file.
     */
    public int decode() throws IOException
    {
        TreeNode node = root;
        boolean done = false;
        while (!done)
        {
            int bit = bitsIn.readBits(1);
            if (bit == -1)
            {
                throw new IOException("Error reading compressed file. \n" +
                    "unexpected end of input. No PSEUDO_EOF value.");
            }
            else
            {
                // move left or right in tree based on value of bit
                // (move left if bit is 0, move right if bit is 1)
                if (bit == 0)
                {
                    node = node.getLeft();
                }
                else if (bit == 1)
                {
                    node = node.getRight();
                }

                if(node.isLeaf()) {
                    if(val is the pseudo end of file value)
                        done = true;
                    else
                        write out value in leaf to output
                        get back to root of tree
            }
        }
    }

}
