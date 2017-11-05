package com.virtlink.gator.templates.handlebars

import com.github.jknack.handlebars.Context
import com.github.jknack.handlebars.context.FieldValueResolver
import com.github.jknack.handlebars.context.JavaBeanValueResolver
import com.github.jknack.handlebars.context.MapValueResolver
import com.virtlink.gator.templates.TemplateInstanceBase
import java.io.Writer

class HandlebarsTemplateInstance(
        override val template: HandlebarsTemplate
) : TemplateInstanceBase() {

    override fun write(writer: Writer) {
        val data = getData()

        val context = Context
                .newBuilder(data)
                .push(CollectionSizeValueResolver.INSTANCE)
//                .resolver(
//                        MapValueResolver.INSTANCE,
//                        JavaBeanValueResolver.INSTANCE,
//                        FieldValueResolver.INSTANCE,
//                        CollectionSizeValueResolver.INSTANCE)
                .build()

        template.template.apply(context, writer)
    }

}