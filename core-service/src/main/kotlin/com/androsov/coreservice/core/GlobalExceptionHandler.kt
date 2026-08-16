package com.androsov.coreservice.core

import com.androsov.coreservice.tasks.model.inner.errors.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    // 1. Обработка ошибок "Сущность не найдена" (например, вашего IllegalArgumentException из TaskService)
    @ExceptionHandler(IllegalArgumentException::class, NoSuchElementException::class)
    fun handleNotFound(ex: RuntimeException): ResponseEntity<ErrorResponse> {
        logger.warn("Resource not found: ${ex.message}")
        return buildResponse(HttpStatus.NOT_FOUND, "Resource Not Found", ex.message)
    }

    // 2. Обработка ошибок валидации (например, неверный UUID в URL или ошибки @Valid в @RequestBody)
    @ExceptionHandler(MethodArgumentNotValidException::class, MethodArgumentTypeMismatchException::class)
    fun handleBadRequest(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.warn("Bad request validation failed: ${ex.message}")
        val message = if (ex is MethodArgumentNotValidException) {
            ex.bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        } else {
            ex.message
        }
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", message)
    }

    // 3. Перехват всех остальных непредвиденных ошибок (серверный сбой)
    @ExceptionHandler(Exception::class)
    fun handleAllUncaughtExceptions(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("An unexpected error occurred: ", ex)
        return buildResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Server Error",
            "Something went wrong on our side."
        )
    }

    private fun buildResponse(status: HttpStatus, error: String, message: String?): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            status = status.value(),
            error = error,
            message = message
        )
        return ResponseEntity(body, status)
    }
}
