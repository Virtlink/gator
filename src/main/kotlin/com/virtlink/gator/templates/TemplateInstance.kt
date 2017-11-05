package com.virtlink.gator.templates

import java.io.Writer

/**
 * An instance of a template.
 */
interface TemplateInstance {

    /**
     * The template of which this is an instance.
     */
    val template: Template

    /**
     * Sets the value of the specified key to the specified value.
     *
     * This replaces any value for the specified key.
     *
     * @param key The key to set.
     * @param value The value; or null to unset the value.
     */
    fun set(key: String, value: Any?)

    /**
     * Clears the value of the specified key.
     *
     * This replaces any value for the specified key.
     *
     * @param key The key to clear.
     */
    fun clear(key: String)
            = set(key, null)

    /**
     * Adds the value to the collection of the specified key.
     *
     * This adds the value to the specified key. If no values exist
     * for the key, this becomes the only value. If any value exists,
     * the key is associated with a list to which this value is added.
     */
    fun add(key: String, value: Any)

    /**
     * Writes the template to the specified writer.
     */
    fun write(writer: Writer)

}