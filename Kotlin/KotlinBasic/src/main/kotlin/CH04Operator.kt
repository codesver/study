import data.JavaMoney

// 단항 / 산술 연산자 -> Java == Kotlin
// 비교 연산자와 동등성, 동일성 -> Java 와는 다르게 비교 연산자를 사용하면 자동으로 compareTo 호출
fun compare(moneyA: JavaMoney, moneyB: JavaMoney): Unit {
    if (moneyA > moneyB) {
        println("Money A가 Money B보다 큽니다.")
    }
}

fun isSameMoney(moneyA: JavaMoney, moneyB: JavaMoney) = moneyA === moneyB

fun isSameMoneyAmount(moneyA: JavaMoney, moneyB: JavaMoney) = moneyA == moneyB

// 논리 연산자 / 코틀린 특이 연산자
fun kotlinOp(num: Int, arr: IntArray) {
    if (num in 1..9) println("일의 자리 O")
    if (num !in 1 until 10) print("일의 자리 X")
    println(arr[num])
}

// 연산자 오버로딩
fun sumMoney(moneyA: JavaMoney, moneyB: JavaMoney): JavaMoney = moneyA + moneyB
