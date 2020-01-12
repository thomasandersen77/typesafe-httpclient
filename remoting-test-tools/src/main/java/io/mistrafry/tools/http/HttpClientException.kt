package io.mistrafry.tools.http

import java.lang.RuntimeException

class HttpClientException(body: String) : RuntimeException(body) {

}
