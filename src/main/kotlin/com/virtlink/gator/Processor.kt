package com.virtlink.gator

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import java.io.*
import java.net.URI

class Processor @Inject constructor(
        private val mapper: ObjectMapper,
        private val generatorFactory: Generator.Factory
) {
    fun generate(inputFilename: String)
            = generate(inputFilename, null)

    fun generate(inputFilename: String, basePath: String?) {
        val file = File(basePath, inputFilename)
        file.inputStream().use {
            generate(it, basePath ?: file.parent)
        }
    }

    fun generate(inputUri: URI)
            = generate(inputUri, null)

    fun generate(inputUri: URI, basePath: String?) {
        val file = if (basePath != null) File(File(basePath).toURI().resolve(inputUri)) else File(inputUri)
        file.inputStream().use {
            generate(it, basePath ?: file.parent)
        }
    }

    fun generate(inputStream: InputStream)
            = generate(inputStream, System.getProperty("user.dir"))

    fun generate(inputStream: InputStream, basePath: String) {
        generate(BufferedReader(InputStreamReader(inputStream)), basePath)
    }

    fun generate(inputReader: Reader)
            = generate(inputReader, System.getProperty("user.dir"))

    fun generate(inputReader: Reader, basePath: String) {
        val reader = mapper.readerFor(InputData::class.java)
        val inputData = reader.readValue<InputData>(inputReader)
        val generator = this.generatorFactory.create(basePath, inputData)
        generator.generate()
    }

}