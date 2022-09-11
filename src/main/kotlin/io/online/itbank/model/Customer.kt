package io.online.itbank.model

import javax.persistence.*

@Entity
@Table(name = "customers")
class Customer(

        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        var user: JwtUser? = null,

        @Column(name = "type", nullable = false)
        @Enumerated(EnumType.ORDINAL)
        var type: CustomerType = CustomerType.CLASSIC,

        @Column(name = "name", nullable = false)
        var name: String,

        @Column(name = "last_name", nullable = false)
        var lastName: String,

        @Column(name = "email", nullable = false)
        var email: String,

        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "customer")
        var _accounts: MutableList<Account> = mutableListOf(),
) : PersistentEntity() {

    val accounts get(): List<Account> = _accounts.toList()

    fun addAccount(account: Account) {
        _accounts += account
    }
}
