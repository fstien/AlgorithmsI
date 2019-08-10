import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int[] shuffle;
    private int n = 0;
    private int arrayLenght = 10;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[arrayLenght];
        shuffle = intArray(arrayLenght);
        StdRandom.shuffle(shuffle);
    }

    private int[] intArray(int c) {
        int[] r = new int[c];
        for (int i = 0; i < c; i++) {
            r[i] = i;
        }
        return r;
    }

    // is the randomized queue empty?
    public boolean isEmpty() { return n == 0; }

    // return the number of items on the randomized queue
    public int size() { return n; }

    // add the item
    public void enqueue(Item item) {
        if(item == null) throw new IllegalArgumentException();
        StdRandom.shuffle(shuffle, n, arrayLenght);
        array[shuffle[n]] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException();
        int i = StdRandom.uniform(n);
        Item toReturn = array[shuffle[i]];

        int iIndex = shuffle[i];
        int lastIndex = shuffle[n-1];

        shuffle[i] = lastIndex;
        shuffle[n-1] = iIndex;

        n--;
        return toReturn;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();
        return array[shuffle[StdRandom.uniform(n)]];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        StdRandom.shuffle(shuffle, 0, n);
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        private int index = 0;

        public boolean hasNext() { return index < n; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            Item toReturn = array[shuffle[index]];
            index++;
            return toReturn;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        System.out.println(rq.isEmpty());

        rq.enqueue("it");

        System.out.println(rq.isEmpty());

        rq.enqueue("was");
        rq.enqueue("the");
        rq.enqueue("best");
        rq.enqueue("of");
        rq.enqueue("times");

        System.out.println(rq.size());

        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());

        rq.enqueue("hello");
        rq.enqueue("world");
        rq.enqueue("Francois");

        for(String str : rq) {
            System.out.println(str);
        }
    }

}