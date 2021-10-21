package client;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RequestHandler {
    Scanner s;
    PrintWriter output;
    public static final String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "client" + File.separator + "data" + File.separator;

    public RequestHandler(Scanner s, PrintWriter output) {
        this.s = s;
        this.output = output;
    }


    synchronized void requestAndProcessResponse(String req) {
        int inp = Integer.parseInt(req);

        switch (inp) {
            case 1 : {
                System.out.println("Do you want to get the file by name or by id (1 - name, 2 - id): ");
                getFileNameOrIdAndRequest(inp);
                break;
            }

            case 2 : {
                System.out.println("Enter name of the file:");
                String name = s.nextLine();
                System.out.println("Enter name of the file to be saved on server:");
                String fileInfo = s.nextLine();
                output.println(String.format("%s %s %s",inp, name, fileInfo));
                break;
            }

            case 3 : {
                System.out.println("Do you want to delete the file by name or by id (1 - name, 2 - id): ");
                getFileNameOrIdAndRequest(inp);
            }
        }

        System.out.println("The request was sent.");
    }

    private void getFileNameOrIdAndRequest(int inp) {
        int type = s.nextInt();
        s.nextLine();
        if(type == 1) {
            System.out.println("Enter name of the file:");
            String name = s.nextLine();
            output.println(String.format("%s %s %s",inp, type, name));
        } else {
            System.out.println("Enter id: ");
            int id = s.nextInt();
            output.println(String.format("%s %s %s",inp, type, id));
        }
    }


}
