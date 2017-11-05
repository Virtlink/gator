package com.virtlink.gator.templates.stringtemplates

import com.virtlink.gator.templates.TemplateInstanceBase
import org.stringtemplate.v4.AutoIndentWriter
import java.io.Writer

class StringTemplateInstance(
        override val template: StringTemplate
): TemplateInstanceBase() {

    override fun write(writer: Writer) {
        val instance = this.template.repository.group.getInstanceOf(this.template.name)
                ?: throw RuntimeException("Template ${this.template.name} not found in ${this.template.repository.group.rootDirURL}")
        val data = this.getData()
        for ((k, v) in data) {
            instance.add(k, v)
        }
        instance.write(AutoIndentWriter(writer))
    }

}