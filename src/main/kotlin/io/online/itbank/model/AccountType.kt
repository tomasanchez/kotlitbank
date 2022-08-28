package io.online.itbank.model

enum class AccountType(private val label: String) {
    CHECKING("Checking"),
    SAVINGS("Savings");

    override fun toString(): String {
        return label
    }
}