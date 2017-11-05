package com.virtlink.gator

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.virtlink.gator.templates.Template
import com.virtlink.gator.templates.TemplateInstance
import com.virtlink.gator.templates.TemplateRepository
import com.virtlink.gator.templates.TemplateRepositoryLoader
import com.virtlink.logger
import java.io.BufferedWriter
import java.io.File
import java.io.Writer
import java.nio.file.FileSystems

/**
 * Generates files based on a data file and a template.
 */
class Generator @Inject constructor(
        @Assisted private val basePath: String,
        @Assisted private val input: InputData,
        private val templateRepositoryLoader: TemplateRepositoryLoader
) {

    interface Factory {
        fun create(basePath: String, input: InputData): Generator
    }

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    fun generate() {
        val repository = getTemplateRepository()
        val template = getTemplate(repository) ?: return

        for (instance in this.input.instances) {
            val templateInstance = template.createInstance()
            populateTemplate(instance, templateInstance)
            getWriter(instance).use {
                templateInstance.write(it)
            }
        }
    }

    /**
     * Gets the output writer.
     *
     * @param instance The instance data, which specifies the output filename.
     * @return The writer.
     */
    private fun getWriter(instance: InstanceData): Writer {
        val file = File(this.basePath, instance.output)
        return BufferedWriter(file.writer())
    }

    /**
     * Gets the template repository.
     *
     * @return The template repository.
     */
    private fun getTemplateRepository(): TemplateRepository {
        val repositoryDir = this.input.repository ?: ""
        val absoluteRepositoryDir = FileSystems.getDefault()
                .getPath(this.basePath, repositoryDir)
                .toAbsolutePath().toString()
        return this.templateRepositoryLoader.loadFromDirectory(absoluteRepositoryDir)
    }

    /**
     * Gets the template from the specified repository.
     *
     * @param repository The template repository.
     * @return The template; or null when not found.
     */
    private fun getTemplate(repository: TemplateRepository): Template? {
        val templateName = this.input.template ?: "template"
        val template = repository.loadTemplate(templateName)

        if (template == null) {
            LOG.error("Template with name $templateName not found.")
            return null
        }
        return template
    }

    /**
     * Populates the template with the values from the specified instance.
     *
     * @param instance The template instance data.
     * @param templateInstance The template instance to populate.
     */
    private fun populateTemplate(instance: InstanceData, templateInstance: TemplateInstance) {
        instance.data.forEach { k, v ->
            templateInstance.add(k, v)
        }
    }

}