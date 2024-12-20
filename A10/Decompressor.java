import java.io.IOException;

/**
 * The Decompressor class decompresses a file into huffman encoding and writes the
 * decoded information into a new file.
 */
public class Decompressor extends HuffTree {
    
    public Decompressor(BitInputStream bis, BitOutputStream bos) throws IOException {
        super(bis, bos);
        this.root = null;
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
    public int decode(BitInputStream bitsIn, BitOutputStream bitsOut) throws IOException {
        readHeaderInfo(bitsIn);

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
                // if leaf
                if (node.isLeaf()) {
                    // end writing is PSEUDO_EOF
                    if (node.getValue() == IHuffConstants.PSEUDO_EOF) {
                       done = true; 
                    } else {
                        // otherwise write value
                        bitsOut.writeBits(IHuffConstants.BITS_PER_WORD, node.getValue()); 
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

    /**
     * Helper method for decode. Reads header info and instantiates frequency map or tree.
     */
    private void readHeaderInfo(BitInputStream bitsIn) throws IOException {
        int num = bitsIn.readBits(BITS_PER_INT);
        if (num == IHuffConstants.STORE_COUNTS) {
            for (int i = 0; i < ALPH_SIZE; i++) { // i represents ASCII
                num = bitsIn.readBits(BITS_PER_INT);
                 // num reps freq
                if (num != 0) {
                    freqMap.put(i, num);
                }
            }
            System.out.println(freqMap);
            freqMap.put(PSEUDO_EOF, 1);
            this.root = buildCodingTree();
        } else if (num == IHuffConstants.STORE_TREE) {
            // make helper method to make tree in STF
            int sizeOfTree = bitsIn.readBits(BITS_PER_INT);
            TreeNode node = root;
            this.root = decodeTreeHelper(node, bitsIn, sizeOfTree, new int[1]);
        }
    }

    // Helper method builds code tree.
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

    // Helper method builds tree.
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
}
