package com.apiproject.taskhandler.user.service.events

import java.time.Instant
import java.util.UUID

data class RawEvent(
    override val eventId: String = UUID.randomUUID().toString(),
    override val timestamp: Instant = Instant.now(),
    val rawData: String
) : KafkaEvent
