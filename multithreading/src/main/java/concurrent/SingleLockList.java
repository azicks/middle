package concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @SuppressWarnings("unchecked")
    @GuardedBy("this")
    private T[] data = (T[]) new Object[20];
    private int size = 0;

    public synchronized void add(T value) {
        if (size + 1 == data.length) {
            System.arraycopy(data, 0, data, 0, data.length + 20);
        }
        data[size++] = value;
    }

    public synchronized T get(int index) {
        return data[index];
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return new Iterator<>() {
            final int currentSize = size;
            @SuppressWarnings("unchecked")
            final T[] currentData = (T[]) new Object[currentSize];
            int position = 0;
            {
                System.arraycopy(data, 0, currentData, 0, currentSize);
            }

            @Override
            public boolean hasNext() {
                return position < currentSize;
            }

            @Override
            public synchronized T next() {
                return currentData[position++];
            }
        };
    }
}
