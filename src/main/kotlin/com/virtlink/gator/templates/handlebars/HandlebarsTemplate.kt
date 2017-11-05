package com.virtlink.gator.templates.handlebars

import com.virtlink.gator.templates.Template

class HandlebarsTemplate(
        override val repository: HandlebarsTemplateRepository,
        val template: com.github.jknack.handlebars.Template
) : Template {

    override fun createInstance(): HandlebarsTemplateInstance {
        return HandlebarsTemplateInstance(this)
    }

}