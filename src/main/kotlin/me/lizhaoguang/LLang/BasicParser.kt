package me.lizhaoguang.LLang

import me.lizhaoguang.LLang.Parser.rule


class BasicParser {
    private val reserved = HashSet<String>()
    private val operators = Parser.Operators()
    private val expr0 = rule()
    private val primary = rule(PrimaryExpr::class.java)
            .or(
                    rule().sep("(").ast(expr0).sep(")"),
                    rule().number(NumberLiteral::class.java),
                    rule().identifier(Name::class.java, reserved),
                    rule().string(StringLiteral::class.java)
            )

    //负号支持
    private val factor = rule().or(
            rule(NegativeExpr::class.java).sep("-").ast(primary),
            primary
    )

    private val expr = expr0.expression(BinaryExpr::class.java, factor, operators)
    val statement0 = rule()

    //块结构
    val block = rule(BlockStmnt::class.java)
            .sep("{").option(statement0)
            .repeat(rule().sep(";", Token.EOL).option(statement0))
            .sep("}")
    val simple = rule(PrimaryExpr::class.java).ast(expr)
    val statement = statement0.or(
            //if
            rule(IfStmnt::class.java).sep("if").ast(expr).ast(block)
                    .option(rule().sep("else").ast(block)),
            rule(WhileStmnt::class.java).sep("while").ast(expr).ast(block),
            simple
    )
    val program = rule().or(statement, rule(NullStmnt::class.java)).sep(";", Token.EOL)

    init {
        reserved.add(";")
        reserved.add("}")
        reserved.add(Token.EOL)

        operators.add("=",  1, Parser.Operators.RIGHT)
        operators.add("==", 2, Parser.Operators.LEFT)
        operators.add(">",  2, Parser.Operators.LEFT)
        operators.add("<",  2, Parser.Operators.LEFT)
        operators.add("+",  3, Parser.Operators.LEFT)
        operators.add("-",  3, Parser.Operators.LEFT)
        operators.add("*",  4, Parser.Operators.LEFT)
        operators.add("/",  4, Parser.Operators.LEFT)
        operators.add("%",  4, Parser.Operators.LEFT)
    }

    fun parse(lexer: Lexer): ASTree {
        return program.parse(lexer)
    }

}
