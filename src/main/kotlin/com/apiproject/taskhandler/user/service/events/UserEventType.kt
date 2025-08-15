package com.apiproject.taskhandler.user.service.events

/**
 * Enumeration of possible user event types.
 * Each type has a corresponding code used for event routing/matching.
 *
 * @property code String identifier used in event processing
 */
enum class UserEventType(val code: String) {
    CREATED("user.created"),
    UPDATED("user.updated"),
    DELETED("user.deleted"),
}