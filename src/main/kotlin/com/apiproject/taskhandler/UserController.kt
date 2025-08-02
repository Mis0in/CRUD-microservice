package com.apiproject.taskhandler

import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(@Autowired private val userRepository: UserRepository) {

    //Get all users from database
    @GetMapping("")
    fun getUsers(): List<User> = userRepository.findAll().toList()

    //Create user
    @PostMapping("")
    fun createUser(user: User): ResponseEntity<User> {
        val createdUser = userRepository.save(user)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    //Get user by id
    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long): ResponseEntity<User> {
        val user = userRepository.findById(userId).orElse(null)
        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}