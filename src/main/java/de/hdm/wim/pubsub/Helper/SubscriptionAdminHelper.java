package de.hdm.wim.pubsub.Helper;

import com.google.cloud.Identity;
import com.google.cloud.Role;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.spi.v1.PagedResponseWrappers.ListSubscriptionsPagedResponse;
import com.google.cloud.pubsub.spi.v1.TopicAdminClient;
import com.google.cloud.pubsub.spi.v1.SubscriptionAdminClient;
import com.google.iam.v1.Binding;
import com.google.iam.v1.Policy;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.pubsub.v1.ListSubscriptionsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.PullResponse;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.ReceivedMessage;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ben on 23/04/2017.
 */


public class SubscriptionAdminHelper {

	private final String projectId;
	private static final Logger logger = Logger.getLogger(SubscriptionAdminHelper.class);


	/**
	 * Instantiates a new Subscription admin helper.
	 */
	public SubscriptionAdminHelper() {	this.projectId = ServiceOptions.getDefaultProjectId();	}

	/**
	 * Gets project id.
	 *
	 * @return the project id
	 */
	public String getProjectId() {	return projectId;	}

	/**
	 * Example of creating a pull subscription for a topic.
	 *
	 * @param subscriptionId the subscription id
	 * @return the subscription
	 * @throws Exception the exception
	 */
	public Subscription createSubscriptionIfNotExist(String topicId, String subscriptionId) throws Exception {

		Subscription subs = getSubscription(topicId);
		if(subs == null){

			try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {

				// eg. projectId = "my-test-project", topicId = "my-test-topic"
				TopicName topicName = TopicName.create(projectId, topicId);

				// eg. subscriptionId = "my-test-subscription"
				SubscriptionName subscriptionName =	SubscriptionName.create(projectId, subscriptionId);

				// create a pull subscription with default acknowledgement deadline
				Subscription subscription =
					subscriptionAdminClient.createSubscription(
							subscriptionName,
							topicName,
							PushConfig.getDefaultInstance(),
							0
					);

				return subscription;
			}
		}else{
			return subs;
		}
	}

	/**
	 * Example of creating a subscription with a push endpoint.
	 *
	 * @param subscriptionId the subscription id
	 * @param endpoint       the endpoint
	 * @return the subscription
	 * @throws Exception the exception
	 */
	public Subscription createSubscriptionWithPushEndpoint(String topicId, String subscriptionId, String endpoint) throws Exception {

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
			TopicName topicName = TopicName.create(projectId, topicId);
			SubscriptionName subscriptionName =
					SubscriptionName.create(projectId, subscriptionId);

			// eg. endpoint = "https://my-test-project.appspot.com/push"
			PushConfig pushConfig = PushConfig.newBuilder().setPushEndpoint(endpoint).build();

			// acknowledgement deadline in seconds for the message received over the push endpoint
			int ackDeadlineInSeconds = 10;

			Subscription subscription =
					subscriptionAdminClient.createSubscription(
							subscriptionName, topicName, pushConfig, ackDeadlineInSeconds);
			return subscription;
		}
	}

	/**
	 * Example of replacing the push configuration of a subscription, setting the push endpoint.
	 *
	 * @param endpoint the endpoint
	 * @throws Exception the exception
	 */
	public void replacePushConfig(String subscriptionId, String endpoint) throws Exception {

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
			SubscriptionName subscriptionName = SubscriptionName.create(projectId, subscriptionId);
			PushConfig pushConfig = PushConfig.newBuilder().setPushEndpoint(endpoint).build();
			subscriptionAdminClient.modifyPushConfig(subscriptionName, pushConfig);
		}
	}

	/**
	 * Example of listing subscriptions.
	 *
	 * @return the list subscriptions paged response
	 * @throws Exception the exception
	 */
	public ListSubscriptionsPagedResponse listSubscriptions() throws Exception {

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
			ListSubscriptionsRequest listSubscriptionsRequest =
					ListSubscriptionsRequest.newBuilder()
							.setProjectWithProjectName(ProjectName.create(projectId))
							.build();
			ListSubscriptionsPagedResponse response =
					subscriptionAdminClient.listSubscriptions(listSubscriptionsRequest);
			Iterable<Subscription> subscriptions = response.iterateAll();

			for (Subscription subscription : subscriptions) {
				// do something with the subscription
			}
			return response;
		}
	}

	/**
	 * Example of deleting a subscription.
	 *
	 * @return the subscription name
	 * @throws Exception the exception
	 */
	public SubscriptionName deleteSubscription(String subscriptionId) throws Exception {

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
			SubscriptionName subscriptionName = SubscriptionName.create(projectId, subscriptionId);
			subscriptionAdminClient.deleteSubscription(subscriptionName);
			return subscriptionName;
		}
	}

	/**
	 * Example of getting a subscription policy.
	 *
	 * @return the subscription policy
	 * @throws Exception the exception
	 */
	public Policy getSubscriptionPolicy(String subscriptionId) throws Exception {

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
			SubscriptionName subscriptionName = SubscriptionName.create(projectId, subscriptionId);
			Policy policy = subscriptionAdminClient.getIamPolicy(subscriptionName.toString());
			if (policy == null) {
				// subscription was not found
			}
			return policy;
		}
	}

	/**
	 * Example of replacing a subscription policy.
	 *
	 * @return the policy
	 * @throws Exception the exception
	 */
	public Policy replaceSubscriptionPolicy(String subscriptionId) throws Exception {

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
			SubscriptionName subscriptionName = SubscriptionName.create(projectId, subscriptionId);
			Policy policy = subscriptionAdminClient.getIamPolicy(subscriptionName.toString());
			// Create a role => members binding
			Binding binding =
					Binding.newBuilder()
							.setRole(Role.viewer().toString())
							.addMembers(Identity.allAuthenticatedUsers().toString())
							.build();
			//Update policy
			Policy updatedPolicy = policy.toBuilder().addBindings(binding).build();

			updatedPolicy = subscriptionAdminClient.setIamPolicy(subscriptionName.toString(), updatedPolicy);
			return updatedPolicy;
		}
	}

	/**
	 * Example of testing whether the caller has the provided permissions on a subscription.
	 *
	 * @return the test iam permissions response
	 * @throws Exception the exception
	 */
	public TestIamPermissionsResponse testSubscriptionPermissions(String subscriptionId) throws Exception {

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
			List<String> permissions = new LinkedList<>();
			permissions.add("pubsub.subscriptions.get");
			SubscriptionName subscriptionName = SubscriptionName.create(projectId, subscriptionId);
			TestIamPermissionsResponse testedPermissions =
					topicAdminClient.testIamPermissions(subscriptionName.toString(), permissions);
			return testedPermissions;
		}
	}

	/**
	 * Example of getting a subscription.
	 *
	 * @return the subscription
	 * @throws Exception the exception
	 */
	public Subscription getSubscription(String subscriptionId) throws Exception {

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
			SubscriptionName subscriptionName = SubscriptionName.create(projectId, subscriptionId);
			Subscription subscription = subscriptionAdminClient.getSubscription(subscriptionName);
			return subscription;
		}
	}
}