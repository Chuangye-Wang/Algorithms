import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        private Item item;
        private Node prev = null;
        private Node next = null;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("input cannot be null");
        }
        Node oldFirst = first;
        first = new Node();
        first.next = oldFirst;
        first.item = item;
        if (oldFirst == null) last = first;
        else oldFirst.prev = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("input cannot be null");
        }
        Node oldLast = last;
        last = new Node();
        last.prev = oldLast;
        last.item = item;
        if (oldLast == null) first = last;
        else oldLast.next = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item itemRemove;
        itemRemove = first.item;
        if (first.next != null) {
            Node newFirst = first.next;
            newFirst.prev = null;
            first = newFirst;
        }
        else {
            first = null;
            last = null;
        }
        size--;
        return itemRemove;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item itemRemove;
        itemRemove = last.item;
        if (last.prev != null) {
            Node newLast = last.prev;
            newLast.next = null;
            last = newLast;
        }
        else {
            first = null;
            last = null;
        }
        size--;
        return itemRemove;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext() { return current != null; }

        public void remove() { throw new UnsupportedOperationException(); }
        public Item next()
        {
            if (current == null) { throw new NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Iterator<Integer> intTest = Deque.iterator();
        Deque<Integer> s = new Deque<Integer>();
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {6, 7, 8, 9, 10};
        for (int i = 0; i < a.length; i++) {
            s.addFirst(a[i]);
            s.addLast(b[i]);
        }
        System.out.println("size = " + s.size());
        System.out.println("s = " + s);
        Deque<Integer>.Node node = s.first;
        Deque<Integer>.Node node1 = s.first;
        while (node != null) {
            System.out.println("item = " + node.item);
            node = node.next;
        }
        node1.item = s.removeFirst();
        System.out.println("first = " + s.first.item);
        System.out.println("firstNext = " + s.first.next.item);
        System.out.println("size = " + s.size);
        node1.item = s.removeLast();
        System.out.println("last = " + s.last.item);
        System.out.println("lastPrev = " + s.last.prev.item);
        System.out.println("size = " + s.size);
        // node = s.first;
        while (s.first != null) {
            System.out.println("item = " + s.first.item);
            s.removeFirst();
        }
        System.out.println("size = " + s.size);

        Deque<String> list = new Deque<String>();
        list.addFirst("A");
        list.addFirst("B");
        list.addFirst("C");
        list.addFirst("D");

        Iterator<String> iterator = list.iterator();
        // Iterator<String> list = Deque.iterator();

        System.out.println(list.size());
        StdOut.println(iterator.hasNext());

        while (iterator.hasNext())
            StdOut.println(iterator.next()+ " ");
    }
}
