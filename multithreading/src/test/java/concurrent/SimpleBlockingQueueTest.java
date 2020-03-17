package concurrent;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockingQueueTest {
    @Test
    public void test() throws InterruptedException {
        final SimpleBlockingQueue queue = new SimpleBlockingQueue<Integer>();
        final List<Integer> result = new ArrayList<>();
        Thread thread1 = new Thread(() -> {
            try {
                result.add((Integer) queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        result.add(1);
        Thread thread2 = new Thread(() -> {
            try {
                queue.offer(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertEquals(List.of(1, 2), result);
    }
}
