package no.spk.app.spring.kotlin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController @Autowired constructor(
        val integrationService: IntegrationService
) {

    @GetMapping
    fun update() : String {
        return integrationService.callRemoteService()
    }
}