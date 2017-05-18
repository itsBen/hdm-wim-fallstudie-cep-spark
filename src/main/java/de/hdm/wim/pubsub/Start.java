package de.hdm.wim.pubsub;

import com.google.cloud.pubsub.spi.v1.AckReplyConsumer;
import com.google.cloud.pubsub.spi.v1.MessageReceiver;
import com.google.cloud.pubsub.spi.v1.Subscriber;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.pubsub.v1.*;
import de.hdm.wim.pubsub.Helper.PublishHelper;
import de.hdm.wim.pubsub.Helper.SubscriptionAdminHelper;
import de.hdm.wim.pubsub.Helper.TopicAdminHelper;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ben on 22/04/2017.
 */
public class Start {

	private static final Logger logger = Logger.getLogger(Start.class);

	public static void main(String[] args) throws Exception {

		String topicId 		= "test-topic-123";

		// create a topic with the topicId
		TopicAdminHelper to = new TopicAdminHelper();
		TopicName topic 	= to.createTopicIfNotExist(topicId).getNameAsTopicName();

		// publish messages to topic
		PublishHelper ph 		= new PublishHelper();
		List<String> messages 	= Arrays.asList("first message", "second message");
		ph.publishMessageToTopic(messages, topic);

		logger.info("test 1");

		// create a subscription
		String subscriptionName 		= "my-subscription-id-1";
		SubscriptionAdminHelper sah 	= new SubscriptionAdminHelper();
		SubscriptionName subscription 	= sah
				.createSubscriptionIfNotExist(topic.getTopic(), subscriptionName)
				.getNameAsSubscriptionName();

		logger.info("test 1.4");

		// override receiveMessage
		MessageReceiver receiver = (message, consumer) -> {
			logger.info("got message: " + message.getData().toStringUtf8());
			consumer.ack();
		};

		logger.info("test 2");

		Subscriber subscriber = null;
		try {
			subscriber = Subscriber.defaultBuilder(subscription, receiver).build();

			subscriber.addListener(
					new Subscriber.Listener() {
						@Override
						public void failed(Subscriber.State from, Throwable failure) {
							// Handle failure. This is called when the Subscriber encountered a fatal error and is shutting down.
							System.err.println(failure);
						}
					},
					MoreExecutors.directExecutor()
			);

			logger.info("test 3");


			subscriber.startAsync().awaitRunning();

			Thread.sleep(60000);
		} finally {
			if (subscriber != null) {
				subscriber.stopAsync();
			}
		}







	}
}
