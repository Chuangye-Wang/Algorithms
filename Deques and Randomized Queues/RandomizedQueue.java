import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] randQueue;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randQueue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == randQueue.length) autoEnlarge();
        randQueue[n] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int randNum = StdRandom.uniform(0, n);
        Item item = randQueue[randNum];
        randQueue[randNum] = randQueue[n -1];
        randQueue[n -1] = null;
        n--;
        if (n > 0 && n == randQueue.length/4) autoShrink();
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int randNum = StdRandom.uniform(0, n);
        return randQueue[randNum];
    }

    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private Item[] randQueueNew;
        private int i = 0;

        public RandomArrayIterator() {
            copyQueue();
            StdRandom.shuffle(randQueueNew);
        }

        private void copyQueue() {
            randQueueNew = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                randQueueNew[j] = randQueue[j];
            }
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return randQueueNew[i++];
        }
    }

    // enlarge queue
    private void autoEnlarge() {
        resize(2* randQueue.length);
    }

    // shrink queue
    private void autoShrink() {
        resize(randQueue.length/2);
    }

    // adjust randQueue size
    private void resize(int n) {
        if (n <= this.n) throw new IllegalArgumentException();
        Item[] randQueueNew = (Item[]) new Object[n];
        for (int i = 0; i < this.n; i++) {
            randQueueNew[i] = randQueue[i];
        }
        randQueue = randQueueNew;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}
