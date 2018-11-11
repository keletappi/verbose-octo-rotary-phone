package com.mikonoma.elisademo.network

import org.junit.After
import org.junit.Before
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import java.util.*

internal val random = Random()
internal fun randomPort(from: Int = 1025, to: Int = 65535) : Int {
    return random.nextInt(to - from) + from
}

abstract class HttpTestBase {
    private val port = randomPort()
    var mockServer: MockServerClient? = null
    val url = "http://localhost:$port"

    @Before
    fun prepare() {
        mockServer = ClientAndServer.startClientAndServer(port)
    }

    @After
    fun tearDown() {
        mockServer?.close()
    }
}