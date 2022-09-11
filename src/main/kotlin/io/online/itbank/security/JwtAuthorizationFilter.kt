package io.online.itbank.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.online.itbank.services.auth.JwtUserDetailsService
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(
        authenticationManager: AuthenticationManager,
        private val jwtUserDetailsService: JwtUserDetailsService,
        private val secret: String,
) : BasicAuthenticationFilter(authenticationManager) {


    companion object {
        private const val PREFIX = "Bearer "
    }

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain,
    ) {

        if (!request.servletPath.contains("/auth/**")) {

            try {
                val authentication = getAuthentication(request)

                if (authentication != null)
                    SecurityContextHolder.getContext().authentication = authentication
            } catch (e: Exception) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = APPLICATION_JSON_VALUE
                response.writer.write("{\"error\":\"${e.message}\"}")
            }

        }

        chain.doFilter(request, response)
    }

    private fun getAuthentication(
            request: HttpServletRequest,
    ): UsernamePasswordAuthenticationToken? {
        val token: String = request.getHeader("Authorization") ?: return null
        if (!token.startsWith(PREFIX)) return null

        val username = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.replace(PREFIX, ""))
                .subject

        val user = jwtUserDetailsService.loadUserByUsername(username)

        return UsernamePasswordAuthenticationToken(user, null, user.authorities)

    }
}



