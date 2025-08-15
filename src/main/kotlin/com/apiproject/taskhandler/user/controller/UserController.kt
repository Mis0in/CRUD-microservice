package com.apiproject.taskhandler.user.controller

import com.apiproject.taskhandler.user.dto.User
import com.apiproject.taskhandler.user.repository.UserRepository
import com.apiproject.taskhandler.user.service.KafkaMessagePublisher
import com.apiproject.taskhandler.user.service.events.UserEventType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * REST Controller for handling user-related operations.
 * Provides endpoints for CRUD operations on User entities and publishes corresponding events to Kafka.
 *
 * @property userRepository Repository for database operations on User entities
 * @property publisher Service for publishing user events to Kafka
 */
@RestController
@RequestMapping("/api/users")
class UserController(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val publisher: KafkaMessagePublisher
) {

    /**
     * Retrieves all users from the database.
     *
     * @return List of all User entities in the system
     */
    @GetMapping("")
    fun getUsers(): List<User> = userRepository.findAll().toList()

    /**
     * Creates a new user in the system.
     * Publishes a USER_CREATED event to Kafka after successful creation.
     *
     * @param user User object to be created (received in request body)
     * @return ResponseEntity containing the created User and HTTP 201 status on success
     */
    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val createdUser = userRepository.save(user)
        publisher.publishUserEvent(createdUser.id, UserEventType.CREATED)

        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param userId ID of the user to retrieve
     * @return ResponseEntity containing the User and HTTP 200 status if found,
     *         or HTTP 404 status if user doesn't exist
     */
    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long): ResponseEntity<User> {
        val user = userRepository.findById(userId)
        return if (user.isPresent) {
            ResponseEntity(user.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Updates an existing user's information.
     * Publishes a USER_UPDATED event to Kafka after successful update.
     *
     * @param userId ID of the user to update
     * @param newUser User object containing new values (received in request body)
     * @return ResponseEntity with HTTP 200 status if update was successful,
     *         or HTTP 404 status if user doesn't exist
     */
    @PutMapping("/{userId}")
    fun updateUserById(@PathVariable userId: Long, @RequestBody newUser: User): ResponseEntity<User> {
        val found = userRepository.findById(userId)
        if (found.isPresent) {
            userRepository.save(User(userId, newUser.name, newUser.email))
            publisher.publishUserEvent(userId, UserEventType.UPDATED)

            return ResponseEntity(HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Deletes a user from the system.
     * Publishes a USER_DELETED event to Kafka after successful deletion.
     *
     * @param userId ID of the user to delete
     * @return ResponseEntity with HTTP 200 status if deletion was successful,
     *         or HTTP 404 status if user doesn't exist
     */
    @DeleteMapping("/{userId}")
    fun deleteUserById(@PathVariable userId: Long): ResponseEntity<User> {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId)
            publisher.publishUserEvent(userId, UserEventType.DELETED)

            return ResponseEntity(HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}