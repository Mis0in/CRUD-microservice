package com.apiproject.taskhandler.user.service.events

import java.time.Instant

/**
 * Base interface for all Kafka event types in the system.
 * Defines common properties that all events must have.
 *
 * @property eventId Unique identifier for the event (auto-generated)
 * @property timestamp When the event occurred (auto-set to creation time)
 */
sealed interface KafkaEvent {
    val eventId: String
    val timestamp: Instant
}

