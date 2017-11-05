package com.virtlink.gator.templates

/**
 * Loads template repositories.
 */
interface TemplateRepositoryLoader {

    /**
     * Loads a template repository from a directory.
     *
     * @param path The path of the repository.
     * @return The template repository.
     */
    fun loadFromDirectory(path: String): TemplateRepository

}