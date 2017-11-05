package com.virtlink.gator.templates

/**
 * A template.
 */
interface Template {

    /**
     * The template repository of which this is a template.
     */
    val repository: TemplateRepository

    /**
     * Creates a new instance of the template.
     */
    fun createInstance(): TemplateInstance



}