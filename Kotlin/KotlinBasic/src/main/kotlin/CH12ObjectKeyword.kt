// Static
class Human(
    var name: String,
    var age: Int
) {
    companion object Factory : Log {              // 클래스 유일 동행 object
        private const val MIN_AGE = 1       // const : Compile 시에 할당 (기본 + String)

        @JvmStatic
        fun newBaby(name: String): Human {
            return Human(name, MIN_AGE)
        }

        override fun log() {
            println("I'm human class's companion object")
        }
    }
}

interface Log {
    fun log()
}

// Singleton
object Singleton {
    var a: Int = 0
}

// Anonymous 특정 인터페이스나 클래스를 상속받은 구현체를 일회성으로 사용할 때 쓰는 클래스
interface Movable {
    fun move()
    fun fly()
}

fun moveSomething(movable: Movable) {
    movable.move()
    movable.fly()
}

fun main() {
    moveSomething(object : Movable {
        override fun move() {
            TODO("Not yet implemented")
        }

        override fun fly() {
            TODO("Not yet implemented")
        }
    })
}
