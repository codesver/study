class PersonConstructors(
    val name: String,
    var age: Int
) {
    init {
        if (age <= 0) throw IllegalArgumentException("나이는 ${age}일 수 없습니다.")
        println("Initialize Block")
    }

    constructor(name: String) : this(name, 1) {
        println("Primary Constructor")
    }

    constructor() : this("홍길동") {
        println("Secondary Constructor")
    }
}

class PersonCustomGetterSetter(
    name: String = "홍길동",
    var age: Int = 1
) {
    init {
        if (age <= 0) throw IllegalArgumentException("Age cannot be ${age}")
    }

//    val name: String = name
//        get() = field.uppercase() // Backing Field

    // Custom Setter
    var name: String = name
        set(value) {
            field = value.uppercase()
        }

    // Custom Getter
    val isAdult: Boolean
        get() = age >= 20

    val uppercaseName: String
        get() = name.uppercase()

    fun isAdult(): Boolean = age >= 20
}

class Person(
    val name: String = "홍길동",
    var age: Int = 1
) {
    init {
        if (age <= 0) throw IllegalArgumentException("Age cannot be ${age}")
    }

    val isAdult: Boolean
        get() = age >= 0

    val uppercaseName: String
        get() = name.uppercase()
}