package com.virtlink.gator.templates.stringtemplates

import com.virtlink.gator.templates.Template
import com.virtlink.gator.templates.TemplateNotFoundException
import com.virtlink.gator.templates.TemplateRepository
import org.stringtemplate.v4.STGroup
import org.stringtemplate.v4.STGroupString
import java.util.*

class StringTemplateRepository(
    val group: STGroup
): TemplateRepository {

    override fun loadTemplate(name: String): StringTemplate? {
        if (!this.group.isDefined(name))
            throw TemplateNotFoundException(name, this.group.rootDirURL.toString())
        return StringTemplate(this, name)
    }

    override fun loadTemplateFromString(text: String): Template {
        val name = UUID.randomUUID().toString()
        group.importTemplates(STGroupString(name, text))
        return StringTemplate(this, name)
    }

}