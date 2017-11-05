package com.virtlink.gator.templates.handlebars

import com.github.jknack.handlebars.ValueResolver
import com.github.jknack.handlebars.ValueResolver.UNRESOLVED
import java.util.LinkedHashMap
import com.sun.corba.se.impl.util.RepositoryId.cache
import java.util.Collections.emptySet
import com.github.jknack.handlebars.context.MethodValueResolver



class CollectionSizeValueResolver: ValueResolver {

    companion object {
        /**
         * The default instance.
         */
        @JvmStatic val INSTANCE: ValueResolver = CollectionSizeValueResolver()
    }

    override fun resolve(context: Any, name: String): Any {
        if (name != "length")
            return UNRESOLVED

        return when (context) {
            is Collection<*> -> context.size
            is Array<*> -> context.size
            else -> UNRESOLVED
        }
    }

    override fun resolve(context: Any): Any {
        return when (context) {
//            is Collection<*> -> context
//            is Array<*> -> context
            else -> UNRESOLVED
        }
    }

    override fun propertySet(context: Any): MutableSet<MutableMap.MutableEntry<String, Any>> {
        val length = resolve(context, "length")
        if (length == UNRESOLVED)
            return emptySet()
        val propertySet = LinkedHashMap<String, Any>()
        propertySet.put("length", length)
        return propertySet.entries
    }
}