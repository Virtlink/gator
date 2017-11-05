package com.virtlink.gator

import com.fasterxml.jackson.databind.ObjectMapper
import com.virtlink.gator.templates.handlebars.HandlebarsTemplateRepositoryLoader

abstract class HandlebarsTests {

    protected fun createProcessor(mapper: ObjectMapper): Processor {
        val generatorFactory = object: Generator.Factory {
            override fun create(basePath: String, input: InputData): Generator {
                return Generator(basePath, input, HandlebarsTemplateRepositoryLoader())
            }
        }
        return Processor(mapper, generatorFactory)
    }

}