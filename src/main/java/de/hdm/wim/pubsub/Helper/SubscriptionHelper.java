package de.hdm.wim.pubsub.Helper;

import com.google.api.gax.grpc.ExecutorProvider;
import com.google.api.gax.grpc.InstantiatingExecutorProvider;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.spi.v1.AckReplyConsumer;
import com.google.cloud.pubsub.spi.v1.MessageReceiver;
import com.google.cloud.pubsub.spi.v1.Subscriber;
import com.google.cloud.pubsub.spi.v1.SubscriptionAdminClient;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;
import org.apache.log4j.Logger;

/**
 * Created by ben on 24/05/2017.
 */
public class SubscriptionHelper {

	private static final Logger _logger = Logger.getLogger(TopicHelper.class);
	private final String _projectId;

	/**
	 * Instantiates a new TopicHelper.
	 */
	public SubscriptionHelper() {
		this._projectId = ServiceOptions.getDefaultProjectId();
	}

	/**
	 * Instantiates a new SubscriptionHelper.
	 *
	 * @param projectId unique project identifier, eg. "my-project-id"
	 */
	public SubscriptionHelper(String projectId){
		this._projectId = projectId;
	}

	/**
	 * Gets project id.
	 *
	 * @return the project id
	 */
	public String getProjectId() {
		return _projectId;
	}

	/**
	 * Create a subscription to a specif topic.
	 *
	 * @param topicName the topic name
	 * @param subscriptionId the subscription id, eg. "my-test-subscription"
	 * @return the subscription
	 * @throws Exception the exception
	 */
	public Subscription createSubscriptionIfNotExists(TopicName topicName, String subscriptionId) throws Exception{

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {

			SubscriptionName subscriptionName =	SubscriptionName.create(_projectId, subscriptionId);

			if(subscriptionAdminClient.getSubscription(subscriptionName) == null) {

				// create a pull subscription with default acknowledgement deadline (10 seconds)
				Subscription subscription = subscriptionAdminClient.createSubscription(
					subscriptionName,
					topicName,
					PushConfig.getDefaultInstance(),
					10
				);

				_logger.info("Successfully created subscription: " + subscriptionId);

				return subscription;
			}else{
				_logger.info("Subscription already exists: " + subscriptionId);
				return subscriptionAdminClient.getSubscription(subscriptionName);
			}
		}
	}

	public void createSubscriber(String subscriptionId) throws Exception{

		SubscriptionName subscriptionName = SubscriptionName.create(_projectId, subscriptionId);

		ExecutorProvider executorProvider =	InstantiatingExecutorProvider.newBuilder()
												.setExecutorThreadCount(1)
												.build();

		// Instantiate an asynchronous message receiver
		MessageReceiver receiver = (message, consumer) ->{
			// handle incoming message, then ack/nack the received message
			_logger.info("Id : " + message.getMessageId());
			_logger.info("Data : " + message.getData().toStringUtf8());
			consumer.ack();
		};

		Subscriber subscriber = null;
		try {
			// Create a subscriber bound to the message receiver
			subscriber = Subscriber
				.defaultBuilder(subscriptionName, receiver)
				.setExecutorProvider(executorProvider)
				.build();

			subscriber.addListener(
				new Subscriber.Listener() {
					@Override
					public void failed(Subscriber.State from, Throwable failure) {
						// Handle failure. This is called when the Subscriber encountered a fatal error and is shutting down.
						_logger.error(failure);
					}
				},
				MoreExecutors.directExecutor());

			subscriber.startAsync().awaitRunning();

			Thread.sleep(6000000);
		} catch (InterruptedException e){
			e.printStackTrace();
		} finally {
			// stop receiving messages
			if (subscriber != null) {
				subscriber.stopAsync();
			}
		}
	}
}
