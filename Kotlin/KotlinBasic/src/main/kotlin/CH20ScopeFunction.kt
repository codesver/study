// What is scope function
// 일시적인 영역을 형성하는 함수
fun printItem(item: Item?) {
    if (item != null) {
        println(item.name)
        println(item.currentPrice)
    }
}

fun printItemWithLet(item: Item?) {
    item?.let {
        println(it.name)
        println(it.currentPrice)
    }
}

// Scope function 분류
// let run also apply with
// let & run return lambda's result
// also & apply return object itself
fun scopeFunction(data: Data) {
    val stringWithIntLet: String = data.let { "${it.string} ${it.int}" }
    val stringWithIntRun: String = data.run { "${string} ${this.int}" }
    val dataAlso: Data = data.also { it.string }
    val dataApply: Data = data.apply { this.string + int }
    with(data) {
        println(string)
        println(this.int)
    }
}

// use when?

fun letFunction(strings: List<String>, string: String?, numbers: List<String>) {
    // let 은 하나 이상의 함수를 call chain 결과로 호출 할 때
    strings.map { it.length }
        .filter { it > 3 }
        .let { lengths -> println(lengths) }

    // non-null 값에 대해서만 code block 실행 할 때
    val length = string?.let {
        println(it.uppercase())
        it.length
    }

    // 일회성으로 제한된 영역에 지역 변수를 만들 때
    numbers.first()
        .let { firstItem ->
            if (firstItem.length >= 5) firstItem else "!${firstItem}!"
        }.uppercase()
}

// run 은 객체 초기화와 반환 값의 계산을 동시에 해야 할 때
fun runFunction(item: Item) {
    val itemRan: Item = item.run {
        // Save repository (it returns saved entity)
        this
    }
}

// apply 는 객체를 수정하는 로직이 call chain 중간에 필요할 때
data class DataApply(var value: Int)
fun applyFunction() {
   fun createData(value: Int): DataApply {
       return DataApply(value).apply { this.value *= 10 }
   }
}

// also 는 객체를 수정하는 로직이 call chaining 중간에 필요할 때
fun alsoFunction() {
    mutableListOf("one", "two", "three")
        .also { println("${it}") }
        .add("four")
}

// with 는 특정 객체를 다른 객체로 변환하야 하는데, 모듈 간의 의종성에 의해 정적 팩토리 혹은 toClass 함수를 만들기 어려울 때
data class DataDto(val value: Int = 0)
fun withFunction(data: DataApply): DataDto = with(data) {
    DataDto(value)
}