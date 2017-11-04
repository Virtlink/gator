package com.virtlink.gator

import ch.qos.logback.classic.Level
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
    val sources by parser.positionalList("SOURCES", help = "filenames of source YAML files", sizeRange = 0..Int.MAX_VALUE)

    /**
     * Whether to enable verbose mode.
     * This prints ERRORs, WARNings, and INFOs.
     */
    val verbose by parser.flagging("-v", "--verbose", help = "enable verbose mode")

    /**
     * Whether to enable quiet mode.
     * This prints only ERRORs.
     */
    val quiet by parser.flagging("-q", "--quiet", help = "enable quiet mode")

    /**
     * Gets the verbosity level.
     */
    val verbosityLevel: Level
        get() = when {
            this.quiet -> Level.ERROR
            this.verbose -> Level.INFO
            else -> Level.WARN
        }

}