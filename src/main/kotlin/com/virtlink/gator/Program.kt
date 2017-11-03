package com.virtlink.gator

import com.google.inject.Guice
import com.google.inject.Injector
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody

/**
 * Entry point of the application.
 */
fun main(args: Array<String>) = mainBody("gator") {
    Arguments.parseArguments(args).run {
        val injector = Guice.createInjector(GatorModule())
        println("Start")
        val generator = injector.getInstance(Generator::class.java)
        println("Done!")
    }

}