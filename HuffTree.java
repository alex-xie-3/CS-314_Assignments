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
import java.io.IOException;
import java.util.TreeMap;
import java.util.ArrayList;

public class HuffTree implements IHuffConstants {
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

    public HuffTree(BitInputStream bis, BitOutputStream bos) throws IOException {
        this.freqMap = new TreeMap<>();
        this.myCounts = new int[ALPH_SIZE];
        // this.ogBitSize = countFreqs(bis, freqMap, myCounts);
        this.pq = new PriorityQueue314<>();
        this.root = null;
        // this.codeMap = new TreeMap<>();
        // int[] sizes = inOrderTraversal(root, "", new int[2], codeMap);
        // this.treeSize = sizes[0];
        // this.numLeaves = sizes[1];
    }

    public HuffTree(int HEADER, BitInputStream bis) throws IOException {
        this.HEADER = HEADER;
        this.freqMap = new TreeMap<>();
        this.myCounts = new int[ALPH_SIZE];
        this.ogBitSize = countFreqs(bis, freqMap, myCounts);
        this.pq = new PriorityQueue314<>();
        this.root = buildCodingTree();
        this.codeMap = new TreeMap<>();
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
        outStream.writeBits(BITS_PER_INT, HEADER);
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

    // FOR DECODING

    /**
     * Uncompress a previously compressed stream in, writing the
     * uncompressed bits/data to out.
     * @param in is the previously compressed data (not a BitInputStream)
     * @param out is the uncompressed file/stream
     * @return the number of bits written to the uncompressed file/stream
     * @throws IOException if an error occurs while reading from the input file or
     * writing to the output file.
    */
    public int decode(BitInputStream bitsIn, BitOutputStream bitsOut) throws IOException {
        // 1. read header format to see if we are in SCF or STF.
        TreeMap<Integer, Integer> freqs = new TreeMap<>();
        int num = bitsIn.readBits(BITS_PER_INT);
        if (num == IHuffConstants.STORE_COUNTS) {
            // TODO: make a new method to store all this crap or reuse the method we have but modified
            for (int i = 0; i < ALPH_SIZE; i++) { // i represents ASCII
                num = bitsIn.readBits(BITS_PER_INT);
                 // num reps freq
                if (!freqs.containsKey(num) && num != 0) {
                    freqs.put(i, num);
                }
            }
            this.freqMap = freqs;
            System.out.println(freqs);
            freqs.put(PSEUDO_EOF, 1);
            this.root = buildCodingTree();
        } else if (num == IHuffConstants.STORE_TREE) {
            // make helper method to make tree in STF
            int sizeOfTree = bitsIn.readBits(BITS_PER_INT);
            TreeNode node = root;
            this.root = decodeTreeHelper(node, bitsIn, sizeOfTree, new int[1]);
        }

        int totalBits = 0;
        TreeNode node = root;
        boolean done = false;
        while (!done) {
            int bit = bitsIn.readBits(1);
            if (bit == -1) {
                throw new IOException("Error reading compressed file. \n" +
                    "unexpected end of input. No PSEUDO_EOF value.");
            } else {
                // move left or right in tree based on value of bit
                // (move left if bit is 0, move right if bit is 1)
                if (bit == 0) {
                    node = node.getLeft();
                }
                else if (bit == 1) {
                    node = node.getRight();
                }

                if (node.isLeaf()) {
                    if (node.getValue() == IHuffConstants.PSEUDO_EOF) {
                       done = true; 
                    } else {
                        bitsOut.writeBits(IHuffConstants.BITS_PER_WORD, node.getValue()); // not sure if this is correct
                        totalBits += BITS_PER_WORD;
                    }
                    // write out value in leaf to output
                    // get back to root of tree
                    node = root;
                }
            }
        }
        bitsIn.close();
        bitsOut.close();
        return totalBits;
    }

    private TreeNode decodeTreeHelper(TreeNode node, BitInputStream in, int sizeOfTree, int[] count) throws IOException {   
        /*
            Use freq map in order to end recursion when we reach the end nodes
        */
        if (count[0] == sizeOfTree) {
            return node; // returns root of tree
        }
        count[0]++;
        int num = in.readBits(1);
        if (num == 0) {
            TreeNode newNode = new TreeNode(-1, 1);
            newNode.setLeft(decodeTreeHelper(newNode.getLeft(), in, sizeOfTree, count));
            newNode.setRight(decodeTreeHelper(newNode.getRight(), in, sizeOfTree, count));
            return newNode;
        }
        if (num == 1) {
            num = in.readBits(BITS_PER_TREE_LEAF);
            TreeNode newNode = new TreeNode(num, 1); // what am i supposed to put for freq.
            return newNode;
        } else {
            // we ran out of bits trying to form huffman code tree.
            throw new IOException("Error reading compressed file. \n");
        }
    }

    // FOR DEBUGGING

    public String printFreqMap() {
        StringBuilder s = new StringBuilder();
        s.append("Frequency Table\n");
        for (Integer i : freqMap.keySet()) {
            s.append(i + " ");
            s.append(Integer.toBinaryString(i) + " ");
            s.append(Character.toString(i) + " ");
            s.append(freqMap.get(i) + "\n");
        }
        return s.toString();
    }

    public String printCodeMap() {
        StringBuilder s = new StringBuilder();
        s.append("Code Map Table\n");
        for (Integer i : codeMap.keySet()) {
            s.append(i + " ");
            s.append(Character.toString(i) + " ");
            s.append(codeMap.get(i) + "\n");
        }
        return s.toString();
    }

}
