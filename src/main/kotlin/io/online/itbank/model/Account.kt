package io.online.itbank.model

import javax.persistence.*

@Entity
@Table(name = "accounts")
class Account(
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "customer_id", insertable = false, updatable = false)
        var customer: Customer? = null,
        @Column(name = "type")
        @Enumerated(EnumType.ORDINAL)
        var type: AccountType,
        @Column(name = "number", unique = true)
        var iban: String,
        @Column(name = "alias", unique = true)
        var alias: String,
        @Column(name = "balance")
        var balance: Double,
) : PersistentEntity()