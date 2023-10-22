public class BinarySearchTree<E extends Comparable<? super E>> {
    private BinaryNode<E> root;

    public static class BinaryNode<E> {
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
    }

    private BinaryNode<E> insert(E item, BinaryNode<E> tree) {
        if (tree == null) {
            return new BinaryNode<>(item, null, null);
        }

        int compareResult = item.compareTo(tree.element);

        if (compareResult < 0) {
            tree.left = insert(item, tree.left);
        } else if (compareResult > 0) {
            tree.right = insert(item, tree.right);
        }

        return tree;
    }

    public BinarySearchTree() {
        root = null;
    }

    public void insert(E x) {
        root = insert(x, root);
    }

    public void remove(E x) {
        root = remove(x, root);
    }

    public E findMin() {
        if (isEmpty()) System.out.println("Error");

        return findMin(root).element;
    }

    public E findMax() {
        if (isEmpty()) System.out.println("Error");
        return findMax(root).element;
    }

    public boolean search(E item) {
        return search(item, root);
    }

    public BinaryNode<E> getRoot() {
        return root;
    }

    public String toString2() {
        return this.toString2(root);
    }



    private String toString2(BinaryNode<E> t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }

    public String toString() {
        return this.toString(root, "");
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

    public boolean isEmpty() {
        return root == null;
    }

    private BinaryNode<E> remove(E item, BinaryNode<E> tree) {
        if (tree == null)
            return tree;   // Item not found; do nothing

        int compareResult = item.compareTo(tree.element);

        if (compareResult < 0) tree.left = remove(item, tree.left);

        else if (compareResult > 0) tree.right = remove(item, tree.right);

        else if (tree.left != null && tree.right != null) {

            tree.element = findMin(tree.right).element;
            tree.right = remove(tree.element, tree.right);
        }
        else tree = (tree.left != null) ? tree.left : tree.right;
        return tree;
    }

    private BinaryNode<E> findMin(BinaryNode<E> node) {
        if (node == null) return null;
        else if (node.left == null) return node;
        return findMin(node.left);
    }

    private BinaryNode<E> findMax(BinaryNode<E> node) {
        if (node != null)
            while (node.right != null)
                node = node.right;

        return node;
    }

    private boolean search(E x, BinaryNode<E> t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return search(x, t.left);
        else if (compareResult > 0)
            return search(x, t.right);
        else
            return true;    // Match
    }



}