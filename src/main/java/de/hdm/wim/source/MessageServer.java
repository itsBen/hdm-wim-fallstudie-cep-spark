package de.hdm.wim.source;

import com.google.gson.Gson;
import de.hdm.wim.resources.Message;
import de.hdm.wim.resources.Participant;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by ben on 18/04/2017.
 */
public class MessageServer {
    private static final Executor SERVER_EXECUTOR     = Executors.newSingleThreadExecutor();
    private static final int PORT                     = 9999;
    private static final long MESSAGE_PERIOD_SECONDS  = 1;
    private static final Logger logger                = Logger.getLogger(MessageServer.class);

    /**
     * The entry point of this application. It will send messages every second until terminated.
     *
     * @param args the input arguments
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        BlockingQueue<String> eventQueue        = new ArrayBlockingQueue<>(1000);
        //final Participant sender1               = new Participant("Jim",    "Barton",    "CEO");
        //final Participant sender2               = new Participant("Mike",   "Obrut",     "PM");
        //final ArrayList<Participant> senders    = new ArrayList<>();

        //senders.add(sender1);
        //senders.add(sender2);

        SERVER_EXECUTOR.execute(new SteamingServer(eventQueue));

        while (true) {
            MessageGenerator msgg   = new MessageGenerator();
            Message message         = msgg.GenerateMessage();
            Gson gson               = new Gson();

            eventQueue.put(gson.toJson(message));
            Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));
        }
    }

    private static class SteamingServer implements Runnable {
        private final BlockingQueue<String> eventQueue;
        private final Logger logger = Logger.getLogger(MessageServer.class);

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
            try (
                    ServerSocket serverSocket   = new ServerSocket(PORT);
                    Socket clientSocket         = serverSocket.accept();
                    PrintWriter out             = new PrintWriter(clientSocket.getOutputStream(), true);
            )
            {
                while (true) {
                    String message = eventQueue.take();

                    logger.info(String.format("Writing \"%s\" to the socket.", message));
                    out.println(message);
                }
            } catch (IOException|InterruptedException e) {
                throw new RuntimeException("Server error", e);
            }
        }
    }
}