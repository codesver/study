import java.lang.IllegalArgumentException

// Extended Function
// fun CLASS_TO_EXTEND.FUNCTION_NAME(PARAMETER): TYPE
// 확장 함수는 private, protected property 를 가져올 수 없다.
// 확장 함수와 멤버 함수 signature 가 동일하면 멤버 함수를 호출
// 확장함수는 객체의 타입에 맞추어서 호출이 된다. (not instance)
// Java -> FileNameKt.function 으로 호출 가능
fun String.lastChar(): Char {
    return this[lastIndex]
}

class Class(val value: Int) {
//    fun doubleValue() = value * 2
}

fun Class.doubleValue(): Int {
    return this.value * 2
}

open class Parent
class Child : Parent()
fun Parent.whoAreYou() = "parent"
fun Child.whoAreYou() = "child"

fun parentAndChild() {
    val parentByParent: Parent = Parent()
    println(parentByParent.whoAreYou())     // parent
    val parentByChild: Parent = Child()
    println(parentByChild.whoAreYou())      // parent
    val childByChild: Child = Child()
    println(childByChild.whoAreYou())       // child
}

// infix 함수
// VAR.FUNCTION(ARGUMENT) 대신 VAR FUNCTION ARGUMENT 형태로 호출하는 것
// Member function 가능
infix fun Int.addInfix(other: Int) = this + other

fun add() {
    val added2 = 1 addInfix 1
}

// inline 함수
inline fun Int.addInline(other: Int) = this + other

// 지역 함수
// 함수 안에 함수를 선언
fun createPerson(firstName: String, lastName: String): Person {
    fun validateName(name: String, fieldName: String) {
        if (name.isEmpty())
            throw IllegalArgumentException("${fieldName} can't be empty. Name = ${name}")
    }

    validateName(firstName, "firstName")
    validateName(lastName, "lastName")
    return Person("${firstName} ${lastName}")
}