import file.a.printHelloWorld as printHelloWorldA
import file.b.printHelloWorld as printHelloWorldB

// Type Alias & as import
fun filterItems(item: Item, filter: (Item) -> Boolean) {

}

typealias ItemFilter = (Item) -> Boolean

fun filterItemsWithTypeAlias(item: Item, filter: ItemFilter) {

}

data class UltraSuperGuardianTribe(val name: String)

typealias USGTMap = Map<String, UltraSuperGuardianTribe>

fun main() {
    val a: USGTMap = HashMap()
}

fun helloWorld() {
    printHelloWorldA()
    printHelloWorldB()
}

// 구조분해, componentN
// Data class 기본적으로 componentN 함수를 만들어 준다.
// Data class 가 아니면 componentN 연산자를 직접 구현할 수 있다.
class Data(
    val string: String,
    val int: Int
) {
    operator fun component1() = string
    operator fun component2() = int
}

fun destructuring() {
    val data = Data("string", 0)
    val (string, int) = Data("string", 0)
    // val string = data.component1()
    // val int = data.component2()
}

// Jump & Label
fun jump(numbers: List<Int>) {
    run {
        numbers.forEach { number ->
            if (number == 2) return@run
            else if (number == 3) return@forEach
        }
    }
}

fun label() {
    abc@ for (i in 1..100) {
        for (j in 1..100) {
            if (j == 2)
                break@abc
            println("${i}, ${j}")
        }
    }
}

// TakeIf && TakeUnless
fun takeIf(number: Int) = number.takeIf { it > 0 }

fun takeUnless(number: Int) = number.takeUnless { it <= 0 }