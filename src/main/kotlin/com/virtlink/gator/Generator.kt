package com.virtlink.gator

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.type.TypeFactory
import com.google.inject.Inject
import org.apache.commons.io.FilenameUtils
import org.stringtemplate.v4.*
import org.stringtemplate.v4.misc.STMessage
import java.io.*
import java.net.URI

/**
 * Generates files based on a data file and a template.
 */
class Generator @Inject constructor(
        private val mapper: ObjectMapper
) {

    fun generate(filename: String) {
        generate(URI(filename))
//        val basePath = FilenameUtils.getFullPath(filename)
//        generate(File(filename).inputStream(), basePath)
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
                template.write(stWriter, object : STErrorListener {
                    override fun compileTimeError(msg: STMessage?) {
                        System.out.println(msg.toString())
                    }

                    override fun IOError(msg: STMessage?) {
                        System.out.println(msg.toString())
                    }

                    override fun internalError(msg: STMessage?) {
                        System.out.println(msg.toString())
                    }

                    override fun runTimeError(msg: STMessage?) {
                        System.out.println(msg.toString())
                    }

                })
            }
        }
    }

    fun getWriter(input: InputData, instance: InstanceData, basePath: String): Writer {
        val file = File(basePath, instance.output)
        return BufferedWriter(file.writer())
    }

    fun getSTWriter(input: InputData, instance: InstanceData, writer: Writer): STWriter {
        return when (instance.autoIndent ?: input.autoIndent ?: false) {
            true -> AutoIndentWriter(writer)
            false -> NoIndentWriter(writer)
        }
    }

    fun getTemplateGroup(input: InputData, instance: InstanceData?, basePath: String): STGroup {
        val templateGroup = instance?.templateGroup
                ?: input.templateGroup
                ?: FilenameUtils.getFullPath(input.template)
        val templateGroupPath = FilenameUtils.concat(basePath, templateGroup)
        val templateGroupFile = File(templateGroupPath)
        return when {
            // Use STRawGroupDir instead of STGroupDir to allow templates without headers.
            templateGroupFile.isDirectory -> STRawGroupDir(templateGroupPath,'$', '$')
            templateGroupFile.isFile -> STGroupFile(templateGroupPath, '$', '$')
            else -> throw FileNotFoundException("The template group directory or file was not found.")
        }
    }

    /**
     * Gets the template for this input data.
     *
     * Returns a cached copy of the template
     * with all values cleared.
     */
    fun getTemplate(input: InputData, instance: InstanceData, group: STGroup): ST {
        val templateName = FilenameUtils.getBaseName(instance.template ?: input.template ?: "template")

        return group.getInstanceOf(templateName)
    }

    /**
     * Populates the template with the values from the specified instance.
     *
     * @param template The template to populate.
     * @param instance The template instance data.
     */
    fun populateTemplate(input: InputData, instance: InstanceData, template: ST) {
        instance.data.forEach { k, v ->
            template.add(k, v)
        }
    }

}