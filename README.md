# Typesafe HttpClient Framework


THF is a small framework built on top of Java 10´s HttpClient. The goal is to support some concepts found in 
Spring RestTemplate and JerseyClient - but without dependencies to third party libraries.

It supports concepts such as:
- Before/After request interceptors
- Typed Http Responses 
- Suppliers for runtime injection of HTTP headers
- "Bring your own serialization framework"
- Simple configuration for use in either a plain Java/Kotlin application or a Spring application 