package io.mistafry.tools.testtools

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestToolsApplication

fun main(args: Array<String>) {
	runApplication<TestToolsApplication>(*args)
}
