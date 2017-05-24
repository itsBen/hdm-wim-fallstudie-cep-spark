package de.hdm.wim.pubsub;

import com.google.cloud.ServiceOptions;
import com.google.pubsub.v1.Topic;
import de.hdm.wim.pubsub.Helper.PublishHelper;
import de.hdm.wim.pubsub.Helper.SubscriptionHelper;
import de.hdm.wim.pubsub.Helper.TopicHelper;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ben on 24/05/2017.
 */
public class Start2 {

	private static final String _projectId	= ServiceOptions.getDefaultProjectId();
	private static String _testTopic 		= "test-topic-1";
	private static String _subscriptionId   = "my-test-subscription-for-test-topic-1";



	public static void main(String[] args) throws Exception {

		TopicHelper th = new TopicHelper(_projectId);
		Topic topic    = th.createTopicIfNotExists(_testTopic);

		SubscriptionHelper sh = new SubscriptionHelper(_projectId);
		sh.createSubscriptionIfNotExists(topic.getNameAsTopicName(), _subscriptionId);

		PublishHelper ph 		= new PublishHelper();
		List<String> messages 	= Arrays.asList("first message", "second message");
		ph.publishMessages(messages, topic.getNameAsTopicName());

		sh.createSubscriber(_subscriptionId);
	}
}
