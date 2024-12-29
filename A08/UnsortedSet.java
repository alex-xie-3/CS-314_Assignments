/*  Student information for assignment:
 *
 *  On OUR honor, Andrew and Alex,
 *  this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: adn2225
 *  email address: adn2225@my.utexas.edu
 *  TA name: Eliza Bidwell
 *  
 *  Student 2 
 *  UTEID: ayx72
 *  email address: alex.xie@utexas.edu
 */

import java.util.Iterator;
import java.util.ArrayList;

/**
 * A simple implementation of an ISet. 
 * Elements are not in any particular order.
 * Students are to implement methods that 
 * were not implemented in AbstractSet and override
 * methods that can be done more efficiently. 
 * An ArrayList must be used as the internal storage container.
 *
 */
public class UnsortedSet<E> extends AbstractSet<E> {

    private ArrayList<E> myCon;

    // O(1)
    public UnsortedSet() {
        myCon = new ArrayList<>();
    }

    // O(N)
    public boolean add(E item) {
        // check precondition
        if (item == null) {
            throw new IllegalArgumentException("Argument item may not be null.");
        }

        if (!contains(item)) {
            myCon.add(item);
            return true;
        }
        return false;
    }

    // O(N^2)
    public boolean addAll(ISet<E> otherSet) {
        // check precondition
        if (otherSet == null) {
            throw new IllegalArgumentException("Argument otherSet may not be null.");
        }

        int oldSize = size();
        for (E elem : otherSet) {
            add(elem);
        }
        return oldSize != size();
    }

    // O(N^2)
    public ISet<E> intersection(ISet<E> otherSet) {
        // check precondition
        if (otherSet == null) {
            throw new IllegalArgumentException("Argument otherSet may not be null.");
        }

        ISet<E> result = new UnsortedSet<>();
        ISet<E> small = size() < otherSet.size() ? this : otherSet;
        ISet<E> big = small == this ? otherSet : this;
        for (E elem : small) {
            if (big.contains(elem)) {
                result.add(elem);
            }
        }
        return result;
    }

    // O(1)
    public Iterator<E> iterator() {
        return myCon.iterator();
    }

    // O(1)
    public int size() {
        return myCon.size();
    }

    // O(N^2)
    public ISet<E> union(ISet<E> otherSet) {
        // check precondition
        if (otherSet == null) {
            throw new IllegalArgumentException("Argument otherSet may not be null.");
        }

        ISet<E> result = new UnsortedSet<>();
        result.addAll(this);
        result.addAll(otherSet);
        return result;
    }

}
