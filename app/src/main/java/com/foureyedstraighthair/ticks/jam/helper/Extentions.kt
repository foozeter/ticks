package com.foureyedstraighthair.ticks.jam.helper

fun String.snakeToCamel(): String {
    val camel = StringBuilder()
    this.split('_')
        .forEachIndexed { i, w ->
            if (i == 0) camel.append(w)
            else w.forEachIndexed { j, c ->
                if (j == 0) camel.append(c.toUpperCase())
                else camel.append(c)
            }
        }

    return camel.toString()
}