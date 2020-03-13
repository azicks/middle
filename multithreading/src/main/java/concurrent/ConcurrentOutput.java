package concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread first = new Thread(() ->
                System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(new SecondThread());
        first.start();
        second.start();
        System.out.println(Thread.currentThread().getName());
    }

    static class SecondThread implements Runnable {
        @Override
        public void run() {
            print();
        }

        void print() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
