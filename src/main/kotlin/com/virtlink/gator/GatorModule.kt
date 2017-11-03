package com.virtlink.gator

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.google.inject.AbstractModule
import com.google.inject.Singleton

/**
 * Dependency injection module.
 */
class GatorModule: AbstractModule() {
    override fun configure() {
        configureObjectMapper()
        configureGenerator()
    }

    private fun configureObjectMapper() {
        // Configure the mapper here
        val mapper = YAMLMapper()
//        val mapper = ObjectMapper()
        // Configuration no longer allowed here

        bind(ObjectMapper::class.java).toInstance(mapper)
    }

    private fun configureGenerator() {
        bind(Generator::class.java).`in`(Singleton::class.java)
    }
}