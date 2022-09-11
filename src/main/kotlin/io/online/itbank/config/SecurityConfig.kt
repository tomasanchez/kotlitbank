package io.online.itbank.config

import io.online.itbank.security.AuthFailureHandler
import io.online.itbank.security.AuthSuccessHandler
import io.online.itbank.security.JsonObjectAuthenticationFilter
import io.online.itbank.security.JwtAuthorizationFilter
import io.online.itbank.services.auth.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint


@Configuration
class SecurityConfig(
        private val authenticationManager: AuthenticationManager,
        private val jwtUserDetailsService: JwtUserDetailsService,
        private val authSuccessHandler: AuthSuccessHandler,
        private val authFailureHandler: AuthFailureHandler,
        @Value("\${jwt.secret}")
        private val secret: String,
) {

    @Bean
    fun authenticationFilter(): JsonObjectAuthenticationFilter {
        val filter = JsonObjectAuthenticationFilter()

        filter.setAuthenticationManager(authenticationManager)
        filter.setAuthenticationSuccessHandler(authSuccessHandler)
        filter.setAuthenticationFailureHandler(authFailureHandler)
        return filter
    }


    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests {
                    it
                            .antMatchers("/").permitAll()
                            .antMatchers("/api/v1.0/auth/**").permitAll()
                            .antMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                            .and()
                            .sessionManagement()
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                            .and()
                            .addFilter(authenticationFilter())
                            .addFilter(
                                    JwtAuthorizationFilter(
                                            authenticationManager,
                                            jwtUserDetailsService,
                                            secret))
                            .exceptionHandling()
                            .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                }
                .httpBasic(withDefaults())


        return http.build()
    }

}