package com.virtlink.gator.templates.handlebars

import com.github.jknack.handlebars.Handlebars
import com.virtlink.gator.templates.Template
import com.virtlink.gator.templates.TemplateNotFoundException
import com.virtlink.gator.templates.TemplateRepository
import java.io.IOException

class HandlebarsTemplateRepository(
        val handlebars: Handlebars
): TemplateRepository {

    override fun loadTemplate(name: String): HandlebarsTemplate? {
        return try {
            val template = handlebars.compile(name)
            HandlebarsTemplate(this, template)
        } catch (e: IOException) {
            throw TemplateNotFoundException(name, handlebars.loader.prefix, null, e)
        }
    }

    override fun loadTemplateFromString(text: String): Template {
        val template = handlebars.compileInline(text)
        return HandlebarsTemplate(this, template)
    }

}