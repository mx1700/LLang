package me.lizhaoguang.LLang

abstract class ASTree : Iterable<ASTree> {
    abstract fun child(i: Int): ASTree
    abstract fun numChildren(): Int
    abstract fun children(): Iterator<ASTree>
    abstract fun location(): String?
    override fun iterator() = children()
}
