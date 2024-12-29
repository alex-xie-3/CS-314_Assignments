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

public abstract class AbstractSet<E> implements ISet<E> {

    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    public boolean contains(E item) {
        // check precondition
        if (item == null) {
            throw new IllegalArgumentException("Argument item may not be null.");
        }

        for (E elem : this) {
            if (elem.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(ISet<E> otherSet) {
        // check precondition
        if (otherSet == null) {
            throw new IllegalArgumentException("Argument otherSet may not be null.");
        }

        if (otherSet.size() > size()) {
            return false;
        }

        for (E elem : otherSet) {
            if (!contains(elem)) {
                return false;
            }
        }
        return true;
    }

    public ISet<E> difference(ISet<E> otherSet) {
        // check precondition
        if (otherSet == null) {
            throw new IllegalArgumentException("Argument otherSet may not be null.");
        }

        ISet<E> result = union(otherSet);
        for (E elem : otherSet) {
            result.remove(elem);
        }
        return result;
    }

    public boolean equals(Object other) {
        if (!(other instanceof ISet)) {
            return false;
        }

        ISet<?> otherSet = (ISet<?>) other;
        if (size() != otherSet.size()) {
            return false;
        }

        for (E elem : this) {
            boolean contains = false;
            for (Object o : otherSet) {
                if (elem.equals(o)) {
                    contains = true;
                }
            }
            if (!contains) {
                return false;
            }
        }
        return true;
    }

    public boolean remove(E item) {
        // check precondition
        if (item == null) {
            throw new IllegalArgumentException("Argument item may not be null.");
        }

        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (item.equals(it.next())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public int size() {
        int count = 0;
        for (E elem : this) {
            count++;
        }
        return count;
    }

    /**
     * Return a String version of this set. 
     * Format is (e1, e2, ... en)
     * @return A String version of this set.
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        String seperator = ", ";
        result.append("(");

        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            result.append(it.next());
            result.append(seperator);
        }
        // get rid of extra separator
        if (this.size() > 0) {
            result.setLength(result.length() - seperator.length());
        }

        result.append(")");
        return result.toString();
    }
}
