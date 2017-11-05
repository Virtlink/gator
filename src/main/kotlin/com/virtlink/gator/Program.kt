package com.virtlink.gator

import com.google.inject.Guice
import com.google.inject.Injector
import com.xenomachina.argparser.mainBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Suppress("PrivatePropertyName")
private val LOG = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)

/**
 * Entry point of the application.
 *
 * See [Arguments] for the command-line arguments.
 */
fun main(args: Array<String>) = mainBody("gator") {
    Arguments.parseArguments(args).run {
        configureLogger()
        if (this.sources.isEmpty()) {
            LOG.error("No sources specified.")
        }

        val injector = configureInjector()
        val processor = injector.getInstance(Processor::class.java)
        for (source in this.sources) {
            LOG.info("Generating $source")
            processor.generate(source)
        }
        LOG.info("Done!")
    }

}

/**
 * Configures the logger.
 */
private fun Arguments.configureLogger() {
    val rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as? ch.qos.logback.classic.Logger
    if (rootLogger != null) {
        rootLogger.level = this.verbosityLevel
    }
}

private fun Arguments.configureInjector(): Injector {
    return Guice.createInjector(GatorModule())
}