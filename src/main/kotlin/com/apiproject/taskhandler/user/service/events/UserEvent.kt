package com.apiproject.taskhandler.user.service.events

import java.time.Instant
import java.util.UUID

data class UserEvent (
    override val eventId: String = UUID.randomUUID().toString(),
    override val timestamp: Instant = Instant.now(),
    val userId: Long,
    val eventType: UserEventType,
) : KafkaEvent