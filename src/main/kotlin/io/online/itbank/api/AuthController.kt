package io.online.itbank.api

import io.online.itbank.model.JwtUser
import io.online.itbank.security.domain.LoginCredentials
import io.online.itbank.security.domain.RegisterForm
import io.online.itbank.services.auth.JwtUserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api")
class AuthController(
        private val jwtUserService: JwtUserService,
) {

    companion object {
        private const val API_VERSION = "v1.0"
        private const val PATH = "auth"
        private val logger = LoggerFactory.getLogger(AuthController::class.java)
    }

    @PostMapping("/$API_VERSION/$PATH/login")
    fun logIn(@RequestBody @Validated loginCredentials: LoginCredentials) {
        logger.info("[/api/$API_VERSION/auth/login] request received")
        logger.info("Login attempt for user: ${loginCredentials.username}")
    }

    @PostMapping("/$API_VERSION/$PATH/register")
    fun register(
            @RequestBody @Validated registerForm: RegisterForm,
    ): ResponseEntity<JwtUser> {
        logger.info("[/api/$API_VERSION/auth/register] request received")
        logger.info("Register attempt for user: ${registerForm.username}")
        val user = jwtUserService.saveUser(registerForm)
        val uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/$API_VERSION/auth/register/${user.username}")
                .build()
                .toUri()

        return ResponseEntity.created(uri).body(user)
    }
}