import data.Person

// 기본 타입
fun type() {
    // 자동 타입 설정
    val int = 3
    val long = 3L
    val float = 3.0f
    val double = 3.0

    // 타입 변환 (명시적 변환 필수)
    val number = 3
    val longNumber = number.toLong()
}

// 타입 캐스팅
fun printAgeIfPerson(obj: Any?) {
    if (obj is Person) {
        println(obj.age)
    }

    val person = obj as? Person
    println(person?.age)
}

fun castToPerson(obj: Any?): Person? {
    return obj as? Person
}

// Kotlin 3가지 특이한 타입
fun cast(obj: Any?): Person? = obj as? Person

fun print(content: String): Unit {
    println("content = $content")
}

fun fail(): Nothing = throw IllegalArgumentException()

// String interpolation, String indexing
fun interpolation() {
    val person = Person("codesver", 25)
    println("Person name is ${person.name} and age is ${person.age}")
}

fun indent() {
    val indent = """
        ABC
        DEF
    """.trimIndent()
    println("indent = $indent")
}

fun charAt(str: String, idx: Int) = str[idx]