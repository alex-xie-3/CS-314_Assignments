import java.io.IOException;
import java.util.TreeMap;

public class HuffTree implements IHuffConstants {

    protected int HEADER;
    protected TreeMap<Integer, Integer> freqMap; // maps ASCII of char to frequency
    protected PriorityQueue314<TreeNode> pq;
    protected TreeNode root;
    protected TreeMap<Integer, String> codeMap; // maps ASCII of char to string rep of tree binary path

    protected final int BITS_PER_TREE_LEAF = 9;

    public HuffTree(BitInputStream bis, BitOutputStream bos) throws IOException {
        this.freqMap = new TreeMap<>();
        this.pq = new PriorityQueue314<>();
        this.codeMap = new TreeMap<>();
    }

    public HuffTree(int HEADER, BitInputStream bis) throws IOException {
        this.HEADER = HEADER;
        this.freqMap = new TreeMap<>();
        this.codeMap = new TreeMap<>();
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
