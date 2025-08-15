package com.apiproject.taskhandler.user.service

/**
 * Enum representing available Kafka topics in the system.
 * Provides type-safe topic names and centralizes topic configuration.
 *
 * @property topicName The actual name of the topic as used in Kafka
 */
enum class KafkaTopic(val topicName: String) {
    /**
     * Topic for all user-related events (creation, updates, deletions)
     */
    USER_EVENTS("USER_EVENTS"),
    // Additional topics can be added here as the system grows
}