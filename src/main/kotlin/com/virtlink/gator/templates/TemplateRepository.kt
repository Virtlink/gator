package com.virtlink.gator.templates

/**
 * A repository of templates.
 */
interface TemplateRepository {

    /**
     * Loads the template with the specified name.
     *
     * @param name The name of the template.
     * @return The template; or null when it could not be loaded.
     */
    fun loadTemplate(name: String): Template?

    /**
     * Loads a template from a string.
     *
     * @param text The template text.
     * @return The template.
     */
    fun loadTemplateFromString(text: String): Template

}