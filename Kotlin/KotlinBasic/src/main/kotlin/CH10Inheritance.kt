abstract class Animal(
    protected val species: String,
    protected open val legCount: Int
) {
    abstract fun move()
}

class Cat(species: String) : Animal(species, 4) {
    override fun move() {
        println("고양이가 사뿐 사뿐 걸어가")
    }
}

class Penguin(
    species: String,
    private val wingCount: Int = 2
) : Animal(species, 2), Fly, Swim {
    override fun move() {
        println("펭귄이 움직인다.")
    }

    override val legCount: Int
        get() = super.legCount + wingCount

    override fun act() {
        super<Swim>.act()
        super<Fly>.act()
    }

    override val ability: Int
        get() = 4
}

interface Fly {
    fun act() {
        println("파닥 파닥")
    }
}

interface Swim {

    val ability: Int
        get() = 3

    fun act() {
        println("어푸 어푸")
    }
}

open class Base(
    open val number: Int = 100
) {
    init {
        println("Base")
        println(number)
    }
}

class Derived(
    override val number: Int = 20
) : Base(number) {
    init {
        println("Derived")
    }
}

fun main() {
    Derived(300)
}