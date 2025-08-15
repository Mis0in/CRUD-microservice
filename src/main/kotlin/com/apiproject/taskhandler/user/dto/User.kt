package com.apiproject.taskhandler.user.dto

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * Represents a User entity in the system.
 * This class maps to the "users" table in the database.
 *
 * @property id Unique identifier of the user (auto-generated)
 * @property name Full name of the user
 * @property email Email address of the user (must be unique)
 */
@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val email: String,
)