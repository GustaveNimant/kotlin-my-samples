// https://kotlinlang.org/docs/reference/reflection.html

package com.javasampleapproach.kotlin.base64

val x = 1

fun main() {
    println(::x.get()) // 1
    println(::x.name)  // x
}