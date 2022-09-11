package io.online.itbank.security.domain

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterForm(
        @field:NotBlank
        val username: String,

        @field:Size(min = 8, message = "Password must be at least 8 characters long")
        val password: String,
        
        val isAdmin: Boolean = false,
)