package cl.ravenhill

import kotlinx.datetime.Clock

fun main(args: Array<String>) {
    println(Clock.System.now())
    for (arg in args) {
        println(arg)
    }
}
