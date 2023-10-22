
public class LeftistHeap <E extends Comparable<? super E>> {
    private class LeftistNode<E> {
        E element;
        LeftistNode<E> left;
        LeftistNode<E> right;
        int nullPath;

        public LeftistNode(E theElement) {
            this(theElement, null, null);
        }

        public LeftistNode(E theElement, LeftistNode<E> left, LeftistNode<E> right) {
            this.element = theElement;
            this.left = left;
            this.right = right;
            this.nullPath = 0;
        }
    }

    private LeftistNode<E> root;

    public LeftistHeap() {
        root = null;
    }

    public void insert(E item) {
        root = merge(new LeftistNode<>(item), root); // create a new node and set the root as the newly inserted item, then merge
    }

    //Merge rhs into the priority queue.
    public void merge(LeftistHeap<E> rhs) {
        if (this == rhs) {
            return;
        }
        root = merge(root, rhs.root);
        rhs.root = null;
    }

    // Internal method to merge two roots.
    private LeftistNode<E> merge(LeftistNode<E> heap1, LeftistNode<E> heap2) {
        if( heap1 == null ) return heap2;
        if( heap2 == null ) return heap1;
        if( heap1.element.compareTo(heap2.element) > 0) return mergeTwoRoots(heap1, heap2);
        else return mergeTwoRoots(heap2, heap1);
    }

    //Internal method to merge two roots. Assumes trees are not empty, and root1's root contains largest item
    private LeftistNode<E> mergeTwoRoots(LeftistNode<E> heap1, LeftistNode<E> heap2 ) {
        if( heap1.left == null )   // Single node
            heap1.left = heap2;       // Other fields in h1 already accurate
        else {
            heap1.right = merge( heap1.right, heap2 );
            if( heap1.left.nullPath < heap1.right.nullPath ) swapChildren(heap1);
            heap1.nullPath = heap1.right.nullPath + 1;
        }
        return heap1;
    }

    /**
     * Swaps t's two children.
     */
    private void swapChildren(LeftistNode<E> leftNode) {
        LeftistNode<E> temporary = leftNode.left;
        leftNode.left = leftNode.right;
        leftNode.right = temporary;
    }

    public E deleteMax() {
        if(isEmpty()) System.out.println("Error");

        E maxItem = root.element;
        root = merge( root.left, root.right );

        return maxItem;
    }

    public void printHeap() {
        while (!this.isEmpty()) {
            E item = deleteMax();
            System.out.println(item);
        }
    }


    public E findMax() {
        if (isEmpty()) {
            System.out.println("Error: The Heap is empty");
        }
        return root.element;
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }
    public static void main( String [ ] args ) {
        int numItems = 100;
        LeftistHeap<Integer> h  = new LeftistHeap<>( );
        LeftistHeap<Integer> h1 = new LeftistHeap<>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            if( i % 2 == 0 )
                h1.insert(i);
            else
                h.insert(i);

        h.merge( h1 );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMax() != i )
                System.out.println( "Oops! " + i );
    }
}


