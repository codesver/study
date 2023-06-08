// Array
fun array() {
    val array = arrayOf(100, 200)

    array.plus(30)

    for (i in array.indices) {
        println("${i} : ${array[i]}")
    }

    for ((i, value) in array.withIndex()) {
        println("${i} : ${value}")
    }
}

// Collection - List, Set, Map
// Mutable Collection -> Element Change Available
fun list() {
    val numbers = listOf(100, 200)
    val emptyList = emptyList<Int>()
    val emptyListWithType: List<Int> = emptyList()
    val mutableList = mutableListOf(100, 200)

    numbers[0]

    for (number in numbers) {
        println(number)
    }

    for ((idx, value) in numbers.withIndex()) {
        println("${idx} ${value}")
    }
}

fun set() {
    val numbers = setOf(100, 200)
    val mutableSet = mutableSetOf(100, 200)
}

fun map() {
    val map = mutableMapOf<Int, String>()
    map[1] = "MONDAY"
    map[2] = "TUESDAY"

    val mapOf = mapOf(1 to "MONDAY", 2 to "TUESDAY") // A to B -> Pair(A, B)

    for (key in map.keys) {
        println(key)
        println(map[key])
    }

    for ((key, value) in map.entries) {
        println("${key} ${value}")
    }
}

// Collection Null
fun nullCollection() {
    val listA: List<Int?>   // Element can be null / List can't be null
    val listB: List<Int>?   // Element can't be null / List can be null
    val listC: List<Int?>?  // Both element and list can be null
}