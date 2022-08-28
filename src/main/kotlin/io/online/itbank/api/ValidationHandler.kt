package io.online.itbank.api

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.function.Consumer
import javax.persistence.EntityNotFoundException

@ControllerAdvice
class ValidationHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
                                              headers: HttpHeaders, status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {

        val errors: MutableMap<String, String?> = HashMap()

        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val message = error.getDefaultMessage()
            errors[fieldName] = message
        })

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException): ResponseEntity<Any> {

        val errors: MutableMap<String, String?> = java.util.HashMap()

        errors["message"] = ex.message

        return ResponseEntity(errors, HttpStatus.NOT_FOUND)
    }


}