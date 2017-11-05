package com.virtlink.gator

import com.fasterxml.jackson.databind.ObjectMapper
import com.virtlink.gator.templates.stringtemplates.StringTemplateErrorListener
import com.virtlink.gator.templates.stringtemplates.StringTemplateRepositoryLoader

abstract class StringTemplatesTests {

    protected fun createProcessor(mapper: ObjectMapper): Processor {
        val generatorFactory = object: Generator.Factory {
            override fun create(basePath: String, input: InputData): Generator {
                return Generator(basePath, input, StringTemplateRepositoryLoader(StringTemplateErrorListener()))
            }
        }
        return Processor(mapper, generatorFactory)
    }

}