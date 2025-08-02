package com.apiproject.taskhandler

import org.springframework.data.repository.CrudRepository

// Entity that is used for interaction with database
interface UserRepository : CrudRepository<User, Long>

