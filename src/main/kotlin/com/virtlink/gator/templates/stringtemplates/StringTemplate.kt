package com.virtlink.gator.templates.stringtemplates

import com.virtlink.gator.templates.Template
import com.virtlink.gator.templates.TemplateInstance


class StringTemplate(
        override val repository: StringTemplateRepository,
        val name: String
): Template {

    override fun createInstance(): TemplateInstance {
        return StringTemplateInstance(this)
    }

}