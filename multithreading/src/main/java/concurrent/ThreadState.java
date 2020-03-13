package concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {

            }
        });
        System.out.println(String.format("Status of thread %s is %s", thread.getName(), thread.getState()));
        System.out.println(String.format("Status of thread %s is %s", secondThread.getName(), secondThread.getState()));
        System.out.println("//////////////////////////////////////");
        thread.start();
        secondThread.start();
        while (thread.getState() != Thread.State.TERMINATED || secondThread.getState() != Thread.State.TERMINATED) {
            System.out.println(String.format("Status of thread %s is %s", thread.getName(), thread.getState()));
            System.out.println(String.format("Status of thread %s is %s", secondThread.getName(), secondThread.getState()));
        }
        System.out.println("//////////////////////////////////////");
        System.out.println(String.format("Status of thread %s is %s", thread.getName(), thread.getState()));
        System.out.println(String.format("Status of thread %s is %s", secondThread.getName(), secondThread.getState()));
    }
}
