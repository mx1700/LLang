package me.lizhaoguang.LLang

open class ASTList(
        protected val children: List<ASTree>
) : ASTree() {

    override fun child(i: Int) = children[i]

    override fun numChildren() = children.size

    override fun children() = children.iterator()

    override fun toString() = children.joinToString(" ", "(", ")")

    override fun location() = children.find { it.location() != null }?.location()
}

class BinaryExpr(children: List<ASTree>) : ASTList(children) {
    val left = children[0]
    val operator = children[1]
    val right = children[2]
}

class PrimaryExpr(children: List<ASTree>) : ASTList(children) {
    companion object {
        @JvmStatic fun create(c: List<ASTree>): ASTree {
            return if (c.size == 1) c[0] else PrimaryExpr(c)
        }
    }
}

class NegativeExpr(children: List<ASTree>) : ASTList(children) {
    fun operand(): ASTree = child(0)
    override fun toString(): String {
        return "-" + operand()
    }
}

class BlockStmnt(children: List<ASTree>) : ASTList(children)

class IfStmnt (children: List<ASTree>) : ASTList(children) {
    fun condition() = child(0)
    fun thenBlock() = child(1)
    fun elseBlock() = if (numChildren() > 2) child(2) else null
    override fun toString(): String {
        return "(if " + condition() + " " + thenBlock() + " else " + elseBlock() + ")"
    }
}

class WhileStmnt(children: List<ASTree>) : ASTList(children) {
    fun condition() = child(0)
    fun body() = child(1)
    override fun toString(): String {
        return "(while " + condition() + " " + body() + ")"
    }
}

class NullStmnt(children: List<ASTree>) : ASTList(children)