package com.virtlink.gator.templates.handlebars

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.io.FileTemplateLoader
import com.github.jknack.handlebars.io.TemplateLoader
import com.google.inject.Inject
import com.virtlink.gator.templates.TemplateRepositoryLoader

class HandlebarsTemplateRepositoryLoader @Inject constructor(): TemplateRepositoryLoader {

    override fun loadFromDirectory(path: String): HandlebarsTemplateRepository
            = loadFromDirectory(path, null)

    /**
     * Loads a template repository from a directory.
     *
     * @param path The path of the repository.
     * @param extension The extension of the template files; or null to use the default ".hbs".
     * @return The template repository.
     */
    fun loadFromDirectory(path: String, extension: String?): HandlebarsTemplateRepository {
        val handlebars = Handlebars(FileTemplateLoader(path, extension ?: TemplateLoader.DEFAULT_SUFFIX))
        return HandlebarsTemplateRepository(handlebars)
    }

}