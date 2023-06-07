// Data class
data class PersonDto(
    val name: String,
    val age: Int
)

// Enum class
enum class Country(
    private val code: String
) {
    KOREA("KO"),
    AMERICA("US"),
    JAPAN("JP")
}

fun handleCountry(country: Country) {
    when(country) {
        Country.KOREA -> TODO()
        Country.AMERICA -> TODO()
        Country.JAPAN -> TODO()
    }
}

// Sealed Class, Sealed Interface
// 컴파일 타임 떄 하위 클래스의 타입을 모두 기억하고 런타임 때 클래스 타입이 추가 될 수 없다.
// 하위 클래스는 같은 패키지에 있어야 한다.
// 클래스를 상속받을 수 있고 하위 클래스는 멀티 인스턴스가 가능하다는 점에서 enum 과 다르다.
sealed class HyundaiCar(
    val name: String,
    val price: Long
)

class Avante : HyundaiCar("avante", 1_000L)

class Sonata : HyundaiCar("sonota", 2_000L)

class Granduer : HyundaiCar("granduer", 3_000L)