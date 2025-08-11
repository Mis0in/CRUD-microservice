package com.apiproject.taskhandler.user.repository

import com.apiproject.taskhandler.user.dto.User
import org.springframework.data.repository.CrudRepository

// Entity that is used for interaction with database
interface UserRepository : CrudRepository<User, Long>