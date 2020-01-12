package io.mistrafry.tools.http

import java.lang.RuntimeException

class HttpServerException(body: String) : RuntimeException(body) {

}
