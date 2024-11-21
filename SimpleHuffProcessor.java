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
import java.util.TreeMap;

public class SimpleHuffProcessor implements IHuffProcessor {

    private IHuffViewer myViewer;
    private TreeMap<Integer, String> codeMap;
    private TreeNode root;
    private int HEADER;
    private int treeSize;
    private int numLeaves;
    private TreeMap<Integer, Integer> freqs;
    private int[] myCounts;
    private final int BITS_PER_TREE_LEAF = 9;

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
        // initializes header
        HEADER = headerFormat;

        BitInputStream bits = new BitInputStream(in);
        // HashMap maps chars to frequency
        freqs = new TreeMap<>();
        myCounts = new int[ALPH_SIZE];
        int ogSize = countFreqs(bits, freqs, myCounts);
        // add psuedo EOF value to priority queue
        freqs.put(PSEUDO_EOF, 1);        
        System.out.println(freqs);

        // enqueues all chars into priority queue
        PriorityQueue314<TreeNode> pq = new PriorityQueue314<>();
        root = HuffTree.buildCodingTree(pq, freqs);

        // intializes code map
        codeMap = new TreeMap<>();
        int[] sizes = HuffTree.inOrderTraversal(root, "", codeMap, new int[2]);
        // TODO -bad code practice?
        numLeaves = sizes[0];
        treeSize = sizes[1];

        System.out.println(codeMap);

        int diff = calculateDiff(ogSize, HEADER);
        if (diff < 0) {
            myViewer.showError("Compressed file has " + diff + " more bits than uncompressed " +
            "file.\n Select \"force\" compresssion option to compress.");
        }
        return diff;
        // showString("Not working yet");
        // myViewer.update("Still not working");
        // throw new IOException("preprocess not implemented");
    }

    /**
     * Helper method for preprocessComplete. Counts frequencies for each char and stores in
     * a HashMap passed by reference.
     */
    private int countFreqs(BitInputStream bits, TreeMap<Integer,Integer> freqs, 
                                                int[] myCounts) throws IOException {
        
        int inbits = bits.readBits(BITS_PER_WORD);
        int size = 0;
        // read through all streamed bits
        while (inbits != -1) {
            // if new, create key with count of 1
            if (!freqs.containsKey(inbits)) {
                freqs.put(inbits, 1);
            // otherwise, add 1 to current count
            } else {
                freqs.put(inbits, freqs.get(inbits) + 1);
            }
            // updates myCounts (used for SCF later)
            myCounts[inbits]++;
            // add standard ASCII encoding size
            size += BITS_PER_WORD;
            inbits = bits.readBits(BITS_PER_WORD);
        }
        // closes stream to prevent data leaks
        bits.close();
        return size;
    }

    private int calculateDiff(int ogSize, int header) {
        int compSize = 0;
        for (Integer val : freqs.keySet()) {
            compSize += codeMap.get(val).length() * freqs.get(val);
        }
        // add magic number, header format, and header content
        compSize += BITS_PER_INT * 2;
        // header content
        if (header == STORE_COUNTS) {
            compSize += ALPH_SIZE * BITS_PER_INT;
        } else if (header == STORE_TREE) {
            // size of tree
            compSize += BITS_PER_INT;
            System.out.println(numLeaves);
            System.out.println(treeSize);
            compSize += numLeaves * BITS_PER_TREE_LEAF + treeSize;
        }
        System.out.println(ogSize);
        System.out.println(compSize);
        return ogSize - compSize;
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
        // initializes input (read) and output (write) streams
        BitInputStream inStream = new BitInputStream(in);
        BitOutputStream outStream = new BitOutputStream(out);
        // writes magic number indicating huffman file
        outStream.writeBits(BITS_PER_INT, MAGIC_NUMBER);
        // writes header format
        outStream.writeBits(BITS_PER_INT, HEADER);
        // writes header content
        if (HEADER == STORE_COUNTS) {
            encodeMap(outStream); // SCF
        } else if (HEADER == STORE_TREE) {
            encodeTree(root, outStream); // STF
        }
        // converts every char in file to huffman encoded binary
        // TODO - figure out if compression writing can be done in just SCF format
        int totalBits = 0;
        int inbits = inStream.readBits(BITS_PER_WORD);
        while (inbits != -1) {
            // TODO- fix integer.parse int
            outStream.writeBits(codeMap.get(inbits).length(), Integer.parseInt(codeMap.get(inbits)));
            totalBits += codeMap.get(inbits).length();
            inbits = inStream.readBits(BITS_PER_WORD);
        }
        // TODO- writes pseudo-EOF file
        outStream.writeBits(codeMap.get(PSEUDO_EOF).length(), Integer.parseInt(codeMap.get(PSEUDO_EOF)));
        inStream.close();
        outStream.close();
        return totalBits;
        //throw new IOException("compress is not implemented");
        //return 0;
    }

    private void encodeMap(BitOutputStream out) {
        for(int i = 0; i < ALPH_SIZE; i++) {
            out.writeBits(BITS_PER_INT, myCounts[i]);
        }
    }

    private void encodeTree(TreeNode root, BitOutputStream out) {
        BitOutputStream bos = new BitOutputStream(out);
        int bitSize = numLeaves * BITS_PER_TREE_LEAF + treeSize;
        bos.writeBits(BITS_PER_INT, bitSize);
        preOrderTraversal(root, bos);
        bos.close();
    }

    private void preOrderTraversal(TreeNode node, BitOutputStream bos) {
        while (node != null) {
            if (node.isLeaf()) {
                bos.writeBits(BITS_PER_WORD + 1, node.getValue());
            }
            
            preOrderTraversal(node.getLeft(), bos);
            bos.writeBits(1, 0);
            preOrderTraversal(node.getRight(), bos);
            bos.writeBits(1, 1);
        }
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
        BitInputStream inStream = new BitInputStream(in);
        BitOutputStream outStream = new BitOutputStream(out);
        int magic = inStream.readBits(BITS_PER_INT);
        if (magic != MAGIC_NUMBER) {
            myViewer.showError("Error reading compressed file. \n" +
                    "File did not start with the huff magic number.");
            inStream.close();
            outStream.close();
            return -1;
        }
        inStream.close();
        outStream.close();
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
