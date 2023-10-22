// ******************ERRORS********************************
// Throws UnderflowException as appropriate


import org.w3c.dom.Node;
import java.util.ArrayList;
import java.util.Arrays;

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     *
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}

public class Tree<E extends Comparable<? super E>> {
    private BinaryNode<E> root;  // Root of tree
    private String treeName;     // Name of tree

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }
    /**
     * Create tree from list
     *
     * @param arr   List of elements
     * @param label Name of tree
     * @ordered true if want an ordered tree
     */
    public Tree(E[] arr, String label, boolean ordered) {
        treeName = label;
        if (ordered) {
            root = null;
            for (int i = 0; i < arr.length; i++) {
                bstInsert(arr[i]);
            }
        } else root = buildUnordered(arr, 0, arr.length - 1);
    }
    /**
     * Build a NON BST tree by inorder
     *
     * @param arr nodes to be added
     * @return new tree
     */
    private BinaryNode<E> buildUnordered(E[] arr, int low, int high) {
        if (low > high) return null;
        int mid = (low + high) / 2;
        BinaryNode<E> curr = new BinaryNode<>(arr[mid], null, null);
        curr.left = buildUnordered(arr, low, mid - 1);
        curr.right = buildUnordered(arr, mid + 1, high);
        return curr;
    }
    /**
     * Change name of tree
     *
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return the indented tree
     */

    public String toString() {
        if (root == null)
            return treeName + " Empty tree";
        else {
            return treeName + "\n" + this.toString(root, "");
        }
    }

    // Time complexity O(n)
    private String toString(BinaryNode<E> node, String indent) {
        // check if the node is null
        String finalString = "";

        if (node == null) {
            return "";
        }

        finalString += toString(node.right, indent + "  ");
        finalString += indent + node.element.toString() + "\n";

        finalString += toString(node.left, indent + "   ");

        // create a new string that will hold the values of the node with their respective indent
     return finalString;
    }

    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        return this.toString2(root);
    }

    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(??)
     * @param t the node that roots the subtree.
     */
    private String toString2(BinaryNode<E> t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }


    /**
     * The complexity of finding the deepest node is O(n)
     * @return
     */

    public E deepestNode() {
        return this.deepestNode(root, 0).getValue();
    }

    private NodeInfo<E> deepestNode(BinaryNode<E> node, int currentLevel) {

        if (node == null) {
            return null;
        }

        if (node.right == null && node.left == null) {
            return new NodeInfo<E>(node.element, currentLevel);
        }

        if (node.right != null && node.left == null) {
            return deepestNode(node.right, currentLevel + 1);
        }

        if (node.left != null && node.right == null) {
            return deepestNode(node.left, currentLevel + 1);
        }

        NodeInfo<E> rightInfo = deepestNode(node.right, currentLevel + 1);
        NodeInfo<E> leftInfo = deepestNode(node.left, currentLevel + 1);

        if (rightInfo.getLevel() > leftInfo.getLevel()) {
            return rightInfo;
        }
        return leftInfo;
    }


    /**
     * The complexity of finding the flip is O(???)
     * reverse left and right children recursively
     */
    public void flip() {
        this.flip(root);
    }

    // Time complexity O(n)

    private void flip(BinaryNode<E> node) {

        if (node == null) {
            return;
        }

        BinaryNode<E> temp = node.left;
        node.left = node.right;
        node.right = temp;
        flip(node.right);
        flip(node.left);
    }

    /**
     * Counts number of nodes in specified level
     * The complexity of nodesInLevel is O(???)
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */

    public int nodesInLevel(int maxLevel) {
        return nodesInLevel(root, 0, maxLevel);
    }


    // Time complexity O(log n)
    private int nodesInLevel(BinaryNode<E> node, int currentPos, int maxLevel) {

        // create two variables that track the amount of nodes at a certain level
        if (node == null) {
            return 0;
        }

        if (currentPos == maxLevel) {
            return 1;
        }

        return nodesInLevel(node.left, currentPos + 1, maxLevel) + nodesInLevel(node.right, currentPos + 1, maxLevel);

    }

    /**
     * Print all paths from root to leaves
     * The complexity of printAllPaths is O(log n)
     */
    public void printAllPaths() {
       this.printAllPaths(root, "");
    }


    private void printAllPaths(BinaryNode<E> node, String pathsSoFar) {
        
        if (node == null) {
            pathsSoFar = "";
        }

        if (node.left != null) {
            printAllPaths(node.left, pathsSoFar + " " + node.element);
        }

        if (node.right != null) {
            printAllPaths(node.right, pathsSoFar + " " + node.element);
        }

        // add all the contents to the array
        if (node.right == null && node.left == null) {
            // we hit the leaf node then return the final list
            System.out.print(pathsSoFar + " " + node.element + "\n");
        }

    }
    /**
     * Counts all non-null binary search trees embedded in tree
     *  The complexity of countBST is O(???)
     * @return Count of embedded binary search trees
     */
    public Integer countBST() {
        if (root == null) return 0;
        return countBST(root);
    }
    // Time complexity is O(n)
    private Integer countBST(BinaryNode<E> node) {

        if (node == null) {
            return 0;
        }

        if (node.right == null && node.left == null) {
            return 1;
        }

        else if (node.right != null && node.left == null) {
            return countBST(node.right);
        }

        else if (node.left != null && node.right == null) {
            return countBST(node.left);
        }
        // check if the format of the bst is the same then, increment the count
        else {
            if (node.element.compareTo(node.left.element) < 0 && node.element.compareTo(node.right.element) > 0) {
                return countBST(node.left) + countBST(node.right);
            }
            return 1 + countBST(node.left) + countBST(node.right);
        }

    }

    /**
     * Insert into a bst tree; duplicates are allowed
     * The complexity of bstInsert depends on the tree.  If it is balanced the complexity is O(log n)
     * @param x the item to insert.
     */
    public void bstInsert(E x) {

        root = bstInsert(x, root);
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */

    private BinaryNode<E> bstInsert(E x, BinaryNode<E> t) {
        if (t == null)
            return new BinaryNode<E>(x, null, null);
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = bstInsert(x, t.left);
        } else {
            t.right = bstInsert(x, t.right);
        }
        return t;
    }

    /**
     * Determines if item is in tree
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(E item) {
        return contains(item, root);
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(E x, BinaryNode<E> t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);
        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }

    /**
     * Remove all paths from tree that sum to less than given value
     * @param sum: minimum path sum allowed in final tree
     */

    // Time complexity is O(log n)
    public void pruneK(Integer sum) {
        pruneK((BinaryNode<Integer>) root, sum);
    }

    private BinaryNode<Integer> pruneK(BinaryNode<Integer> node, int sum) {

        if (node == null) {
            return null;
        }

        if (node.element >= sum) {
            return node;
        }

        if (node.left != null) {
            node.left = pruneK(node.left, sum - node.element);
        }

        // if the left node from root totals to a value less than the sum, remove
        if (node.right != null) {
            node.right = pruneK(node.right, sum - node.element);
        }

      if (node.left == null && node.right == null) {
          return null;
      }
      return node;
    }

    /**
     * Build tree given inOrder and preOrder traversals.  Each value is unique
     * @param inOrder  List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     */
    public void buildTreeTraversals(E[] inOrder, E[] preOrder) {
        root = buildTreeTraversals(inOrder, preOrder, 0, preOrder.length - 1, 0, inOrder.length - 1);
    }

    // Time complexity O(n)
    private BinaryNode<E> buildTreeTraversals(E[] inorder, E[] preOrder, int beginningPre, int endingPre, int beginningIn, int endingIn) {

        if (beginningIn > endingIn) {
            return null;
        }

        if (beginningIn == endingIn) {
            return new BinaryNode<>(inorder[beginningIn]);
        }

        BinaryNode<E> myRoot = new BinaryNode<>(preOrder[beginningPre]);

        int mid = Arrays.asList(inorder).indexOf(preOrder[beginningPre]);

        // Inorder
        int inOrderLeftSideLength = mid - beginningIn;

        myRoot.left = buildTreeTraversals(inorder, preOrder, beginningPre + 1, inOrderLeftSideLength + beginningPre, beginningIn, mid - 1);
        myRoot.right = buildTreeTraversals(inorder, preOrder, inOrderLeftSideLength + beginningPre + 1, endingPre, mid + 1, endingIn);

        return myRoot;

    }

    /**
     * Find the least common ancestor of two nodes
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */

    public String lca(E a, E b){
        BinaryNode<E> ancestor = null;
        if (a.compareTo(b) < 0) {
            ancestor = lca(root, a, b, ancestor);
        } else {
            ancestor = lca(root, b, a, ancestor);
        }
        if (ancestor == null) return "none";
        else return ancestor.toString();
    }

    // Time complexity O(log n)
    private BinaryNode<E> lca(BinaryNode<E> node, E node1, E node2, BinaryNode<E> currentAncestor) {

        if (node == null) {
            return null;
        }

        if (currentAncestor != null && node1.compareTo(currentAncestor.element) >= 0 && node2.compareTo(currentAncestor.element) <= 0) {
            return currentAncestor;
        }

        if (currentAncestor != null && node2.compareTo(currentAncestor.element) >= 0 && node1.compareTo(currentAncestor.element) <= 0) {
            return currentAncestor;
        }

        if (node1 == node2) {
            return new BinaryNode<>(node1);
        }

        if (node1.compareTo(node.element) <= 0 && node2.compareTo(node.element) <= 0) {
            return lca(node.left, node1, node2, node);
        }

        if (node1.compareTo(node.element) >= 0 && node2.compareTo(node.element) >= 0) {
            return lca(node.right, node1, node2, node);
        }

        return node;
    }

    /**
     * Balance the tree O(n)
     */

    public void balanceTree() {

        ArrayList<BinaryNode<E>> list = new ArrayList<>();
        getTraversal(root, list);

        root = balanceTree(list, 0, list.size() - 1);
    }


    // Time complexity O(n)
    private void getTraversal(BinaryNode<E> node, ArrayList<BinaryNode<E>> list) {
        if (node == null) {
            return;
        }
        getTraversal(node.left, list);
        list.add(node);
        getTraversal(node.right, list);
    }

    private BinaryNode<E> balanceTree(ArrayList<BinaryNode<E>> list, int low, int high) {
        int mid = (high + low) / 2;

        BinaryNode<E> tempRoot = list.get(mid);

        if (low == high) {
            tempRoot.left = null;
            tempRoot.right = null;
            return tempRoot;
        }

        if (low > high) {
            return null;
        }

        tempRoot.left = balanceTree(list, low, mid - 1);
        tempRoot.right = balanceTree(list, mid + 1, high);

        return tempRoot;
    }

    /**
     * In a BST, keep only nodes between range
     *
     * @param a lowest value
     * @param b highest value
     */
    public void keepRange(E a, E b) {
        root = keepRange(root, a, b, "");
    }

    //Time complexity is  O(log n)

    private BinaryNode<E> keepRange(BinaryNode<E> node, E low, E high, String path) {

        if (node == null) {
            return null;
        }

        if (node.left != null && node.element.compareTo(low) < 0 || node.element.compareTo(high) > 0) {
            keepRange(node.left, low, high, path + " " + node.element);
        }
        if (node.right != null && node.element.compareTo(low) > 0 || node.element.compareTo(high) < 0) {
            keepRange(node.left, low, high, path + " " + node.element);
        }

        return node;

    }

    // Basic node stored in unbalanced binary  trees
    private static class BinaryNode<E> {
        E element;            // The data in the node
        BinaryNode<E> left;   // Left child
        BinaryNode<E> right;  // Right child

        // Constructors
        BinaryNode(E theElement) {
            this(theElement, null, null);
        }

        BinaryNode(E theElement, BinaryNode<E> lt, BinaryNode<E> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        // toString for BinaryNode
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(element);
            return sb.toString();
        }

    }

}
