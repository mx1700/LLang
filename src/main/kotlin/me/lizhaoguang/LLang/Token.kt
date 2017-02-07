package me.lizhaoguang.LLang

abstract class Token<out V>(val lineNumber: Int) {
    abstract val value: V;
    val text: String
        get() = value.toString()

    open fun isIdentifier(): Boolean {
        return false
    }

    open fun isNumber(): Boolean {
        return false
    }

    open fun isString(): Boolean {
        return false
    }

    open val number: Int
        get() {
            throw StoneException("not number token")
        }

    companion object {
        val EOL = "\\n"
    }
}

object EOFToken : Token<Nothing>(-1) {
    override val value: Nothing
        get() = throw UnsupportedOperationException()

    override fun toString(): String {
        return "EOFToken"
    }
}

class EOLToken(lineNumber: Int) : IdentifierToken(lineNumber, Token.EOL) {
    override fun toString(): String {
        return "EOLToken"
    }
}

class NumberToken(line: Int, override val value: Int) : Token<Int>(line) {
    override fun toString(): String {
        return "NumberToken($value)"
    }

    override fun isNumber() = true
    override val number: Int
        get() = value
}
class StringToken(line: Int, override val value: String) : Token<String>(line) {
    override fun toString(): String {
        return "StringToken($value)"
    }

    override fun isString(): Boolean {
        return true
    }
}
open class IdentifierToken(line: Int, override val value: String) : Token<String>(line){
    override fun toString(): String {
        return "IdentifierToken($value)"
    }

    override fun isIdentifier(): Boolean {
        return true
    }
}