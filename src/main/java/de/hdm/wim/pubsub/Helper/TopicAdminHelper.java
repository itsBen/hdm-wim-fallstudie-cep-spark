package de.hdm.wim.pubsub.Helper;

import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.spi.v1.TopicAdminClient;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;
import de.hdm.wim.source.MessageServer;
import org.apache.log4j.Logger;

/**
 * Created by ben on 22/04/2017.
 */
public class TopicAdminHelper {

	private final String projectId;
	private static final Logger logger = Logger.getLogger(TopicAdminHelper.class);


	/**
	 * Instantiates a new Topic admin client snippets.
	 */
	public TopicAdminHelper() {
		this.projectId = ServiceOptions.getDefaultProjectId();
	}

	/**
	 * Gets project id.
	 *
	 * @return the project id
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * Create topic with given id.
	 *
	 * @param topicId the topic id
	 * @return the topic
	 * @throws Exception the exception
	 */
	public Topic createTopicIfNotExist(String topicId) throws Exception {

		logger.info("Create Topic: " + topicId);

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {

			TopicName topicName = TopicName.create(projectId, topicId);
			if(topicAdminClient.getTopic(topicName) == null){
				logger.info("Successfully created topic: " + topicId);
				return topicAdminClient.createTopic(topicName);
			}else{
				logger.warn("Topic already exists: " + topicId);
				return topicAdminClient.getTopic(topicName);
			}
		}
	}

	/**
	 * Deleting a topic with given id.
	 *
	 * @param topicId the topic id
	 * @throws Exception the exception
	 */
	public void deleteTopic(String topicId) throws Exception {

		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
			TopicName topicName = TopicName.create(projectId, topicId);
			topicAdminClient.deleteTopic(topicName);
		}catch(Exception ex) {
			logger.warn("Could not delete topic!", ex);
		}
	}

	/**
	 * Get topic by id.
	 *
	 * @param topicId the topic id
	 * @return the topic
	 * @throws Exception the exception
	 */
	public Topic getTopic(String topicId) throws Exception {

		Topic topic = null;
		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
			TopicName topicName = TopicName.create(projectId, topicId);
			topic = topicAdminClient.getTopic(topicName);
		}catch (Exception ex) {
			logger.info("Failed to get topic: {} " + topicId, ex);
		}
		return topic;
	}
}