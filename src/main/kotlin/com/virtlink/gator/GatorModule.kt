package com.virtlink.gator

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.virtlink.gator.templates.handlebars.HandlebarsTemplateRepositoryLoader
import com.virtlink.gator.templates.stringtemplates.StringTemplateErrorListener
import com.virtlink.gator.templates.stringtemplates.StringTemplateRepositoryLoader
import org.stringtemplate.v4.STErrorListener
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.virtlink.gator.templates.TemplateRepositoryLoader


/**
 * Dependency injection module.
 */
class GatorModule: AbstractModule() {
    override fun configure() {
        configureObjectMapper()
        configureGenerator()
        configureStringTemplateEngine()
        configureHandlebarsTemplateEngine()

        // Handlebars
        bind(TemplateRepositoryLoader::class.java).to(HandlebarsTemplateRepositoryLoader::class.java)
    }

    private fun configureObjectMapper() {
        val mapper = YAMLMapper()
        bind(ObjectMapper::class.java).toInstance(mapper)
    }

    private fun configureGenerator() {
        install(FactoryModuleBuilder()
                .implement(Generator::class.java, Generator::class.java)
                .build(Generator.Factory::class.java))
    }

    private fun configureStringTemplateEngine() {
        bind(StringTemplateRepositoryLoader::class.java).`in`(Singleton::class.java)
        bind(STErrorListener::class.java).to(StringTemplateErrorListener::class.java).`in`(Singleton::class.java)
    }

    private fun configureHandlebarsTemplateEngine() {
        bind(HandlebarsTemplateRepositoryLoader::class.java).`in`(Singleton::class.java)
    }
}