package server;

import java.io.Serializable;

public class FileSerialNo implements Serializable {
    static int count = 0;

    public synchronized void incrementSno() {
        count++;
    }

    public synchronized String getSynchronizedCount() {
        return String.valueOf(count);
    }
}
