package com.apiproject.taskhandler.user.service.events

import java.time.Instant
import java.util.UUID


/**
 * Represents a raw/unstructured event in the system.
 * Used for events that don't have a specific schema or structure.
 *
 * @property rawData The unstructured payload of the event
 */
data class RawEvent(
    override val eventId: String = UUID.randomUUID().toString(),
    override val timestamp: Instant = Instant.now(),
    val rawData: String
) : KafkaEvent
