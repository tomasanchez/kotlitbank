package io.online.itbank.respository

import io.online.itbank.model.JwtUser
import org.springframework.data.repository.CrudRepository

interface JwtUserRepository : CrudRepository<JwtUser, Long> {
    fun findByUsername(username: String?): JwtUser?
}