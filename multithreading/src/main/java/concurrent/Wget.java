package concurrent;

public class Wget {
    static private int index;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (index = 0; index != 100; index++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while (thread.getState() != Thread.State.TERMINATED) {
            System.out.print("\rLoading : " + index + "%");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
