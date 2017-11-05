package com.virtlink.gator.templates

/**
 * base class for template instances.
 */
abstract class TemplateInstanceBase: TemplateInstance {

    private val data: MutableMap<String, Any> = mutableMapOf()

    /**
     * The data.
     */
    protected fun getData(): Map<String, Any> = this.data

    override fun set(key: String, value: Any?) {
        if (value != null) {
            data[key] = value
        } else {
            data.remove(key)
        }
    }

    override fun add(key: String, value: Any) {
        data.compute(key, { _, v ->
            when (v) {
                null -> value
                is AttributeList -> v.add(value)
                else -> AttributeList(listOf(v, value))
            }
        })
    }

}