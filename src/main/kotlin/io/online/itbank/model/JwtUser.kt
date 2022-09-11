package io.online.itbank.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*


@Entity
@Table(name = "users")
class JwtUser(
        @Column(name = "username", nullable = false, unique = true)
        private var username: String,

        @Column(name = "password", nullable = false)
        private var password: String,

        @Column(name = "role", nullable = true)
        @Enumerated(EnumType.ORDINAL)
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
        private var roles: MutableSet<Role> = mutableSetOf(),

        @Column(name = "enabled", nullable = false)
        private var enabled: Boolean = true,

        ) : PersistentEntity(), UserDetails {


    fun addRole(role: Role): JwtUser {
        roles.add(role)
        return this
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles
    }

    @JsonIgnore
    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isEnabled(): Boolean {
        return enabled
    }

}