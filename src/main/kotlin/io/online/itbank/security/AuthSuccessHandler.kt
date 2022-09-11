package io.online.itbank.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    private val expTime: Long = 15L

    private val mapper = jacksonMapperBuilder().addModule(JavaTimeModule()).build()

    override fun onAuthenticationSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?,
    ) {

        val principal: UserDetails = authentication?.principal as UserDetails

        val token = JWT.create()
                .withSubject(principal.username)
                .withArrayClaim("roles", principal.authorities.map { it.authority }.toTypedArray())
                .withExpiresAt(Date(System.currentTimeMillis() + expTime * 60 * 1_000))
                .sign(Algorithm.HMAC256(secret))

        response?.addHeader("Authorization", "Bearer $token")

        val body = mutableMapOf<String, Any>()
        body["accessToken"] = token
        body["timestamps"] = LocalDateTime.now()
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.writer?.write(mapper.writeValueAsString(body))
        
    }
}