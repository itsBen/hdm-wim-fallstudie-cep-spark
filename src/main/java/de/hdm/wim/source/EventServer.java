package de.hdm.wim.source;

import com.google.gson.Gson;
import de.hdm.wim.resources.Event;
import de.hdm.wim.resources.Message;
import de.hdm.wim.resources.Participant;
import de.hdm.wim.streaming.SimpleStreaming;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by ben on 18/04/2017.
 */
public class EventServer {
    private static final Executor SERVER_EXECUTOR = Executors.newSingleThreadExecutor();
    private static final int PORT = 9999;
    private static final String DELIMITER = ":";
    private static final long EVENT_PERIOD_SECONDS = 1;
    private static final Random random = new Random();

    /**
     * The entry point of this application.
     *
     * @param args the input arguments
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        final Logger logger = Logger.getLogger(SimpleStreaming.class);

        BlockingQueue<String> eventQueue        = new ArrayBlockingQueue<>(1000);
        final Participant sender1               = new Participant("Jim",    "Barton",    "CEO");
        final Participant sender2               = new Participant("Mike",   "Obrut",     "PM");
        final ArrayList<Participant> senders    = new ArrayList<>();

        senders.add(sender1);
        senders.add(sender2);

        SERVER_EXECUTOR.execute(new SteamingServer(eventQueue));

        while (true) {
            MessageGenerator msgg   = new MessageGenerator(senders);
            Message message         = msgg.GenerateMessage();
            Gson gson               = new Gson();

            eventQueue.put(gson.toJson(message));
            Thread.sleep(TimeUnit.SECONDS.toMillis(EVENT_PERIOD_SECONDS));
        }
    }

    private static class SteamingServer implements Runnable {
        private final BlockingQueue<String> eventQueue;

        /**
         * Instantiates a new Steaming server.
         *
         * @param eventQueue the event queue
         */
        public SteamingServer(BlockingQueue<String> eventQueue) {
            this.eventQueue = eventQueue;
        }

        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            ) {
                while (true) {
                    String event = eventQueue.take();
                    System.out.println(String.format("Writing \"%s\" to the socket.", event));
                    out.println(event);
                }
            } catch (IOException|InterruptedException e) {
                throw new RuntimeException("Server error", e);
            }
        }
    }
}