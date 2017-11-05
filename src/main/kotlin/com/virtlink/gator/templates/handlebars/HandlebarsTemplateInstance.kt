package com.virtlink.gator.templates.handlebars

import com.virtlink.gator.templates.TemplateInstanceBase
import java.io.Writer

class HandlebarsTemplateInstance(
        override val template: HandlebarsTemplate
) : TemplateInstanceBase() {

    override fun write(writer: Writer) {
        val data = getData()
        template.template.apply(data, writer)
    }

}