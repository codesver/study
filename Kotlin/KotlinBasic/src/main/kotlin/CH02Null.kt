import data.Person

// Null Check
fun startsWithA1(str: String?): Boolean {
//    if (str == null) throw IllegalAccessException("It is null")
//    return str.startsWith("A")
    return str?.startsWith("A") ?: throw IllegalAccessException("It is null")
}

fun startsWithA2(str: String?): Boolean? {
//    if (str == null) return null
//    return str.startsWith("A")
    return str?.startsWith("A")
}

fun startsWithA3(str: String?): Boolean {
//    if (str == null) return false
//    return str.startsWith("A")
    return str?.startsWith("A") ?: false
}

// Safe Call and Elvis Operator
fun lengthOfString(str: String?): Int {
    println(str?.length)    // Print str.length or null if str is null
    return str?.length ?: 0
}

// Not-Null!!
fun startsWithA(str: String?): Boolean {
    return str!!.startsWith("A")
}

// Platform Type
fun platformType() {
    val person = Person("codesver")
    val name: String = person.name
    startsWithA(name)
}