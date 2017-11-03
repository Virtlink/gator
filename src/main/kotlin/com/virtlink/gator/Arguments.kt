package com.virtlink.gator

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.DefaultHelpFormatter

class Arguments(parser: ArgParser) {

    companion object {
        fun parseArguments(args: Array<String>): Arguments {
            val helpFormatter = DefaultHelpFormatter(
                    "Generates source files based on a configuration in a YAML/JSON file.",
                    "Copyright (C) 2017 - Daniel Pelsmaeker")
            val parser = ArgParser(args, ArgParser.Mode.GNU, helpFormatter)
            return Arguments(parser)
        }
    }

    /**
     * Gets the source filenames.
     */
    val sources by parser.positionalList("source filenames", 1..Int.MAX_VALUE)

}