package io.pivotal

import java.util.HashMap
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
class ExceptionController {
    @ExceptionHandler(HttpUnauthorizedException::class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    internal fun unauthorizedAccess(e: Exception) {
    }
}