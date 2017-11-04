package com.virtlink.gator

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import com.virtlink.logger
import org.apache.commons.io.FilenameUtils
import org.stringtemplate.v4.*
import java.io.*
import java.net.URI

/**
 * Generates files based on a data file and a template.
 */
class Generator @Inject constructor(
        private val mapper: ObjectMapper,
        private val templateErrorListener: TemplateErrorListener
) {

    companion object {
        private const val START_DELIMITER = '$'
        private const val END_DELIMITER = '$'
    }

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    fun generate(filename: String) {
        generate(URI(filename))
    }

    fun generate(filename: URI) {
        val file = File(filename)
        val basePath = FilenameUtils.getFullPath(file.path)
        val inputStream = file.inputStream()
        generate(inputStream, basePath)
    }

    fun generate(inputStream: InputStream, basePath: String) {
        generate(BufferedReader(InputStreamReader(inputStream)), basePath)
    }

    fun generate(data: Reader, basePath: String) {
        val reader = mapper.readerFor(InputData::class.java)
        val inputData = reader.readValue<InputData>(data)

        generate(inputData, basePath)
    }


    fun generate(input: InputData, basePath: String) {
        val globalGroup = getTemplateGroup(input, null, basePath)

        for (instance in input.instances) {
            val group = if (instance.templateGroup != null) getTemplateGroup(input, instance, basePath) else globalGroup
            val template = getTemplate(input, instance, group)
            populateTemplate(input, instance, template)
            getWriter(input, instance, basePath).use {
                val stWriter = getSTWriter(input, instance, it)
                template.write(stWriter, templateErrorListener)
            }
        }
    }

    private fun getWriter(input: InputData, instance: InstanceData, basePath: String): Writer {
        val file = File(basePath, instance.output)
        return BufferedWriter(file.writer())
    }

    private fun getSTWriter(input: InputData, instance: InstanceData, writer: Writer): STWriter {
        return when (instance.autoIndent ?: input.autoIndent == true) {
            true -> AutoIndentWriter(writer)
            false -> NoIndentWriter(writer)
        }
    }

    private fun getTemplateGroup(input: InputData, instance: InstanceData?, basePath: String): STGroup {
        val templateGroup = instance?.templateGroup
                ?: input.templateGroup
                ?: FilenameUtils.getFullPath(input.template)
        val templateGroupPath = FilenameUtils.concat(basePath, templateGroup)
        val templateGroupFile = File(templateGroupPath)
        return when {
            templateGroupFile.isDirectory -> STGroupDir(templateGroupPath,START_DELIMITER, END_DELIMITER)
            templateGroupFile.isFile -> STGroupFile(templateGroupPath, START_DELIMITER, END_DELIMITER)
            else -> throw FileNotFoundException("The template group directory or file was not found.")
        }
    }

    /**
     * Gets the template for this input data.
     *
     * Returns a cached copy of the template
     * with all values cleared.
     */
    private fun getTemplate(input: InputData, instance: InstanceData, group: STGroup): ST {
        val templateName = FilenameUtils.getBaseName(instance.template ?: input.template ?: "template")

        return group.getInstanceOf(templateName)
    }

    /**
     * Populates the template with the values from the specified instance.
     *
     * @param template The template to populate.
     * @param instance The template instance data.
     */
    private fun populateTemplate(input: InputData, instance: InstanceData, template: ST) {
        instance.data.forEach { k, v ->
            template.add(k, v)
        }
    }

}