package com.apiproject.taskhandler.user.service.events

enum class UserEventType(val code: String) {
    CREATED("user.created"),
    UPDATED("user.updated"),
    DELETED("user.deleted"),
}