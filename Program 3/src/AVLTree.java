// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AVLTree<E extends Comparable<? super E>> {
    /**
     * Construct the tree.
     */
    public AVLTree( ) {
        root = null;
    }

    /**
     * Insert into the tree.
     * @param item the item to insert.
     */
    public void insert(E item) {
        root = insert(item, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param item the item to remove.
     */
    public void remove(E item) {
        root = remove(item, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param item the item to remove.
     * @param node the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<E> remove(E item, AvlNode<E> node) {
        if( node == null )
            return node;   // Item not found; do nothing

        int compareResult = item.compareTo( node.element );

        if( compareResult < 0 )
            node.left = remove( item, node.left );
        else if( compareResult > 0 )
            node.right = remove( item, node.right );
        else if( node.left != null && node.right != null ) // Two children
        {
            node.element = findMin( node.right ).element;
            node.right = remove( node.element, node.right );
        }
        else
            node = ( node.left != null ) ? node.left : node.right;
        return balance( node );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public E findMin( ) {
        if(isEmpty())
            throw new RuntimeException( );
        return findMin( root ).element;
    }

    public void deleteMin() {
        root =  deleteMin(root);
     }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public E findMax( ) {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param item the item to search for.
     * @return true if x is found.
     */
    public boolean contains(E item) {
        return contains(item, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( ) {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( ) {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree(String label) {
        System.out.println(label);
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root,"" );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AvlNode<E> balance(AvlNode<E> node) {
        if(node == null )
            return node;

        if( height( node.left ) - height( node.right ) > ALLOWED_IMBALANCE )
            if( height( node.left.left ) >= height( node.left.right ) )
                node = rightRotation( node );
            else
                node = doubleRightRotation( node );
        else
        if( height( node.right ) - height( node.left ) > ALLOWED_IMBALANCE )
            if( height(node.right.right ) >= height( node.right.left ))
                node = leftRotation( node );
            else
                node = doubleLeftRotation( node );

        node.height = Math.max( height( node.left ), height( node.right ) ) + 1;
        return node;
    }

    public void checkBalance()                       {
        checkBalance(root);
    }

    private int checkBalance(AvlNode<E> node) {
        if( node == null )
            return -1;

        if( node != null ) {
            int hl = checkBalance( node.left );
            int hr = checkBalance( node.right );
            if( Math.abs( height( node.left ) - height( node.right ) ) > 1 ||
                    height( node.left ) != hl || height( node.right ) != hr )
                System.out.println( "\n\n***********************OOPS!!" );
        }

        return height( node );
    }


    /**
     * Internal method to insert into a subtree.  Duplicates are allowed
     * @param item the item to insert.
     * @param node the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<E> insert(E item, AvlNode<E> node) {
        if( node == null ) return new AvlNode<>( item, null, null );

        int compareResult = item.compareTo( node.element );

        if( compareResult < 0 )
            node.left = insert( item, node.left );
        else
            node.right = insert( item, node.right );

        return balance( node );
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param node the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<E> findMin(AvlNode<E> node) {
        if( node == null ) return node;

        while( node.left != null )
            node = node.left;
        return node;
    }

    /**
     * returns the new tree after the smallest node has been
     * deleted from the subtree rooted at node
     */
   private AvlNode<E> deleteMin(AvlNode<E> node) {

       if (node == null) {
           return null;
       }

       if (node.left != null) {
           node.left = deleteMin(node.left);
           return balance(node);
       } else {
           return node.right;
       }
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param node the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<E> findMax(AvlNode<E> node) {
        if( node == null )
            return node;

        while( node.right != null )
            node = node.right;
        return node;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param item is item to search for.
     * @param node the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains(E item, AvlNode<E> node) {
        while( node != null ) {
            int compareResult = item.compareTo( node.element );

            if( compareResult < 0 )
                node = node.left;
            else if( compareResult > 0 )
                node = node.right;
            else
                return true;    // Match
        }

        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param node the node that roots the tree.
     */
    private void printTree(AvlNode<E> node, String indent) {
        if( node != null ) {
            printTree( node.right, indent+"   " );
            System.out.println( indent+ node.element + "("+ node.height  +")" );
            printTree( node.left, indent+"   " );
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height(AvlNode<E> node) {
       if (node == null) return -1;

       return node.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<E> rightRotation(AvlNode<E> node) {
        AvlNode<E> theLeft = node.left;
        node.left = theLeft.right;
        theLeft.right = node;
        node.height = Math.max( height( node.left ), height( node.right ) ) + 1;
        theLeft.height = Math.max( height( theLeft.left ), node.height ) + 1;
        return theLeft;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<E> leftRotation(AvlNode<E> node) {
        AvlNode<E> theRight = node.right;
        node.right = theRight.left;
        theRight.left = node;
        node.height = Math.max( height( node.left ), height( node.right ) ) + 1;
        theRight.height = Math.max( height( theRight.right ), node.height ) + 1;
        return theRight;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<E> doubleRightRotation(AvlNode<E> node) {
        node.left = leftRotation( node.left );
        return rightRotation( node );
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<E> doubleLeftRotation(AvlNode<E> node) {
        node.right = rightRotation( node.right );
        return leftRotation( node );
    }

    private static class AvlNode<AnyType> {
        // Constructors
        public AvlNode( AnyType theElement ) {
            this( theElement, null, null );
        }

        public AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt) {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }

    /** The tree root. */
    private AvlNode<E> root;


    // Test program
    public static void main( String [ ] args ) {
        AVLTree<Integer> tree1 = new AVLTree<>();
        AVLTree<Dwarf> tree2 = new AVLTree<>();

        String[] nameList = {"Snowflake", "Sneezy", "Doc", "Grumpy", "Bashful", "Dopey", "Happy", "Doc", "Grumpy", "Bashful", "Doc", "Grumpy", "Bashful"};
        for (int i=0; i < nameList.length; i++)
            tree2.insert(new Dwarf(nameList[i]));

        tree2.printTree( "The Tree" );

        tree2.remove(new Dwarf("Bashful"));

        tree2.printTree( "The Tree after delete Bashful" );
        for (int i=0; i < 8; i++) {
            tree2.deleteMin();
            tree2.printTree( "\n\n The Tree after deleteMin" );
        }
    }

}
