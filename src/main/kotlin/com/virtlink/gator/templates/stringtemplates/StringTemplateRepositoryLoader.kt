package com.virtlink.gator.templates.stringtemplates

import com.google.inject.Inject
import com.virtlink.gator.templates.TemplateRepositoryLoader
import org.stringtemplate.v4.STErrorListener
import org.stringtemplate.v4.STGroupDir

class StringTemplateRepositoryLoader @Inject constructor(
        private val errorListener: STErrorListener
): TemplateRepositoryLoader {

    override fun loadFromDirectory(path: String): StringTemplateRepository
            = loadFromDirectory(path, '$', '$')

    /**
     * Loads a template repository from a directory.
     *
     * @param path The path of the repository.
     * @param delimiterStartChar The start delimiter character.
     * @param delimiterStopChar The end delimiter character.
     * @return The template repository.
     */
    fun loadFromDirectory(path: String, delimiterStartChar: Char, delimiterStopChar: Char): StringTemplateRepository {
        val group = STGroupDir(path, delimiterStartChar, delimiterStopChar)
        group.listener = errorListener
        return StringTemplateRepository(group)
    }
}