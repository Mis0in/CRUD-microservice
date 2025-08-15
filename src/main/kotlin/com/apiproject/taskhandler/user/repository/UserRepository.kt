package com.apiproject.taskhandler.user.repository

import com.apiproject.taskhandler.user.dto.User
import org.springframework.data.repository.CrudRepository

/**
 * Repository interface for User entity CRUD operations.
 * Extends Spring Data's CrudRepository with User as the entity type and Long as the ID type.
 */
interface UserRepository : CrudRepository<User, Long>