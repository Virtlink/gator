package com.virtlink

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Creates a logger for the current class.
 *
 * Usage:
 *
 *     val LOG by logger()
 *
 */
fun <T : Any> T.logger(): Lazy<Logger> {
    return lazy { LoggerFactory.getLogger(this.javaClass.name) }
}