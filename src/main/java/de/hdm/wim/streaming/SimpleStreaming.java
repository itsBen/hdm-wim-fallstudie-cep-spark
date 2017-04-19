package de.hdm.wim.streaming;

import org.apache.log4j.*;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.*;

/**
 * Created by ben on 18/04/2017.
 */
public class SimpleStreaming {

    private static final String HOST    = "localhost";
    private static final int PORT       = 9999;

    public static void main(String[] args) {

        final Logger logger = Logger.getLogger(SimpleStreaming.class);

        // Configure and initialize the SparkStreamingContext
        SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("SimpleStreaming");

        JavaStreamingContext ssc = new JavaStreamingContext(conf, Durations.seconds(5));

        // Receive streaming data from the source
        JavaReceiverInputDStream<String> lines = ssc.socketTextStream(HOST, PORT);

        lines.print();

        // Execute the Spark workflow defined above
        ssc.start();

        try {
            ssc.awaitTermination();
        }
        catch(InterruptedException ie){
            logger.warn("InterruptedException: " + ie);
        }
    }
}
