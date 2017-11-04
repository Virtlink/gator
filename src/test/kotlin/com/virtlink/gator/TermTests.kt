package com.virtlink.gator

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import org.junit.Test

class TermTests {
    @Test
    fun test() {
        // Arrange
        val inputFilePath = this.javaClass.getResource("terms.yaml").toURI()
        val generator = Generator(YAMLMapper(), TemplateErrorListener())

        // Act
        generator.generate(inputFilePath)
    }
}