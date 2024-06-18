package cl.ravenhill.echo

import cl.ravenhill.util.id

fun main(args: Array<String>) {
    for (arg in args) {
        println(id(arg))
    }
}

