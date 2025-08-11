package com.apiproject.taskhandler.user.service

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
    private val logger: Logger = LoggerFactory.getLogger(KafkaMessagePublisher::class.java)

    fun sendMessage(topic: KafkaTopic, message: String) {
        template.send(topic.topicName, message)
    }

}