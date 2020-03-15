package concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @SuppressWarnings("unchecked")
    @GuardedBy("this")
    private T[] data = (T[]) new Object[20];
    private int size = 0;
    private int position = 0;
    private int modCount = 0;

    public synchronized void add(T value) {
        if (size + 1 == data.length) {
            System.arraycopy(data, 0, data, 0, data.length + 20);
        }
        data[size++] = value;
        modCount++;
    }

    public synchronized T get(int index) {
        return data[position];
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return new Iterator<T>() {
            int currentMods = modCount;

            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public synchronized T next() {
                if (currentMods != modCount) {
                    throw new ConcurrentModificationException();
                }
                return data[position++];
            }
        };
    }
}
