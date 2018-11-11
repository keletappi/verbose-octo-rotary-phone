package com.mikonoma.elisademo.network

import org.junit.After
import org.junit.Before
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import java.util.*
import java.util.concurrent.atomic.AtomicInteger



internal val random = Random()
internal fun randomPort(from: Int = 1025, to: Int = 65535) : Int {
    return random.nextInt(to - from) + from
}

internal val portRange = 1024..65535
internal val portCounter  = AtomicInteger(1024)
internal fun port(): Int {
        synchronized(portCounter) {
            val nextPort = portCounter.incrementAndGet()
            if (!(nextPort in portRange)) {
                portCounter.set(1025)
                return 1025
            }
            return nextPort;
    }
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