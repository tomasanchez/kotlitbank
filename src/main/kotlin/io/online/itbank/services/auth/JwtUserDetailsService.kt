package io.online.itbank.services.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(private val jwtUserService: JwtUserService) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return username?.let { jwtUserService.findByUsername(it) }
                ?: throw IllegalArgumentException("Username is null")
    }

}