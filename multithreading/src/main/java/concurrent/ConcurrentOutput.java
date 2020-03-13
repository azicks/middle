package concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread first = new Thread(() ->
                System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(new SecondThread());
        first.start();
        second.start();
        Runnable third = new SecondThread();
        third.run();
        new Thread(third).start();
        Thread fourth = new AnotherThread();
        fourth.start();
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

    static class AnotherThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
