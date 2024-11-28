/*  Student information for assignment:
 *
 *  On OUR honor, Alex Xie and Kevin Nguyen, this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 2
 *
 *  Student 1
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

public class SimpleHuffProcessor implements IHuffProcessor {

    private IHuffViewer myViewer;
    private int HEADER;
    // compress must be able to access precompress's Compressor object 
    private Compressor c;

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

        BitInputStream bis = new BitInputStream(in);

        // intializes Compressor object
        c = new Compressor(HEADER, bis);

        // returns the difference between original file size and compressed file size
        return c.calculateDiff();
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
        // if no space is saved in the compressed file, and force compression isn't on
        if (c.calculateDiff() < 0 && !force) {
            myViewer.showError("Compressed file has " + c.calculateDiff() +  " more bits than " +
            "uncompressed file.\n Select \"force\" compression option to compress.");
            return 0;
        }
        // initializes input (read) and output (write) streams
        BitInputStream inStream = new BitInputStream(in);
        BitOutputStream outStream = new BitOutputStream(out);
        // runs master compress method (which writes compressed data to a new file)
        return c.encode(inStream, outStream);
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
        // initializes input (read) and output (write) streams
        BitInputStream inStream = new BitInputStream(in);
        BitOutputStream outStream = new BitOutputStream(out);

        // checks if the first integer read is the magic number
        int magic = inStream.readBits(BITS_PER_INT);
        // if not, show error and do not compress
        if (magic != MAGIC_NUMBER) {
            myViewer.showError("Error reading compressed file. \n" +
                    "File did not start with the huff magic number.");
            inStream.close();
            outStream.close();
            return -1;
        }
        // initializes decompressor object
        Decompressor d = new Decompressor(inStream, outStream);
        // runs master decompress method (which writes decompressed data to a new file)
        int num = d.decode(inStream, outStream);
        return num;
    }

    public void setViewer(IHuffViewer viewer) {
        myViewer = viewer;
    }
}

