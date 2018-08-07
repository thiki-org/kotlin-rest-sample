package net.thiki.rest.sample.exception

import java.util.Date

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Throwable::class)
    fun handleAllExceptions(ex: Throwable, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val cause = ex.cause
        if (cause is AssertionException){
            return handleAssertionException(cause, request)
        }
        val exceptionResponse = ExceptionResponse(Date(), ex.message ?: "",
                request.getDescription(false), 0)
        ex.printStackTrace()
        return ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(AssertionException::class)
    fun handleAssertionException(ex: AssertionException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(Date(), ex.message ?: "",
                request.getDescription(false), 0)
        return ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND)
    }

}
