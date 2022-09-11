package io.online.itbank.model

import org.springframework.security.core.GrantedAuthority

enum class Privilege(val authorities: MutableSet<out GrantedAuthority>) {
    ADMIN(mutableSetOf(Role.ROLE_ADMIN, Role.ROLE_USER)),
    USER(mutableSetOf(Role.ROLE_USER)),
}