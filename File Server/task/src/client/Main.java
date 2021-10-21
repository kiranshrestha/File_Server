package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        String command;
        String address = "127.0.0.1";
        int port = 23456;
        File file = new File(RequestHandler.path);
        if(!file.exists()) {
            file.mkdirs();

        }
        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             PrintWriter output = new PrintWriter(socket.getOutputStream(),true)
        ) {
            System.out.println("Client started!");
            RequestHandler handler = new RequestHandler(s, output);
            ThreadClient threadClient = new ThreadClient(socket);
            new Thread(threadClient).start();

             System.out.println("Enter action (1 - get a file, 2 - create a file, 3 - delete a file):");
                command = s.nextLine();
                if (command.equals("exit")) {
                    output.println("exit");
                    System.out.println("The request was sent");
                } else
                handler.requestAndProcessResponse(command);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
