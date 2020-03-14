package concurrent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownload implements Runnable {
    //private static final String URL = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
    private static final String URL = "ftp://office.iteh.su/A-Ha - Take On Me.mpg";
    private static String argUrl;
    private static int speedLimit; // in KBytes / sec

    public static void main(String[] args) {
        argUrl = URL;
        if (args.length > 0 && !args[0].isEmpty()) {
            argUrl = args[0];
        }
        if (args.length == 2) {
            speedLimit = Integer.parseInt(args[1]);
        }
        FileDownload fileDownload = new FileDownload();
        Thread fileDownloadThread = new Thread(fileDownload);
        fileDownloadThread.start();

    }

    @Override
    public void run() {
        try {
            URL url = new URL(argUrl);
            try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(url.getFile())) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                int counter = 0;
                System.out.println(String.format("Staring download to %s", url.getFile()));
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    counter++;
                    try {
                        if (speedLimit > 0) {
                            Thread.sleep(1000 / speedLimit, 1000 % speedLimit);
                            System.out.print(String.format("\rDownloaded %s KB with speed %s Kb/s", counter, speedLimit));
                        } else {
                            System.out.print(String.format("\rDownloaded %s", counter));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
