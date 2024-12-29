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
 * In this implementation of the ISet interface the elements in the Set are
 * maintained in ascending order.
 *
 * The data type for E must be a type that implements Comparable.
 *
 * Implement methods that were not implemented in AbstractSet
 * and override methods that can be done more efficiently. An ArrayList must
 * be used as the internal storage container. For methods involving two set,
 * if that method can be done more efficiently if the other set is also a
 * SortedSet, then do so.
 */
public class SortedSet<E extends Comparable<? super E>> extends AbstractSet<E> {

    private ArrayList<E> myCon;

    /**
     * create an empty SortedSet
     */
    // O(1)
    public SortedSet() {
        myCon = new ArrayList<>();
    }

    /**
     * Create a copy of other that is sorted.<br>
     * @param other != null
     */
    // O(N)
    public SortedSet(ISet<E> other) {
        // check precondition
        if (other == null) {
            throw new IllegalArgumentException("Argument other may not be null.");
        }

        myCon = new ArrayList<>(other.size());
        addAll(other);
    }

    // O(N)
    public boolean add(E item) {
        // check precondition
        if (item == null) {
            throw new IllegalArgumentException("Argument item may not be null.");
        }

        int pos = bsearch(item, 0, size() - 1);
        if (pos < 0) {
            myCon.add(pos * -1 - 1, item);
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

        if (otherSet instanceof SortedSet) {
            return addAll((SortedSet<E>) otherSet);
        }

        int oldSize = size();
        for (E elem : otherSet) {
            add(elem);
        }
        return oldSize != size();
    }

    // O(N)
    private boolean addAll(SortedSet<E> otherSet) {
        int oldSize = size();
        merge(otherSet);
        return oldSize != size();
    }

    // O(log N)
    public boolean contains(E item) {
        // check precondition
        if (item == null) {
            throw new IllegalArgumentException("Argument item may not be null.");
        }

        return bsearch(item, 0, size() - 1) >= 0;
    }

    // starter method
    public boolean containsAll(ISet<E> otherSet) {
        if (otherSet instanceof SortedSet) {
            return containsAll((SortedSet<E>) otherSet);
        }
        return super.containsAll(otherSet);
    }

    // O(N)
    private boolean containsAll(SortedSet<E> otherSet) {
        if (otherSet.size() > size()) {
            return false;
        }

        Iterator<E> it = iterator();
        Iterator<E> otherIt = otherSet.iterator();
        E otherElem = otherIt.hasNext() ? otherIt.next() : null;

        while (otherElem != null) {
            // there's still elements in otherSet
            if (!it.hasNext()) {
                return false;
            }

            int compare = it.next().compareTo(otherElem);
            // check if the current otherSet element is contained
            if (compare == 0) {
                otherElem = otherIt.hasNext() ? otherIt.next() : null;
            // calling set "skipped" equivalent element of otherSet
            } else if (compare > 0) {
                return false;
            }
        }
        return true;
    }

    // starter method
    public ISet<E> difference(ISet<E> otherSet) {
        if (otherSet instanceof SortedSet) {
            return difference((SortedSet<E>) otherSet);
        }
        return super.difference(otherSet);
    }

    // O(N)
    private ISet<E> difference(SortedSet<E> otherSet) {
        SortedSet<E> result = new SortedSet<>();
        result.myCon.ensureCapacity(size());

        Iterator<E> it = iterator();
        Iterator<E> otherIt = otherSet.iterator();
        E elem = it.hasNext() ? it.next() : null;
        E otherElem = otherIt.hasNext() ? otherIt.next() : null;

        while (elem != null && otherElem != null) {
            int compare = elem.compareTo(otherElem);
            // these elements are guaranteed to not be in otherSet
            if (compare < 0) {
                result.myCon.add(elem);
                elem = it.hasNext() ? it.next() : null;
            // apply difference
            } else if (compare == 0) {
                elem = it.hasNext() ? it.next() : null;
                otherElem = otherIt.hasNext() ? otherIt.next() : null;
            // cannot reasonably assume otherElem is/is not in this set
            } else {
                otherElem = otherIt.hasNext() ? otherIt.next() : null;
            }
        }
        // add the remaining elements that are greater than the final element of otherSet
        while (elem != null) {
            result.myCon.add(elem);
            elem = it.hasNext() ? it.next() : null;
        }
        return result;
    }

    // starter method
    public boolean equals(Object other) {
        if (other instanceof SortedSet) {
            return equals((SortedSet<?>) other);
        }
        return super.equals(other);
    }

    // O(N)
    private boolean equals(SortedSet<?> otherSet) {
        if (otherSet.size() != size()) {
            return false;
        }

        Iterator<E> it = iterator();
        Iterator<?> otherIt = otherSet.iterator();
        while (it.hasNext()) {
            if (!it.next().equals(otherIt.next())) {
                return false;
            }
        }
        return true;
    }

    // O(N^2)
    public ISet<E> intersection(ISet<E> otherSet) {
        if (otherSet instanceof SortedSet) {
            return intersection((SortedSet<E>) otherSet);
        }

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

    // O(N)
    private ISet<E> intersection(SortedSet<E> otherSet) {
        SortedSet<E> result = new SortedSet<>();
        result.myCon.ensureCapacity(otherSet.size());

        Iterator<E> it = iterator();
        Iterator<E> otherIt = otherSet.iterator();
        E elem = it.hasNext() ? it.next() : null;
        E otherElem = otherIt.hasNext() ? otherIt.next() : null;

        while (elem != null && otherElem != null) {
            int compare = elem.compareTo(otherElem);
            if (compare == 0) {
                result.myCon.add(elem);
                elem = it.hasNext() ? it.next() : null;
                otherElem = otherIt.hasNext() ? otherIt.next() : null;
            } else if (compare < 0) {
                elem = it.hasNext() ? it.next() : null;
            } else {
                otherElem = otherIt.hasNext() ? otherIt.next() : null;
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

    /**
     * Return the smallest element in this SortedSet.
     * <br> pre: size() != 0
     *
     * @return the smallest element in this SortedSet.
     */
    // O(1)
    public E min() {
        // check precondition
        if (size() == 0) {
            throw new IllegalArgumentException("No elements in this SortedSet.");
        }

        return myCon.get(0);
    }

    /**
     * Return the largest element in this SortedSet.
     * <br> pre: size() != 0
     * @return the largest element in this SortedSet.
     */
    // O(1)
    public E max() {
        // check precondition
        if (size() == 0) {
            throw new IllegalArgumentException("No elements in this SortedSet.");
        }

        return myCon.get(size() - 1);
    }

    // if other SortedSet: O(N)
    // if other ISet: O(N^2)
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

    // O(log N)
    // code for binary search from class slides
    private int bsearch(E tgt, int low, int high) {
        if (low <= high) {
            int mid = low + ((high - low) / 2);
            int compare = myCon.get(mid).compareTo(tgt);
            if (compare == 0) {
                return mid;
            } else if (compare > 0) {
                return bsearch(tgt, low, mid - 1);
            } else {
                return bsearch(tgt, mid + 1, high);
            }
        }
        return -(low + 1);
    }

    // O(N)
    // code for merge from class slides
    private void merge(ISet<E> otherSet) {
        Iterator<E> it = iterator();
        Iterator<E> otherIt = otherSet.iterator();
        E elem = it.hasNext() ? it.next() : null;
        E otherElem = otherIt.hasNext() ? otherIt.next() : null;
        ArrayList<E> temp = new ArrayList<>(size() + otherSet.size());

        while (elem != null && otherElem != null) {
            int compare = elem.compareTo(otherElem);
            if (compare == 0) {
                temp.add(elem);
                elem = it.hasNext() ? it.next() : null;
                otherElem = otherIt.hasNext() ? otherIt.next() : null;
            } else if (compare < 0) {
                temp.add(elem);
                elem = it.hasNext() ? it.next() : null;
            } else {
                temp.add(otherElem);
                otherElem = otherIt.hasNext() ? otherIt.next() : null;
            }
        }
        while (elem != null) {
            temp.add(elem);
            elem = it.hasNext() ? it.next() : null;
        }
        while (otherElem != null) {
            temp.add(otherElem);
            otherElem = otherIt.hasNext() ? otherIt.next() : null;
        }
        myCon = temp;
    }
}
