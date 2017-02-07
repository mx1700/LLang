package me.lizhaoguang.LLang

open class ASTLeaf(
        val token: Token<*>
) : ASTree() {
    override fun child(i: Int): ASTree {
        throw IndexOutOfBoundsException()
    }

    override fun numChildren() = 0

    override fun children() = emptyList<ASTree>().iterator()

    override fun toString() = token.text

    override fun location() = "at lineNumber ${token.lineNumber}"
}

class NumberLiteral(token: Token<*>) : ASTLeaf(token) {
    val value: Int = token.value as Int
}

class Name(token: Token<*>) : ASTLeaf(token) {
    val name = token.text
}

class StringLiteral(token: Token<*>) : ASTLeaf(token) {
    val value = token.text
}
