package com.apiproject.taskhandler.user.service

enum class KafkaTopic(val topicName: String) {
    UserCreated("user-created"),
    UserDeleted("user-deleted"),
}