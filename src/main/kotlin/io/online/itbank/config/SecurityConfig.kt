package io.online.itbank.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
                .authorizeHttpRequests { authz ->
                    authz.anyRequest().authenticated()
                }
                .httpBasic(withDefaults())
        return http.build()
    }


    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().antMatchers(
                    "/css/**", "/js/**",
                    "/img/**", "/lib/**", "/favicon.ico",
                    "/oauth2", "/api/**")
        }
    }

}