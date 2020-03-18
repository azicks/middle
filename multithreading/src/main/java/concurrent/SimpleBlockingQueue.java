package concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    public static final int MAX_SIZE = 20;

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == MAX_SIZE) {
                wait();
        }
        notify();
        queue.add(value);
    }

    public synchronized T poll() throws InterruptedException {
        while (this.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        notify();
        return result;
    }

    public boolean isEmpty() {
        return queue.size() == 0;
    }
}
