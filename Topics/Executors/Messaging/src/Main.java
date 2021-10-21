import java.util.concurrent.*;

/* Do not change this class */
class Message {
    final String text;
    final String from;
    final String to;

    Message(String from, String to, String text) {
        this.text = text;
        this.from = from;
        this.to = to;
    }
}

/* Do not change this interface */
interface AsyncMessageSender {
    void sendMessages(Message[] messages);
    void stop();
}

class AsyncMessageSenderImpl implements AsyncMessageSender {
    private final ExecutorService executor;
    private final int repeatFactor;

    public AsyncMessageSenderImpl(int repeatFactor) {
        this.repeatFactor = repeatFactor;
        int poolSize = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void sendMessages(Message[] messages) {
        for (Message msg : messages) {
            executor.submit(() -> {
                for (int i = 0; i < repeatFactor; i++) {
                    System.out.printf("(%s>%s): %s\n", msg.from, msg.to, msg.text);
                }
            });
        }
    }

    @Override
    public void stop() {
        try {
            executor.shutdown();
            executor.awaitTermination(50,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}

