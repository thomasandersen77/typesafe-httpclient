package no.spk.app.spring.kotlin

import com.fasterxml.jackson.databind.ObjectMapper
import no.spk.felles.ws.client.HeaderBuilder
import no.spk.felles.ws.client.HttpClient
import no.spk.felles.ws.client.HttpClientImpl
import no.spk.felles.ws.client.JsonMappingProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI

@SpringBootApplication
class SpringApplication

fun main(args: Array<String>) {
	runApplication<SpringApplication>(*args)
}

@Configuration
class ClientConfiguration(){
	@Bean
	fun httpClient(@Value("url") url : String) : HttpClient {
		return HttpClientImpl(URI.create(url), JacksonJsonMappingProvider())
	}

	inner class JacksonJsonMappingProvider : JsonMappingProvider{
		override fun <T : Any?> toJson(type: T): String {
			return Mapper.mapper.writeValueAsString(type)
		}

		override fun <T : Any?> fromJson(json: String?, type: Class<T>?): T {
			return Mapper.mapper.readValue(json, type)
		}
	}

	companion object Mapper {
		val mapper : ObjectMapper
			get() {
				val mapper = ObjectMapper();
				return mapper
			}
	}
}