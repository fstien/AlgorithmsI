import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class LinkedStack {
        private Node first = null;

        private class Node {
            int item;
            Node next;
        }

        public boolean isEmpty() {
            return first == null;
        }

        public void push(int item) {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
        }

        public int pop() {
            int item = first.item;
            first = first.next;
            return item;
        }
    }



    private Item[] a;
    private int n = 0;
    private int used = 0;
    private LinkedStack free;

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[10];
        free = new LinkedStack();
    }

    // is the randomized queue empty?
    public boolean isEmpty() { return n == 0; }

    // return the number of items on the randomized queue
    public int size() { return n; }

    // add the item
    public void enqueue(Item item) {
        if(free.isEmpty()) {
            a[n] = item;
            used++;
        }
        else {
            int index = free.pop();
            a[index] = item;
        }
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        int index = StdRandom.uniform(used);

        while(a[index] == null) {
            index = StdRandom.uniform(used);
        }

        Item toReturn = a[index];
        a[index] = null;

        free.push(index);

        return toReturn;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int index = StdRandom.uniform(used);

        while(a[index] == null) {
            index = StdRandom.uniform(used);
        }

        return a[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        public boolean hasNext()

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next()

    }

    // unit testing (required)
    public static void main(String[] args)

}