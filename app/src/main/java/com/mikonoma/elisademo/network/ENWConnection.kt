package com.mikonoma.elisademo.network

import java.util.Collections.emptyMap

interface ENWConnection {
    suspend fun execute(request: ENWRequest) : ENWResponse
}

data class ENWRequest(val URL: String)

data class ENWResponse(val code: Int?,
                       val body: ByteArray,
                       val headers: Map<String, List<String>> = emptyMap(),
                       val error: ENWError? = null) {

    val hasError: Boolean = error != null

    constructor(code: Int,
                body: String?,
                headers: Map<String, List<String>> = emptyMap(),
                error: ENWError? = null) :
            this(code,
                body?.toByteArray()?: ByteArray(0),
                headers,
                error)

}

data class ENWError(val exception: Exception)
