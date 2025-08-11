package com.apiproject.taskhandler.user.service.events

data class RawEvent(val rawData: String) : KafkaEvent
