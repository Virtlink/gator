package com.virtlink.gator.templates

/**
 * A position in the document.
 *
 * @property line The zero-based line number.
 * @property character The zero-based character offset on the line.
 */
data class Position(val line: Int, val character: Int) {

    override fun toString(): String
            = "${line+1}:$character"

}