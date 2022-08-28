package io.online.itbank.model

enum class CustomerType(private val label: String) {
    CLASSIC("Classic"),
    GOLD("Gold"),
    PLATINUM("Platinum"),
    BLACK("Black");

    override fun toString(): String {
        return label
    }
}
