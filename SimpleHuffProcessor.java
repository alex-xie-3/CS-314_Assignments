/*  Student information for assignment:
 *
 *  On OUR honor, Alex Xie and Kevin Nguyen, this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 0
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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class SimpleHuffProcessor implements IHuffProcessor {

    private IHuffViewer myViewer;

    /**
     * Preprocess data so that compression is possible ---
     * count characters/create tree/store state so that
     * a subsequent call to compress will work. The InputStream
     * is <em>not</em> a BitInputStream, so wrap it int one as needed.
     * @param in is the stream which could be subsequently compressed
     * @param headerFormat a constant from IHuffProcessor that determines what kind of
     * header to use, standard count format, standard tree format, or
     * possibly some format added in the future.
     * @return number of bits saved by compression or some other measure
     * Note, to determine the number of
     * bits saved, the number of bits written includes
     * ALL bits that will be written including the
     * magic number, the header format number, the header to
     * reproduce the tree, AND the actual data.
     * @throws IOException if an error occurs while reading from the input file.
    */
    public int preprocessCompress(InputStream in, int headerFormat) throws IOException {
        BitInputStream bits = new BitInputStream(in);
        int inbits = bits.readBits(1);
        HashMap<Integer, Integer> freqs = new HashMap<>();
        int ogSize = countFreqs(bits, inbits, freqs);        
        bits.close();

        // enqueues all chars into priority queue
        PriorityQueue314<TreeNode> pq = new PriorityQueue314<>();
        TreeNode root = buildCodingTree(pq, freqs);

        // creates code map
        HashMap<Integer, String> codeMap = new HashMap<>();
        inOrderTraversal(root, "", codeMap);
        System.out.println(codeMap);

        // calculate return value
        int compSize = 0;
        for (Integer val : freqs.keySet()) {
            compSize += codeMap.get(val).length() * freqs.get(val);
        }
        return ogSize - compSize;
        // showString("Not working yet");
        // myViewer.update("Still not working");
        // throw new IOException("preprocess not implemented");
    }

    /**
     * Helper method for preprocessComplete. Counts frequencies for each char and stores in
     * a HashMap passed by reference.
     */
    private int countFreqs(BitInputStream bits, int inbits, 
                                            HashMap<Integer,Integer> freqs) throws IOException {
        int ogSize = 0;
        while (inbits != -1) {
            inbits = bits.readBits(IHuffConstants.BITS_PER_WORD);
            if (!freqs.containsKey(inbits)) {
                freqs.put(inbits, 1);
            } else {
                freqs.put(inbits, freqs.get(inbits) + 1);
            }
            ogSize++;
        }
        return ogSize;
    }

    /**
     * Helper method for preprocessComplete. Iteratively creates the coding tree using
     * Huffman encoding.
     */
    private TreeNode buildCodingTree(PriorityQueue314<TreeNode> pq, HashMap<Integer, Integer> freqs) {
        for (Integer i : freqs.keySet()) {
            TreeNode temp = new TreeNode(i, freqs.get(i));
            pq.enqueue(temp);
        }

        // tracks root
        TreeNode root = null;
        // coding tree creation
        while (pq.size() >= 2) {
            // dequeue 2 highest priority (lowest frequency) TreeNodes
            TreeNode temp1 = pq.dequeue();
            TreeNode temp2 = pq.dequeue();
            // -1 is placeholder value
            root = new TreeNode(-1, temp1.getFrequency() + temp2.getFrequency());
            root.setLeft(temp1);
            root.setRight(temp2);
            // add internal node
            pq.enqueue(root);
        }

        // add psuedo EOF value to priority queue
        // TODO
        pq.enqueue(new TreeNode(PSEUDO_EOF, 1));
        return root;
    }

    /**
     * Helper method for buildCodingTree. Recursively performs an in-order traversal for 
     * binary search tree, adding leaf nodes to coding map. 
     */
    private void inOrderTraversal(TreeNode root, String path, HashMap<Integer, String> map) {
        if (root != null)  {
            inOrderTraversal(root.getLeft(), path + "0", map);
            map.put(root.getValue(), path);
            path = "";
            inOrderTraversal(root.getRight(), path + "1", map); 
        }
        
    }

    /**
	 * Compresses input to output, where the same InputStream has
     * previously been pre-processed via <code>preprocessCompress</code>
     * storing state used by this call.
     * <br> pre: <code>preprocessCompress</code> must be called before this method
     * @param in is the stream being compressed (NOT a BitInputStream)
     * @param out is bound to a file/stream to which bits are written
     * for the compressed file (not a BitOutputStream)
     * @param force if this is true create the output file even if it is larger than the input file.
     * If this is false do not create the output file if it is larger than the input file.
     * @return the number of bits written.
     * @throws IOException if an error occurs while reading from the input file or
     * writing to the output file.
     */
    public int compress(InputStream in, OutputStream out, boolean force) throws IOException {
        BitInputStream inBits = new BitInputStream(in);
        BitOutputStream outBits = new BitOutputStream(out);
        outBits.writeBits(BITS_PER_INT, MAGIC_NUMBER);
        int magic = inBits.readBits(BITS_PER_INT);
        if (magic != MAGIC_NUMBER) {
            myViewer.showError("Error reading compressed file. \n" +
                    "File did not start with the huff magic number.");
            inBits.close();
            outBits.close();
            return -1;
        }

        inBits.close();
        outBits.close();
        throw new IOException("compress is not implemented");
        //return 0;
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
    public int uncompress(InputStream in, OutputStream out) throws IOException {
	        throw new IOException("uncompress not implemented");
	        //return 0;
    }

    public void setViewer(IHuffViewer viewer) {
        myViewer = viewer;
    }

    private void showString(String s){
        if (myViewer != null) {
            myViewer.update(s);
        }
    }
}
