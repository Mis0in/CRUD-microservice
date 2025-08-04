package com.apiproject.taskhandler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskHandlerApplication

fun main(args: Array<String>) {
    runApplication<TaskHandlerApplication>(*args)
}
