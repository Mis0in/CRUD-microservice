package com.apiproject.taskhandler.user.service.events

import java.time.Instant
import java.util.UUID

/**
 * Represents a user-related event in the system.
 * Used to track changes to user entities (create/update/delete).
 *
 * @property userId ID of the user affected by this event
 * @property eventType Type of operation that triggered this event
 */
data class UserEvent (
    override val eventId: String = UUID.randomUUID().toString(),
    override val timestamp: Instant = Instant.now(),
    val userId: Long,
    val eventType: UserEventType,
) : KafkaEvent