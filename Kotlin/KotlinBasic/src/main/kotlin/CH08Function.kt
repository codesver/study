import java.io.BufferedReader
import java.io.FileReader

// Function Declaration
fun max(a: Int, b: Int) = if (a > b) a else b

// default parameter
fun repeat(str: String, num: Int = 3, useNewLine: Boolean = true) {
    for (i in 1..num) {
        if (useNewLine) {
            println(str)
        } else {
            print(str)
        }
    }
}

fun repeatTestWithStr(str: String) {
    repeat(str)
}

// named argument (parameter)
fun repeatTestWithStrAndUseNewLine(str: String, useNewLine: Boolean) {
    repeat(str, useNewLine = useNewLine)
}

// Variable Arguments
fun printAll(vararg strings: String) {
    for (string in strings) {
        println(string)
    }
}

fun testVarargs(array: Array<String>) {
    printAll(*array) // Spread 연산자
}
