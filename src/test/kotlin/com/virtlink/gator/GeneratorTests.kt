package com.virtlink.gator

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader

class GeneratorTests {
    @Test
    fun testHelloWorld() {
        // Arrange
//        val inputStream = this.javaClass.getResourceAsStream("input1.yaml")
        val inputFilePath = this.javaClass.getResource("input1.yaml").toURI()
//        val reader = BufferedReader(InputStreamReader(inputStream))
//        val dataInput = StringReader("---\n" +
//                "template: template1.st\n" +
//                "instances:\n" +
//                "- name: Daniel\n" +
//                "- name: World")
//        StreamR
        val generator = Generator(YAMLMapper())

        // Act
        generator.generate(inputFilePath)

        // Assert
    }
}