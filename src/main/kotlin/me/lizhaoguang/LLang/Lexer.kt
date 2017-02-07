package me.lizhaoguang.LLang
import java.io.Reader
import java.io.LineNumberReader
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Lexer(reader: Reader) {
    private val reader = LineNumberReader(reader)
    private val queue = ArrayList<Token<*>>()
    private var hasMore = true
    val pattern = Pattern.compile(regexPat)!!

    companion object {
        val list = listOf(
                "(//.*)",                   //注释
                "([0-9]+)",                 //数字字面量
                "(\"[^\"]*\")",             //字符串
                "[A-Za-z][A-Za-z0-9]*",     //标识符
                "==|<=|>=|&&|\\|\\|",       //比较运算符
                "\\p{Punct}"
        );
        val regexPat = "\\s*(" + list.joinToString("|") + ")?"
    }

    fun read(): Token<*> {
        if (fillQueue(0)) {
            return queue.removeAt(0)
        } else {
            return EOFToken
        }
    }

    fun peek(i: Int): Token<*> {
        if (fillQueue(0)) {
            return queue[i]
        } else {
            return EOFToken
        }
    }

    private fun fillQueue(i: Int): Boolean {
        while (i >= queue.size) {
            if (hasMore) {
                loadLine()
            } else {
                return false
            }
        }
        return true
    }

    private fun loadLine() {
        val line = reader.readLine()
        if (line == null) {
            hasMore = false
            return
        }
        val lineNumber = reader.lineNumber
        val matcher = pattern.matcher(line)
        matcher.useTransparentBounds(true)
                .useAnchoringBounds(false)

        var pos = 0;
        val endPos = line.length
        while(pos < endPos) {
            matcher.region(pos, endPos)
            if (matcher.lookingAt()) {
                val token = getToken(lineNumber, matcher)
                if (token != null) {
                    queue.add(token)
                }
                pos = matcher.end()
            } else {
                throw Exception("解析错误")
            }
        }
        queue.add(EOLToken(lineNumber))
    }

    private fun getToken(lineNumber: Int, matcher: Matcher): Token<*>? {
//        (1..matcher.groupCount()).forEach {
//            println(matcher.group(it))
//        }
        val value = matcher.group(1);
        if (value == null || matcher.group(2) != null) {
            return null
        }

        return when {
            matcher.group(3) != null -> NumberToken(lineNumber, value.toInt())
            matcher.group(4) != null -> StringToken(lineNumber, value.substring(1, value.length - 1))
            else -> IdentifierToken(lineNumber, value)
        }
    }
}