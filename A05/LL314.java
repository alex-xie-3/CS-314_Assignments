/*
 * Student information for assignment: 
 * On my honor, Alex Xie, this programming assignment is my own work 
 * and I have not provided this code to any other student. 
 * UTEID: ayx72
 * email address: alex.xie@utexas.edu
 * Number of slip days I am using: 1
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LL314<E> implements IList<E> {

    private final DoubleListNode<E> HEADER;
    private int size;
 
    public LL314() {
        HEADER = new DoubleListNode<>();
        HEADER.prev = HEADER;
        HEADER.next = HEADER;
        size = 0;
    }

    /**
    * return an Iterator for this list.
    * <br>pre: none
    * <br>post: return an Iterator object for this List
    * Big O: O(1)
    */
    public Iterator<E> iterator() {
        return new LLIterator();
    }

    /**
     * Private class for iterating through a linked list.
     */
    private class LLIterator implements Iterator<E> {
        
        private DoubleListNode<E> current;
        private boolean canRemove;
        
        /**
         * Create a new LLIterator object that implements the methods from the
         * Iterator interface and some extra helpful methods useful for traversing
         * a circular doubly linked list.
         */
        private LLIterator(){
            current = HEADER.next;
            canRemove = false;
        }

        /**
         * Returns true if next iteration exists
         * @return true is next iteration exists, false otherwise
         */
        @Override
        public boolean hasNext(){
            return current != HEADER;
        }
        
        /**
         * Returns next element's data in iteration
         * @return the next element's data in the iteration
         */
        @Override
        public E next(){
            if (!hasNext()) {
                throw new NoSuchElementException("No nodes left to iterate through.");
            }
            canRemove = true;
            E data = current.data;
            current = current.next;
            return data;
        }

        /**
         * Removes the element most recently returned by next
         * pre: canRemove
         */
        @Override
        public void remove() {
            if (!canRemove) {
                throw new NoSuchElementException("No nodes to remove");
            }
            removeThis(current.prev);
            canRemove = false;
        }
    }

    /**
     * Add an item to the end of this list.
     * <br>pre: item != null
     * <br>post: size() = old size() + 1, get(size() - 1) = item
     * @param item the data to be added to the end of this list,
     * item != null
     * Big O(n): O(1)
     */
    public void add(E item) {
        addLast(item);
    }

    /**
     * Insert an item at a specified position in the list.
     * <br>pre: 0 <= pos <= size(), item != null
     * <br>post: size() = old size() + 1, get(pos) = item,
     * all elements in the list with a positon >= pos have a
     * position = old position + 1
     * @param pos the position to insert the data at in the list
     * @param item the data to add to the list, item != null
     * Big O: O(n)
    */
    public void insert(int pos, E item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        // addLast is faster so handle edge case
        if (size == 0 || pos == size) {
            addLast(item);
        // same for addFirst
        } else if (pos == 0) {
            addFirst(item);
        } else {
            // otherwise insert accordingly
            DoubleListNode<E> oldNode = getThis(pos);
            DoubleListNode<E> newNode = new DoubleListNode<E>(oldNode.prev, item, oldNode);
            oldNode.prev.next = newNode;
            oldNode.prev = newNode;
            size++;
        }
    }

    /**
     * Change the data at the specified position in the list.
     * the old data at that position is returned.
     * <br>pre: 0 <= pos < size(), item != null
     * <br>post: get(pos) = item, return the
     * old get(pos)
     * @param pos the position in the list to overwrite
     * @param item the new item that will overwrite the old item,
     * item != null
     * @return the old data at the specified position
     * Big O: O(n)
     */
    public E set(int pos, E item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        DoubleListNode<E> oldNode = getThis(pos);
        // stores old data to be returned
        E oldData = oldNode.data;
        // sets data to new item
        oldNode.data = item;
        return oldData;
    }

    /**
     * Get an element from the list.
     * <br>pre: 0 <= pos < size()
     * <br>post: return the item at pos
     * @param pos specifies which element to get
     * @return the element at the specified position in the list
     * Big O: O(n)
     */
    public E get(int pos) {
        return getThis(pos).data;
    }

    /**
     * Remove an element in the list based on position.
     * <br>pre: 0 <= pos < size()
     * <br>post: size() = old size() - 1, all elements of
     * list with a position > pos have a position = old position - 1
     * @param pos the position of the element to remove from the list
     * @return the data at position pos
     * Big O: O(n)
     */
    public E remove(int pos) {
        DoubleListNode<E> curr = getThis(pos);
        return removeThis(curr);
    }

    /**
     * Remove the first occurrence of obj in this list.
     * Return <tt>true</tt> if this list changed
     * as a result of this call, <tt>false</tt> otherwise.
     * <br>pre: obj != null
     * <br>post: if obj is in this list the first occurrence
     * has been removed and size() = old size() - 1.
     * If obj is not present the list is not altered in any way.
     * @param obj The item to remove from this list. obj != null
     * @return Return <tt>true</tt> if this list changed
     * as a result of this call, <tt>false</tt> otherwise.
     * Big O: O(n)
     */
    public boolean remove(E obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null.");
        }
        DoubleListNode<E> curr = HEADER.next;
        // iterates from start to remove first occurence
        for (int i = 0; i < size; i++) {
            if (curr.data.equals(obj)) {
                removeThis(curr);
                // if can remove, return true
                return true;
            }
            curr = curr.next;
        }
        // if cannot find or cannot remove return false
        return false;
    }

    /**
     * Return a sublist of elements in this list
     * from <tt>start</tt> inclusive to <tt>stop</tt> exclusive.
     * This list is not changed as a result of this call.
     * <br>pre: <tt>0 <= start <= size(), start <= stop <= size()</tt>
     * <br>post: return a list whose size is stop - start
     * and contains the elements at positions start through stop - 1
     * in this list.
     * @param start index of the first element of the sublist.
     * @param stop stop - 1 is the index of the last element
     * of the sublist.
     * @return a list with <tt>stop - start</tt> elements,
     * The elements are from positions <tt>start</tt> inclusive to
     * <tt>stop</tt> exclusive in this list.
     * If start == stop an empty list is returned.
     * Big O: O(n)
     */
    public IList<E> getSubList(int start, int stop) {
        if (start < 0 || start > stop || stop > size) {
            throw new IllegalArgumentException("Start and/or stop is invalid.");
        }
        IList<E> sublist = new LL314<>();
        DoubleListNode<E> curr = getThis(start);
        // adds to sublist from start to stop
        for (int i = start; i < stop; i++) {
            sublist.add(curr.data);
            curr = curr.next;
        }
        return sublist;
    }

    /**
     * Return the size of this list.
     * In other words the number of elements in this list.
     * <br>pre: none
     * <br>post: return the number of items in this list
     * @return the number of items in this list
     * Big O: O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Find the position of an element in the list.
     * <br>pre: item != null
     * <br>post: return the index of the first element equal to item
     * or -1 if item is not present
     * @param item the element to search for in the list. item != null
     * @return return the index of the first element equal to item
     * or a -1 if item is not present
     * Big O: O(n)
     */
    public int indexOf(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        DoubleListNode<E> curr = HEADER.next;
        // iterates from start to find first occurence
        for (int i = 0; i < size; i++) {
            if (curr.data.equals(item)) {
                // if data matches item, return index
                return i;
            }
            curr = curr.next;
        }
        // if cannot find, return -1
        return -1;
    }

    /**
     * Find the position of an element in the list starting
     * at a specified position.
     * <br>pre: 0 <= pos < size(), item != null
     * <br>post: return the index of the first element equal
     * to item starting at pos
     * or -1 if item is not present from position pos onward
     * @param item the element to search for in the list. Item != null
     * @param pos the position in the list to start searching from
     * @return starting from the specified position
     * return the index of the first element equal to item
     * or a -1 if item is not present between pos
     * and the end of the list
     * Big O: O(n)
     */
    public int indexOf(E item, int pos) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        DoubleListNode<E> curr = getThis(pos);
        // starts searching from pos
        for (int i = pos; i < size; i++) {
            // if data matches item, return index
            if (curr.data.equals(item)) {
                return i;
            }
            curr = curr.next;
        }
        // if cannot find, return -1
        return -1;
    }

    /**
     * return the list to an empty state.
     * <br>pre: none
     * <br>post: size() = 0
     * Big O: O(1)
     */
    public void makeEmpty() {
        if (size != 0) {
            // sets pointers of nodes connected to HEADER to be cleared
            HEADER.next.prev = null;
            HEADER.prev.next = null;
            // sets pointers of HEADER back to default
            HEADER.next = HEADER;
            HEADER.prev = HEADER;
            size = 0;
        }
    }

    /**
     * Remove all elements in this list from <tt>start</tt>
     * inclusive to <tt>stop</tt> exclusive.
     * <br>pre: <tt>0 <= start <= size(), start <= stop <= size()</tt>
     * <br>post: <tt>size() = old size() - (stop - start)</tt>
     * @param start position at beginning of range of elements
     * to be removed
     * @param stop stop - 1 is the position at the end
     * of the range of elements to be removed
     * Big O: O(n)
     */
    public void removeRange(int start, int stop) {
        if (start < 0 || start > stop || stop > size) {
            throw new IllegalArgumentException("Start and/or stop is invalid.");
        }
        DoubleListNode<E> curr = getThis(start);
        // removes from start to stop
        for (int i = start; i < stop; i++) {
            curr = curr.next;
            removeThis(curr.prev);
        }
    }

    /**
     * add item to the front of the list. <br>
     * pre: item != null <br>
     * post: size() = old size() + 1, get(0) = item
     * @param item the data to add to the front of this list
     * Big O: O(1)
    */
    public void addFirst(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        // set new first node
        DoubleListNode<E> newNode = new DoubleListNode<>(HEADER, item, HEADER.next);
        // set former first (second) node 
        HEADER.next.prev = newNode;
        // set HEADER
        HEADER.next = newNode;

        size++;
    }

    /**
     * add item to the end of the list. <br>
     * pre: item != null <br>
     * post: size() = old size() + 1, get(size() -1) = item
     * @param item the data to add to the end of this list
     * Big O: O(1)
    */
    public void addLast(E item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        // set new last node
        DoubleListNode<E> newNode = new DoubleListNode<>(HEADER.prev, item, HEADER);
        // set former last (second to last) item
        HEADER.prev.next = newNode;
        // set HEADER
        HEADER.prev = newNode;

        size++;
    }

    /**
     * remove and return the first element of this list. <br>
     * pre: size() > 0 <br>
     * post: size() = old size() - 1
     * @return the old first element of this list
     * Big O: O(1)
    */
    public E removeFirst() {
        return removeThis(HEADER.next);
    }

    /**
     * remove and return the last element of this list. <br>
     * pre: size() > 0 <br>
     * post: size() = old size() - 1
     * @return the old last element of this list
     * Big O: O(1)
    */
    public E removeLast() {
        return removeThis(HEADER.prev);
    }

    /**
     * Determine if this DLList is equal to other. Two
     * DLLists are equal if they contain the same elements
     * in the same order.
     * @return true if this IList is equal to other, false otherwise
     * Big O: O(1)
     */
    @Override
    public boolean equals(Object that) {
        if (that == null) { // check for null
            return false;
        }
        if (that instanceof IList) { // check same class
            return false;
        }
        LL314<?> other = (LL314<?>) that;
        if (this.size() != other.size()) { // check same size
            return false;
        }
        // compare each element in both lists
        Iterator<E> thisItr = this.iterator();
        Iterator<?> otherItr = other.iterator();
        while (thisItr.hasNext() && otherItr.hasNext()) {
            E thisElement = thisItr.next();
            Object otherElement = otherItr.next();
            if (!thisElement.equals(otherElement)) {
                return false; // return false immediately if not equal
            }
        }
        return true;
    }

    /**
     * Return a String version of this list enclosed in
     * square brackets, []. Elements are in
     * are in order based on position in the
     * list with the first element
     * first. Adjacent elements are separated by comma's
     * @return a String representation of this IList
     * Big O: O(n)
     */
    @Override
    public String toString() {
        // edge cases
        if (size == 0) {
            return "[]";
        }
        // fence post traversal
        DoubleListNode<E> curr = HEADER.next;
        StringBuilder result = new StringBuilder("[" + curr.data);
        for (int i = 0; i < size - 1; i++) {
            curr = curr.next;
            result.append(", ");
            result.append(curr.data);
        }
        result.append("]");
        return result.toString();
    }

    /**
     * Helper method that gets the node at an index. Efficiently searches by
     * checking the given position and either iterating through the top half
     * or bottom half of the list to reach desired node.
     * pre: 0 <= pos < size()
     * @param pos index of node
     * @return node at given index
     */
    private DoubleListNode<E> getThis(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IllegalArgumentException("Index isn't in range.");
        }
        DoubleListNode<E> start = HEADER.next;
        // search in first half of list
        if (pos < size / 2) {
            for (int i = 0; i < pos; i++) {
                start = start.next;
            }
        // search in second half of list
        } else {
            start = HEADER.prev;
            for (int i = size - 1; i > pos; i--) {
                start = start.prev;
            }
        }
        return start;
    }

    /**
     * Helper method that sets surrounding nodes of 
     * a node to remove it and return its data.
     * @param curr node to be removed
     * @return data of removed node
     */
    private E removeThis(DoubleListNode<E> curr) {
        if (size == 0) {
            throw new IllegalArgumentException("List cannot be empty.");
        }
        E oldData = curr.data;
        // sets nodes to each side of removed node
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        // decrement size
        size--;
        return oldData;
    }

    /**
     * A class that represents a node to be used in a linked list.
    * These nodes are doubly linked. All methods are O(1).
    *
    * @author Mike Scott
    * @version 9/25/2023
    */

    private static class DoubleListNode<E> {

        // the data to store in this node
        private E data;

        // the link to the next node (presumably in a list)
        private DoubleListNode<E> next;

        // the reference to the previous node (presumably in a list)
        private DoubleListNode<E> prev;

        /**
         * default constructor.
        * <br>pre: none
        * <br>post: data = null, next = null, prev = null
        * <br>O(1)
        */
        public DoubleListNode() {
            this(null, null, null);
        }

        /**
         * create a DoubleListNode that holds the specified data
        * and refers to the specified next and previous elements.
        * <br>pre: none
        * <br>post: this.data = data, this.next = next, this.prev = prev
        * <br>O(1)
        * @param prev the previous node
        * @param data the  data this DoubleListNode should hold
        * @param next the next node
        */
        public DoubleListNode(DoubleListNode<E> prev, E data, DoubleListNode<E> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;    
        }
    }
}