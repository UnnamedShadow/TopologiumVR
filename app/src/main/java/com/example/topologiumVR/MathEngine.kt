package com.example.topologiumVR

//enum class Operator(symbol: Char, biBuiltin: String, unBuiltin: String) {
//    Add('+', "add", "positive"),
//    Mul('*', "multiply", "conjugate"),
//    Sub('-', "subtract", "negative"),
//    Div('/', "divide", "inverse"),
//}
//
//enum class InvocationType { Macro, Newton, Builtin }
//
//sealed class Token{
//    data class Invocation(val name: String, val args: List<String>, val type: InvocationType): Token()
//    data class Inner(val data: String): Token()
//    data class Op(val op: Operator): Token()
//    data class Literal(val text: String): Token()
//    data class Variable(val name: Char)
//}
//
//sealed class MathExpression {
//    data class Variable(val name: Char): MathExpression()
//    data class Literal(val value: Double): MathExpression()
//
//    data class MacroInvocation(val name: String, val args: List<String>): MathExpression()
//    data class NewtonInvocation(val op: NewtonApi.Operation, val exp: String): MathExpression()
//    data class BuiltinInvocation(val builtin: String, val args: List<String>): MathExpression()
//}

sealed class LCExpression {
}

fun parse()
