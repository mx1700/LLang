package me.lizhaoguang.LLang

import java.lang.RuntimeException

class StoneException(msg: String, tree: ASTree? = null) :
        RuntimeException(msg +
                (if (tree != null) " " + tree.location()
                else ""))
