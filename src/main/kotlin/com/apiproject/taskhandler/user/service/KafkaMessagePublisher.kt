package com.apiproject.taskhandler.user.service

import com.apiproject.taskhandler.user.service.events.UserEvent
import com.apiproject.taskhandler.user.service.events.KafkaEvent
import com.apiproject.taskhandler.user.service.events.RawEvent
import com.apiproject.taskhandler.user.service.events.UserEventType
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaMessagePublisher(
    @Autowired private val template: KafkaTemplate<String, Any>,
    @Autowired private val mapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(KafkaMessagePublisher::class.java)

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