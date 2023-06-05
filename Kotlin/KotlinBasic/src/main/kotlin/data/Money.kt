package data

data class Money(
    val amount: Long
) {
    operator fun plus(other: Money): Money {
        return Money(amount + other.amount)
    }
}