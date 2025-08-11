package com.apiproject.taskhandler.user.service.events

import java.time.Instant
import java.util.UUID

sealed interface KafkaEvent {
    val eventId: String
    val timestamp: Instant
}