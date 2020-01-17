# Typesafe HttpClient Framework


THF is a very small framework built on top of Java 10´s HttpClient. The goal is to support some concepts found in 
Spring RestTemplate and JerseyClient - but without dependencies to third party libraries.

Currently the framework supports:
- Before/After request interceptors
- Typed HTTP Responses 
- Suppliers for runtime injection of HTTP headers
- "Bring your own serialization framework" - simply implement a provider interface using your preferred serialization framework
- Very simple configuration for use in either a plain Java/Kotlin application or a Spring application 



### Sample configuration for Kotlin Spring Boot application
The following is an example of a Spring Boot configuration in Kotlin. The example use Jackson for serializing to and from JSON. By delegating this responsibility to you, the user, the framework does not impose either a spesific framework or version of a framework for serialization/deserialization.
```kotlin

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
}```
