fun validateScoreIsNotNegative(score: Int) {
    if (score !in 0..100) {
        throw IllegalArgumentException("$score should be between 0 and 100")
    }
}

fun getPassOrFail(score: Int) = if (score >= 50) "P" else "F"

fun getGrader(score: Int) =
    if (score >= 90) "A"
    else if (score >= 80) "B"
    else if (score >= 70) "C"
    else "D"

fun getGradeWithWhen(score: Int) = when(score / 10) {
    9 -> "A"
    8 -> "B"
    7 -> "C"
    else -> "D"
}

fun getGradeWithWhenIn(score: Int) = when(score) {
    in 90..99 -> "A"
    in 80..89 -> "B"
    in 70..79 -> "C"
    else -> "D"
}

fun startsWithA(obj: Any): Boolean {
    return when (obj) {
        is String -> obj.startsWith("A")    // Smart Cast
        else -> false
    }
}

fun judgeNumber(number: Int) {
    when (number) {
        1, 0, -1 -> println("Is 1, 0, -1")
        else -> println("Is not 1, 0, -1")
    }
}

fun judgeNumber2(number: Int) {
    when {
        number == 0 -> println("Is 0")
        number % 2 == 0 -> println("Is even number")
        else -> println("Is odd number")
    }
}