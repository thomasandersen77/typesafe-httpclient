package io.mistrafry.tools.http

import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class Webservice(val url: String) {

}

class Client {
    val builder : HttpClient.Builder = HttpClient.newBuilder();
    val client : HttpClient

    init {
        client = builder.build()
    }
}

class Request(val client: Client, path: String) {
    val req : HttpRequest.Builder = HttpRequest.newBuilder(URI.create(path));

    init {
        // configure in DSL
        //req.header()
    }

    fun <T> post(body: String, clz : Class<T>) : T {
        req.POST(HttpRequest.BodyPublishers.ofString(body))
        val httpResponse = client.client.send(req.build(), HttpResponse.BodyHandlers.ofString())
        when(httpResponse.statusCode()) {
            in 400..499 -> throw HttpClientException(httpResponse.body())
            in 500..599 -> throw HttpServerException(httpResponse.body())
            else -> {
                return ObjectMapper().readValue<T>(httpResponse.body(), clz)
            }
        }
    }

    fun get(consumer: Consumer) {
        req.GET()
        req.build()
        val httpResponse = client.client.send(req.build(), HttpResponse.BodyHandlers.ofString())

        //consumer.consume(httpResponse)
    }
}

interface Consumer {
    fun <T> consume(httpResponse: HttpResponse<String>) : T
}

class HttpClientDSL(val host: String) {
    private val webservice : Webservice = Webservice(host)
    private val client : Client = Client()

    fun client(doClient : Client.() -> Unit) = Client()

    fun <T> post(path: String, body: String, clz : Class<T>)
            = Request(client, path).post(body, clz)
}

fun webservice(host: String, init: HttpClientDSL.() -> Unit) = HttpClientDSL(host).init()

fun main() {
    webservice("http://localhost:8080") {
        client {
            post("/person", "{name: 'thomas'}", String::class.java)

        }
    }
}

/*
class KafkaDSL(bootstrapServers: String) {
  private val kafka = Kafka(bootstrapServers)

  fun producer(topic: String, doProduce: Producer.() -> Unit) =
    Producer(kafka, topic).doProduce()

  fun consumer(topic: String, doConsume: Consumer.() -> Unit) =
    Consumer(kafka, topic).doConsume()
}

fun kafka(bootstrapServers: String, init: KafkaDSL.() -> Unit) =
  KafkaDSL(bootstrapServers).init()
 */