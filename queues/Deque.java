import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int nodeCount = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return nodeCount == 0;
    }

    // return the number of items on the deque
    public int size() {
        return nodeCount;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldFirst = first;
        first = new Node();
        first.value = item;

        first.next = oldFirst;

        if (nodeCount == 0) last = first;
        else oldFirst.previous = first;

        nodeCount++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.value = item;

        last.previous = oldLast;

        if (nodeCount == 0) first = last;
        else oldLast.next = last;

        nodeCount++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Node oldFirst = first;
        first = oldFirst.next;
        first.previous = null;

        return oldFirst.value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Node oldLast = last;
        last = oldLast.previous;
        last.next = null;

        return oldLast.value;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.value;
            current = current.next;
            return item;
        }
    }

    private class Node {
        Node next;
        Node previous;
        Item value;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.removeFirst();
    }
}