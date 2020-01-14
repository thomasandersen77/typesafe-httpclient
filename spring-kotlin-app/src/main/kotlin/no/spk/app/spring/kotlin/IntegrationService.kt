package no.spk.app.spring.kotlin

import no.spk.felles.ws.client.HttpClient
import org.springframework.stereotype.Component

@Component
class IntegrationService (val client: HttpClient){

    fun callRemoteService(): String {
        val response = client.get("{}", Response::class.java)
        return response.message
    }

    inner class Response(val message : String) {}
}