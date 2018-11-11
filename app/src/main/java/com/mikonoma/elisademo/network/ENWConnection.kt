package com.mikonoma.elisademo.network

import java.io.InputStream
import java.util.Collections.emptyMap

interface ENWConnection {
    fun execute(request: ENWRequest) : ENWResponse
}

data class ENWRequest(val URL: String)

data class ENWResponse(val code: Int?,
                       val body: InputStream?,
                       val headers: Map<String, List<String>> = emptyMap(),
                       val error: ENWError? = null) {

    constructor(code: Int,
                body: ByteArray?,
                headers: Map<String, List<String>> = emptyMap(),
                error: ENWError? = null) :
            this(code,
                body?.inputStream(),
                headers,
                error
            )

    constructor(code: Int,
                body: String?,
                headers: Map<String, List<String>> = emptyMap(),
                error: ENWError? = null) :
            this(code,
                body?.byteInputStream(),
                headers,
                error)

}

data class ENWError(val exception: Exception?)
