package com.virtlink.gator

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import org.junit.Test

class StringTemplatesTemplateTests : StringTemplatesTests() {
    @Test
    fun testHelloWorld() {
        // Arrange
        val inputFilePath = this.javaClass.getResource("input1.yaml").toURI()
        val processor = createProcessor(YAMLMapper())

        // Act
        processor.generate(inputFilePath)

        // Assert
    }

    @Test
    fun testTerms() {
        // Arrange
        val inputFilePath = this.javaClass.getResource("terms.yaml").toURI()
        val generator = createProcessor(YAMLMapper())

        // Act
        generator.generate(inputFilePath)

        // Assert
    }

}