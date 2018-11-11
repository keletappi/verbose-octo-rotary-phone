package com.mikonoma.elisademo.network

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.Collections.emptyMap

interface ENWConnection {
    fun execute(request: ENWRequest) : ENWResponse
}

data class ENWRequest(val URL: String)

data class ENWResponse(val code: Int,
                       val body: InputStream,
                       val headers: Map<String, String> = emptyMap()) {
    constructor(code: Int,
                body: String = "",
                headers: Map<String, String> = emptyMap()) :
            this(code,
                ByteArrayInputStream(body.toByteArray()),
                headers
        )
}
