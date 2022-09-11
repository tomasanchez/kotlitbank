package io.online.itbank.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.online.itbank.security.domain.LoginCredentials
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JsonObjectAuthenticationFilter : UsernamePasswordAuthenticationFilter() {

    private val mapper: ObjectMapper = jacksonObjectMapper()

    override fun attemptAuthentication(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
    ): Authentication {

        try {
            val sb = StringBuilder()
            var line: String?
            var token: UsernamePasswordAuthenticationToken? = null

            request?.reader?.let { it ->

                while (it.readLine().also { line = it } != null) {
                    sb.append(line)
                }

                val authRequest = mapper.readValue(sb.toString(), LoginCredentials::class.java)
                token = UsernamePasswordAuthenticationToken(authRequest.username,
                        authRequest.password)
                setDetails(request, token)
            }

            return this.authenticationManager.authenticate(token
                    ?: throw java.lang.RuntimeException("Token could not be created"))
        } catch (ioe: IOException) {
            throw RuntimeException(ioe)
        }

    }
}