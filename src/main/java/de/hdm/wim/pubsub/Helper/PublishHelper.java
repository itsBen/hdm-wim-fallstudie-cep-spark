package de.hdm.wim.pubsub.Helper;

import com.google.api.gax.core.ApiFuture;
import com.google.cloud.pubsub.spi.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ben on 23/04/2017.
 */
public class PublishHelper {

	private static final Logger logger = Logger.getLogger(PublishHelper.class);

	public void publishMessageToTopic(List<String> messages, TopicName topicName) throws Exception {
		Publisher publisher = null;
    	try {
			publisher = Publisher.defaultBuilder(topicName).build();
			List<ApiFuture<String>> messageIds = new ArrayList<>();

			for (String message : messages) {
				ByteString data = ByteString.copyFromUtf8(message);
				PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
				ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
				messageIds.add(messageIdFuture);
			}

			for (ApiFuture<String> messageId : messageIds) {
				System.out.println("published with message ID: " + messageId.get());
			}
		} finally {
			if (publisher != null) {
				publisher.shutdown();
			}
		}
	}
}
