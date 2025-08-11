package com.apiproject.taskhandler.user.service.events

import java.time.Instant
import java.util.UUID

data class UserEvent (
    val eventId: String = UUID.randomUUID().toString(),
    val userId: String,
    val timestamp: Instant = Instant.now(),
    val eventType: UserEventType,
) : KafkaEvent