
public class LinkedList<E> {
    private Node<E> head, tail;
    private int size;
    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> n) {
            this.element = element;
            this.next = n;
        }

        public Node(E element) {
            this(element, null);
        }

    }
    public void insert(E value) {
        if (this.tail == null) {
            this.tail = new Node<E>(value);
            this.head = this.tail;
        } else {
            this.tail.next = new Node<E>(value);
            this.tail = tail.next;
        }
    }
    public E delete() {
        if (head == null) {
            return null;
        }
        E memory = head.element;
        head = head.next;

        if (head == null) {
            tail = null;
        }
        return memory;
    }

    public void printContents() {
        Node<E> current = head;
        while (current != null) {
            System.out.println(current.element);
            current = current.next;
        }
    }
}
