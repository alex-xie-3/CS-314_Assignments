import java.io.IOException;
import java.util.TreeMap;

/**
 * The HuffTree class takes the similarities between Compressor and Decompressor and combines them
 * into a superclass. (In hindsight, this superclass was completely useless. The only thing it 
 * really did was allow me to call IHuffConstants without referencing the interface.)
 */
public class HuffTree implements IHuffConstants {

    // instance variables are protected in order to retain visibility throughout subclasses
    protected int HEADER;
    protected TreeMap<Integer, Integer> freqMap; // maps ASCII of char to frequency
    protected PriorityQueue314<TreeNode> pq;
    protected TreeNode root;
    protected TreeMap<Integer, String> codeMap; // maps ASCII of char to string rep of tree binary path

    protected final int BITS_PER_TREE_LEAF = 9;

    // Constructor intended for the Compressor subclass
    public HuffTree(int HEADER, BitInputStream bis) throws IOException {
        this.HEADER = HEADER;
        this.freqMap = new TreeMap<>();
        this.pq = new PriorityQueue314<>();
        this.codeMap = new TreeMap<>();
    }

    // Constructor intended for the Decompressor subclass
    public HuffTree(BitInputStream bis, BitOutputStream bos) throws IOException {
        this.freqMap = new TreeMap<>();
        this.pq = new PriorityQueue314<>();
        this.codeMap = new TreeMap<>();
    }

}
