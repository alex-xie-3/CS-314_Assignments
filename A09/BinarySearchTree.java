/*
* Student information for assignment:
*
*  On my honor, Alex Xie, this programming assignment is my own work
*  and I have not provided this code to any other student.
*
*  UTEID: ayx72
*  email address: alex.xie@utexas.edu
*  TA name: Eliza Bidwell
*  Number of slip days I am using: 0
*/

import java.util.ArrayList;
import java.util.List;

/**
 * Shell for a binary search tree class.
* @author scottm
* @param <E> The data type of the elements of this BinarySearchTree.
* Must implement Comparable or inherit from a class that implements
* Comparable.
*
*/
public class BinarySearchTree<E extends Comparable<? super E>> {

    private BSTNode<E> root;
    private int size;

    /**
     *  Add the specified item to this Binary Search Tree if it is not already present.
    *  <br>
    *  pre: <tt>value</tt> != null<br>
    *  post: Add value to this tree if not already present. Return true if this tree
    *  changed as a result of this method call, false otherwise.
    *  @param value the value to add to the tree
    *  @return false if an item equivalent to value is already present
    *  in the tree, return true if value is added to the tree and size() = old size() + 1
    */
    public boolean add(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        int oldSize = size;
        root = addHelper(value, root);
        // check if tree is modified
        return oldSize != size;
    }

    private BSTNode<E> addHelper(E value, BSTNode<E> node) {
        // fell off tree
        if (node == null) {
            size++;
            return new BSTNode<>(value);
        } else {
            // move to left subtree
            if (node.data.compareTo(value) > 0) {
                node.left = addHelper(value, node.left);
            // move to right subtree
            } else if (node.data.compareTo(value) < 0) {
                node.right = addHelper(value, node.right);
            }
            // if equal, do nothing
            return node;
        }
    }

    /**
     *  Remove a specified item from this Binary Search Tree if it is present.
    *  <br>
    *  pre: <tt>value</tt> != null<br>
    *  post: Remove value from the tree if present, return true if this tree
    *  changed as a result of this method call, false otherwise.
    *  @param value the value to remove from the tree if present
    *  @return false if value was not present
    *  returns true if value was present and size() = old size() - 1
    */
    public boolean remove(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        int oldSize = size;
        root = removeHelper(value, root);
        // check if tree was modified
        return oldSize != size;
    }

    private BSTNode<E> removeHelper(E value, BSTNode<E> node) {
        // keep null componenets as is
        if (node == null) {
            return null;
        }
        // move to left subtree
        if (node.data.compareTo(value) > 0) {
            node.left = removeHelper(value, node.left);
        // move to right subtree
        } else if (node.data.compareTo(value) < 0) {
            node.right = removeHelper(value, node.right);
        } else {
            size--;
            // Case 1: removing a leaf
            if (node.isLeaf()) {
                return null;
            // Case 2: removing root with single child
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            // Case 3: removing root with two children
            } else {
                // find largest element in left subtree to be new root                
                node.data = maxHelper(node.left);
                // remove existing element
                node.left = removeHelper(node.data, node.left);
                // counteract double subtracting for size
                size++;
            }
        }
        // if equal, do nothing
        return node;
    }

    /**
     *  Check to see if the specified element is in this Binary Search Tree.
    *  <br>
    *  pre: <tt>value</tt> != null<br>
    *  post: return true if value is present in tree, false otherwise
    *  @param value the value to look for in the tree
    *  @return true if value is present in this tree, false otherwise
    */
    public boolean isPresent(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        return isPresentHelper(value, root);
    }

    private boolean isPresentHelper(E value, BSTNode<E> node) {
        // if tree is empty or fell off tree 
        if (node == null) {
            return false;
        }
        // move to left substree
        if (node.data.compareTo(value) > 0) {
            return isPresentHelper(value, node.left);
        // move to right subtree
        } else if (node.data.compareTo(value) < 0) {
            return isPresentHelper(value, node.right);
        }
        // equal
        return true;
    }

    /**
     *  Return how many elements are in this Binary Search Tree.
    *  <br>
    *  pre: none<br>
    *  post: return the number of items in this tree
    *  @return the number of items in this Binary Search Tree
    */
    public int size() {
        return size;
    }

