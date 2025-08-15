package com.apiproject.taskhandler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main application class that serves as the entry point for the Spring Boot application.
 * Autoconfigures Spring Boot and starts the application context.
 */
@SpringBootApplication
class TaskHandlerApplication

/**
 * Application entry point function.
 *
 * @param args Command line arguments passed to the application
 */
fun main(args: Array<String>) {
    runApplication<TaskHandlerApplication>(*args)
}
