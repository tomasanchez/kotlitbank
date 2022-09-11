package io.online.itbank.services.auth

import io.online.itbank.model.JwtUser
import io.online.itbank.model.Privilege
import io.online.itbank.respository.JwtUserRepository
import io.online.itbank.security.domain.RegisterForm
import io.online.itbank.services.generic.GenericPersistentEntityService
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class JwtUserService(
        val userRepository: JwtUserRepository,
        val passwordEncoder: PasswordEncoder,

        ) : GenericPersistentEntityService<JwtUser>() {


    fun findByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
                ?: throw EntityNotFoundException("Username not found")
    }

    fun saveUser(registerForm: RegisterForm): JwtUser {

        try {
            val user = findByUsername(registerForm.username)
            throw Exception("Username ${user.username} already exists")
        } catch (e: EntityNotFoundException) {
            val encodedPassword = passwordEncoder.encode(registerForm.password)
            val user = JwtUser(registerForm.username, encodedPassword)

            if (registerForm.isAdmin)
                user.privilege = Privilege.ADMIN

            return userRepository.save(user)
        }
    }

    override val dao: CrudRepository<JwtUser, Long>
        get() = userRepository
}