    /**
     *  return the height of this Binary Search Tree.
    *  <br>
    *  pre: none<br>
    *  post: return the height of this tree.
    *  If the tree is empty return -1, otherwise return the
    *  height of the tree
    *  @return the height of this tree or -1 if the tree is empty
    */
    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(BSTNode<E> node) {
        // empty tree
        if (node == null) {
            return -1;
        }
        // recursively add 1 to each level traversed, for all paths
        return 1 + Math.max(heightHelper(node.left), heightHelper(node.right));
    }

    /**
     *  Return a list of all the elements in this Binary Search Tree.
    *  <br>
    *  pre: none<br>
    *  post: return a List object with all data from the tree in ascending order.
    *  If the tree is empty return an empty List
    *  @return a List object with all data from the tree in sorted order
    *  if the tree is empty return an empty List
    */
    public List<E> getAll() {
        List<E> result = new ArrayList<>();
        // passes in by reference
        getAllHelper(root, result);
        return result;
    }

    private void getAllHelper(BSTNode<E> node, List<E> list) {
        // in-order traversal
        if (node != null) {
            getAllHelper(node.left, list);
            list.add(node.data);
            getAllHelper(node.right, list);
        }
    }

    /**
     * return the maximum value in this binary search tree.
    * <br>
    * pre: <tt>size()</tt> > 0<br>
    * post: return the largest value in this Binary Search Tree
    * @return the maximum value in this tree
    */
    public E max() {
        if (size() == 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        return maxHelper(root);
    }

    private E maxHelper(BSTNode<E> node) {
        // traverses to right-most node
        while (node.right != null) {
            node = node.right;
        }
        return node.data;
    }

    /**
     * return the minimum value in this binary search tree.
    * <br>
    * pre: <tt>size()</tt> > 0<br>
    * post: return the smallest value in this Binary Search Tree
    * @return the minimum value in this tree
    */
    public E min() {
        if (size() == 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        return minHelper(root);
    }

    private E minHelper(BSTNode<E> node) {
        // traverse to left-most node
        while (node.left != null) {
            node = node.left;
        }
        return node.data;
    }

    /**
     * An add method that implements the add algorithm iteratively 
    * instead of recursively.
    * <br>pre: data != null
    * <br>post: if data is not present add it to the tree, 
    * otherwise do nothing.
    * @param data the item to be added to this tree
    * @return true if data was not present before this call to add, 
    * false otherwise.
    */
    public boolean iterativeAdd(E data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        // empty tree
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
            return true;
        }

        // track node and preceding parent
        BSTNode<E> node = root;

        while (node != null) {
            // traverse left subtree
            if (node.data.compareTo(data) > 0) {
                if (node.left == null) {
                    node.left = new BSTNode<>(data);
                    size++;
                    return true;
                }
                node = node.left;
            // traverse right subtree
            } else if (node.data.compareTo(data) < 0) {
                if (node.right == null) {
                    node.right = new BSTNode<>(data);
                    size++;
                    return true;
                }
                node = node.right;
            // already added
            } else {
                return false;
            }
        }
        // choose which side of parent to assign added node
        size++;
        return true;
    }

    /**
     * Return the "kth" element in this Binary Search Tree. If kth = 0 the
    * smallest value (minimum) is returned.
    * If kth = 1 the second smallest value is returned, and so forth.
    * <br>pre: 0 <= kth < size()
    * @param kth indicates the rank of the element to get
    * @return the kth value in this Binary Search Tree
    */
    public E get(int kth) {
        if (kth < 0 || kth >= size()) {
            throw new IllegalArgumentException("Kth term must be from 0 to size-1");
        }
        // count is passed by reference
        return getHelper(kth, root, new int[]{0});
    }

    private E getHelper(int kth, BSTNode<E> node, int[] count) {
        if (node == null) {
            return null; // reached a leaf node
        }
    
        // search left subtree
        E left = getHelper(kth, node.left, count);
        if (left != null) {
            return left;
        }
    
        // check current node for correct index, k
        if (count[0] == kth) {
            return node.data;
        }
        // increment k
        count[0]++;
    
        // search right subtree
        return getHelper(kth, node.right, count);
    }

    /**
     * Return a List with all values in this Binary Search Tree 
    * that are less than the parameter <tt>value</tt>.
    * <tt>value</tt> != null<br>
    * @param value the cutoff value
    * @return a List with all values in this tree that are less than 
    * the parameter value. If there are no values in this tree less 
    * than value return an empty list. The elements of the list are 
    * in ascending order.
    */
    public List<E> getAllLessThan(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        List<E> result = new ArrayList<>();
        getAllLessThanHelper(value, root, result);
        return result;
    }

    private void getAllLessThanHelper(E value, BSTNode<E> node, List<E> list) {
        // only exectute if node hasn't fell off tree
        if (node != null) {
            if (node.data.compareTo(value) < 0) {
                // perform in order traversal
                getAllLessThanHelper(value, node.left, list);
                list.add(node.data);
                getAllLessThanHelper(value, node.right, list);
            // otherwise, travel left until condition is met 
            } else {
                getAllLessThanHelper(value, node.left, list);
            }
        }
    }

    /**
     * Return a List with all values in this Binary Search Tree 
    * that are greater than the parameter <tt>value</tt>.
    * <tt>value</tt> != null<br>
    * @param value the cutoff value
    * @return a List with all values in this tree that are greater
    *  than the parameter value. If there are no values in this tree
    * greater than value return an empty list. 
    * The elements of the list are in ascending order.
    */
    public List<E> getAllGreaterThan(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        List<E> result = new ArrayList<>();
        getAllGreaterThanHelper(value, root, result);
        return result;
    }

    private void getAllGreaterThanHelper(E value, BSTNode<E> node, List<E> list) {
        // only exectute if node hasn't fell off tree
        if (node != null) {
            if (node.data.compareTo(value) > 0) {
                // perform in order traversal
                getAllGreaterThanHelper(value, node.left, list);
                list.add(node.data);
                getAllGreaterThanHelper(value, node.right, list);
            // otherwise, travel right until condition is met 
            } else {
                getAllGreaterThanHelper(value, node.right, list);
            }
        }
    }

    /**
     * Find the number of nodes in this tree at the specified depth.
    * <br>pre: none
    * @param d The target depth.
    * @return The number of nodes in this tree at a depth equal to
    * the parameter d.
    */
    public int numNodesAtDepth(int d) {
        if (d < 0) {
            return 0;
        }
        return numNodesAtDepthHelper(d, root, 0);
    }

    public int numNodesAtDepthHelper(int d, BSTNode<E> node, int currD) {
        // fell off tree
        if (node == null) {
            return 0;
        }
        // add 1 if at correct depth
        if (currD == d) {
            return 1;
        }
        // traverse all possible paths
        return numNodesAtDepthHelper(d, node.left, currD + 1) + 
            numNodesAtDepthHelper(d, node.right, currD + 1);
    }

    /**
     * Prints a vertical representation of this tree.
    * The tree has been rotated counter clockwise 90
    * degrees. The root is on the left. Each node is printed
    * out on its own row. A node's children will not necessarily
    * be at the rows directly above and below a row. They will
    * be indented three spaces from the parent. Nodes indented the
    * same amount are at the same depth.
    * <br>pre: none
    */
    public void printTree() {
        printTree(root, "");
    }

    private void printTree(BSTNode<E> n, String spaces) {
        if(n != null){
            printTree(n.right, spaces + "  ");
            System.out.println(spaces + n.data);
            printTree(n.left, spaces + "  ");
        }
    }

    private static class BSTNode<E extends Comparable<? super E>> {
        private E data;
        private BSTNode<E> left;
        private BSTNode<E> right;

        private BSTNode() {
            this(null);
        }

        private BSTNode(E initValue) {
            this(null, initValue, null);
        }

        private BSTNode(BSTNode<E> initLeft,
                E initValue,
                BSTNode<E> initRight) {
            data = initValue;
            left = initLeft;
            right = initRight;
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }
    }
}