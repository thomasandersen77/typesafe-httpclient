package no.spk.app.spring.kotlin

import no.spk.felles.ws.client.Headers
import no.spk.felles.ws.client.HttpClient
import org.springframework.stereotype.Component

@Component
class IntegrationService (val client: HttpClient){

    fun callRemoteService(vararg heaaders : String): String {
        val headerBuilder = Headers().put("key", "value")
        val response = client.get("{}", headerBuilder, Response::class.java)
        return response.type.message
    }

    inner class Response(val message : String) {}
}