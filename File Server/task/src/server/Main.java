package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    public static void main(String[] args) throws IOException {
        String address = "127.0.0.1";
        int port = 23456;
        File file = new File(FileHandler.path);
        if (!file.exists() && !file.canWrite()) {
            file.mkdirs();
        }


        HashMap<String,String> fileList = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(FileHandler.pathSave);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            fileList = (HashMap<String, String>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            fileList = new HashMap<>();
        }


        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = server.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                FileHandler fileHandler = new FileHandler(out, fileList);

                    String command = in.readLine();
                    if (command != null) {
                        if(command.equals("exit")) {
                            // try catch block
                            try {
                                FileOutputStream myFileOutStream
                                        = new FileOutputStream(FileHandler.pathSave);

                                ObjectOutputStream myObjectOutStream
                                        = new ObjectOutputStream(myFileOutStream);

                                myObjectOutStream.writeObject(fileList);

                                // closing FileOutputStream and
                                // ObjectOutputStream
                                myObjectOutStream.close();
                                myFileOutStream.close();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            Main.stopServer();
                            break;
                        } else
                            out.println(fileHandler.processCommands(command));
                    }
            }
        }
    }

    public static void stopServer(){
        System.exit(0);
    }
}