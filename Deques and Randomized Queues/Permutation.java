import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        // StdOut.println(k);
        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randQueue.enqueue(s);
            // StdOut.println(s);
        }
        while (k > 0) {
            StdOut.println(randQueue.dequeue());
            k--;
        }
    }
}
