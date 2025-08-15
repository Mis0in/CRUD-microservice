package com.apiproject.taskhandler.user.service

import com.apiproject.taskhandler.user.service.events.UserEvent
import com.apiproject.taskhandler.user.service.events.KafkaEvent
import com.apiproject.taskhandler.user.service.events.RawEvent
import com.apiproject.taskhandler.user.service.events.UserEventType
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

/**
 * Service responsible for publishing events to Kafka topics.
 * Handles serialization and delivery of different event types with proper error handling and logging.
 *
 * @property template KafkaTemplate used for sending messages to Kafka brokers
 * @property mapper ObjectMapper used for JSON serialization of events
 */
@Component
class KafkaMessagePublisher(
    @Autowired private val template: KafkaTemplate<String, Any>,
    @Autowired private val mapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(KafkaMessagePublisher::class.java)

    /**
     * Publishes a generic KafkaEvent to the specified topic.
     * Handles different event types with appropriate serialization:
     * - UserEvent: Serialized as JSON
     * - RawEvent: Sent as raw string data
     *
     * @param topic Destination Kafka topic (from KafkaTopic enum)
     * @param key Partition key for the message
     * @param event The event object to publish
     * @throws RuntimeException if JSON serialization fails
     */
    fun publishEvent(topic: KafkaTopic, key: String, event: KafkaEvent) {
        val message = when (event) {
            is UserEvent -> mapper.writeValueAsString(event)
            is RawEvent -> event.rawData
        }

        val future = template.send(topic.topicName, key, message)
        future.whenComplete { result, ex ->
            if (ex != null) {
                logger.error(
                    "Unable to send message=[{}] with key=[{}] to topic=[{}]",
                    message, key, topic.topicName, ex
                )
            } else {
                logger.debug(
                    "Sent message=[{}] with key=[{}] to topic=[{}] offset=[{}]",
                    message, key, topic.topicName, result?.recordMetadata?.offset()
                )
            }
        }

    }

    /**
     * Convenience method for publishing user-related events.
     * Creates a UserEvent automatically and publishes to the USER_EVENTS topic.
     *
     * @param userId ID of the user affected by the event
     * @param eventType Type of user event (CREATED/UPDATED/DELETED)
     */
    fun publishUserEvent(userId: Long, eventType: UserEventType) {
        publishEvent(
            topic = KafkaTopic.USER_EVENTS,
            key = "$userId",
            event = UserEvent(
                userId = userId,
                eventType = eventType,
            ),
        )
    }

}