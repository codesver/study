fun printNumbers(numbers: IntArray) {
    // For-Each
    for (number in numbers) println(number)

    // Traditional For
    for (i in 0..10) println(numbers[i])            // Ascending
    for (i in 10 downTo 0) println(numbers[i])      // Descending
    for (i in 0..10 step 2) println(numbers[i])     // Step
    for (i in 0 until 10) println(numbers[i])       // Until

    // Progression & Range
    (0..10).forEach(::println)
    (10 downTo 0).forEach(::println)
    (0..10 step 2).forEach(::println)
    (0 until 10).forEach(::println)

    // While
    var i = 1
    while (i <= 10) {
        println(numbers[i])
        i++
    }
}