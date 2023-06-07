// [ Java Kotlin 차이 ]
// public -> 모든 곳에서 접근 가능
// protected -> 선언된 클래스 또는 하위 클래스에서만 접근 가능
// internal -> 같은 모듈에서만 접근 가능
// private -> 선언된 클래스 내에서만 접근 가능

// [ Kotlin 파일 접근 제어 ]
// public -> 기본값으로 어디서든 접근이 가능
// protected -> 파일에는 사용 불가능
// internal -> 같은 모듈에서만 접근 가능
// private -> 같은 파일에서만 접근 가능

// [ 다양한 구성요소의 접근 제어 ]
// Class member -> public(∀), protected(선언된 클래스 또는 하위 클래스), internal(같은 모듈), private(선언된 클래스)
// Constructor -> Class member 와 동일하지만 constructor keyword 생략 불가능
// 유틸성 클래스 대신 파일 최상단에 함수를 작성
fun isDirectoryPath(path: String): Boolean {
    return path.endsWith("/")
}
// Property -> 동일하지만 2가지 방법으로 설정
class Car(
    val name: String,
    var owner: String,
    price: Int
) {
    var price = price
        private set
}

// Java Kotlin 같이 사용할 때 주의