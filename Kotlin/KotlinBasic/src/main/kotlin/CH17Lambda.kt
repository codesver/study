// Java to Kotlin Lambda
// Kotlin 에서는 함수가 그 자체로 값이 될 수 있기 때문에 변수에 할당하거나 파라미터로 넘길 수 있다.
// Lambda 는 함수명을 작성하지 않는다.
class Fruit(
    val name: String,
    val price: Int
)

fun lambda(fruits: List<Fruit>) {

    // Lambda
    val isApple: (Fruit) -> Boolean = fun(fruit: Fruit) = fruit.name == "apple"
    val isBanana: (Fruit) -> Boolean = { fruit: Fruit -> fruit.name == "banana" }

    isApple(Fruit("apple", 1000))
    isBanana.invoke(Fruit("banana", 1000))

    filterFruits(fruits, fun(fruit: Fruit) = fruit.name == "apple")
    filterFruits(fruits, {fruit: Fruit -> fruit.name == "apple" })
    filterFruits(fruits) { fruit: Fruit -> fruit.name == "apple" }
    filterFruits(fruits) { fruit -> fruit.name == "apple" }
    filterFruits(fruits) { it.name == "apple" }
}

fun filterFruits(fruits: List<Fruit>, filter: (Fruit) -> Boolean): List<Fruit> {
    val result = mutableListOf<Fruit>()
    for (fruit in fruits) {
        if (filter(fruit)) {
            result.add(fruit)
        }
    }
    return result
}

// Closure
// Kotlin 에서는 Java 와는 다르게 람다에서 사용하는 변수의 제약이 없다.
// Kotlin 은 람다가 시작하는 지점에 참조하고 있는 변수들을 모두 포획하여 그 정보를 가지고 있으며 이를 closure 라고 한다.
