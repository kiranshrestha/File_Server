package client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Thread for clients
 */
public class ThreadClient implements Runnable {

    private Socket socket;
    private BufferedReader cin;

    public ThreadClient(Socket socket) throws IOException {
        this.socket = socket;
        this.cin = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = cin.readLine();
                if(message!= null) {
                    if (message.contains("FILE")) {
                        message = message.replace("FILE", "");
                        System.out.println("The file was downloaded! Specify a name for it:");
                        Scanner scanner = new Scanner(System.in);
                        String filename = scanner.nextLine();
                        scanner.close();
                        File clientDownload = new File(RequestHandler.path + filename);
                        if(clientDownload.createNewFile()) {
                            try(PrintWriter printWriter = new PrintWriter(new FileWriter(clientDownload))) {
                                printWriter.write(message);
                            }
                        }
                        System.out.println("File saved on the hard drive!");
                    } else {
                        System.out.println(message);
                    }
                }
            }
        } catch (SocketException e) {

        } catch (IOException exception) {
            System.out.println(exception);
        }
    }
}
