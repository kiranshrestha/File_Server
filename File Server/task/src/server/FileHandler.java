package server;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler implements Serializable{
    public static final String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "server" + File.separator + "data" + File.separator;
    public static final String pathSave = path + "saveInfo";

    HashMap<String,String> fileInfos;
    FileSerialNo serialNo;
    PrintWriter out;

    public FileHandler(PrintWriter out, HashMap<String, String> hashMap) {
        this.fileInfos = hashMap;
        serialNo = new FileSerialNo();
        this.out = out;
    }

    public String processCommands(String msg) {

        List<String> cmdList = List.of(msg.split(" "));

        switch (cmdList.get(0)) {

            case "1" : {
                File file;
                if(cmdList.get(1).equals("1")) {
                    String fileName = cmdList.get(2);
                    if(fileInfos.containsValue(fileName)) {
                        file = new File(path + fileName);
                    } else {
                        out.println("The response says that this file is not found!");
                        break;
                    }

                } else {
                    String id = cmdList.get(2);
                    if(fileInfos.containsKey(id)) {
                        file = new File(path + fileInfos.get(id));
                    } else {
                        out.println("The response says that this file is not found!");
                        break;
                    }
                }

                if (file.exists()) {
                    StringBuilder sb = new StringBuilder();
                    try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                        while (true) {
                            String line = bufferedReader.readLine();
                            if(line == null) {
                                break;
                            }
                            sb.append(line);
                        }
                        out.println("FILE" + sb);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    out.println("The response says that this file is not found!");
                }
                break;
            }

            case "2" : {
                String filename = cmdList.get(1);
                System.out.println(filename);
                File file;
                if(cmdList.size() > 2) {
                     file = new File(path + cmdList.get(2));
                } else {
                     file = new File(path + filename);
                }
                try {
                    if (file.createNewFile()) {
                        serialNo.incrementSno();
                        final String synchronizedCount = serialNo.getSynchronizedCount();
                        if(cmdList.size() > 2) {
                            fileInfos.put(synchronizedCount, cmdList.get(2));
                        } else {
                            fileInfos.put(synchronizedCount, filename);
                        }
                        try(FileWriter fileWriter = new FileWriter(file)) {
                                fileWriter.write("test" + synchronizedCount);
                        }
                        out.println("Response says that file is saved! ID = " + synchronizedCount);
                    } else {
                        System.out.println("File not created");
                        out.println("The response says that creating the file was forbidden!");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }

            case "3" : {

                File file = null;
                String fileId = "";
                if(cmdList.get(1).equals("1")) {
                    String fileName = cmdList.get(2);
                    if(fileInfos.containsValue(fileName)) {
                        for (Map.Entry<String, String> keySet: fileInfos.entrySet()){
                            if(keySet.getValue().equals(fileName)) {
                                fileId = keySet.getKey();
                                file = new File(path + fileName);
                            }
                        }
                    } else {
                        out.println("The response says that this file is not found!");
                        break;
                    }

                } else {
                    String id = cmdList.get(2);
                    if(fileInfos.containsKey(id)) {
                        fileId = id;
                        file = new File(path + fileInfos.get(id));
                    } else {
                        out.println("The response says that this file is not found!");
                        break;
                    }
                }

                if (file.delete()) {
                    fileInfos.remove(fileId);
                   out.println("The response says that the file was successfully deleted!");
                } else
                    out.println("The response says that this file is not found!");

            }
        }
        return "";
    }
}
