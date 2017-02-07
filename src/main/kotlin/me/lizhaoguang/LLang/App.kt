package me.lizhaoguang.LLang
import java.io.StringReader

val code = """
even = 0
odd = 0
i = 1
while i < 10 {
    if i % 2 == 0 {
        even = even + i
    } else {
        odd = odd + i
    }
i = i + 1
}
even + odd
"""

val code1 = "a + b"

fun main(args: Array<String>) {
    val reader = StringReader(code)
    val lexer = Lexer(reader)

//    val num = Parser.rule().number()
//    val parser = Parser.rule().ast(num).sep("+").ast(num)

//    do {
//        val t = lexer.read()
//        println(t)
//    } while (t != EOFToken)

    val parser = BasicParser()
//    //val ast = parser.parse(lexer)
//
    while(lexer.peek(0) != EOFToken) {
        val ast = parser.parse(lexer)
        println(ast.toString())
    }

}
