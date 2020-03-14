package concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) {
        ConsoleProgress consoleProgress = new ConsoleProgress();
        Thread consoleProgressThread = new Thread(consoleProgress);
        consoleProgressThread.start();
        System.out.println(String.format("\rThread %s started", consoleProgressThread.getName()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consoleProgressThread.interrupt();
    }

    @Override
    public void run() {
        String[] symbols = {"-", "\\", "|", "/"};
        int pos = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print(String.format("\rLoading: %s", symbols[pos]));
            pos = (pos == 3) ? 0 : pos + 1;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(String.format("\rThread %s interrupted", Thread.currentThread().getName()));
            }
        }
    }
}
