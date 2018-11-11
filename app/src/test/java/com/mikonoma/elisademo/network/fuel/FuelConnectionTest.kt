package com.mikonoma.elisademo.network.fuel

import com.mikonoma.elisademo.network.ENWRequest
import com.mikonoma.elisademo.network.HttpTestBase
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FuelConnectionTest : HttpTestBase() {

    @Test
    fun testExecute() {
        assert(mockServer != null, { "Server not initialized" })

        mockServer?.`when`(HttpRequest.request().withMethod("GET"))
            ?.respond(HttpResponse.response()
                .withStatusCode(200)
                .withBody("diiba daaba")
                .withHeader("foobar", "foo", "bar"))

        val request = ENWRequest(url + "/foo")
        val response = FuelConnection.execute(request)

        assertThat(response.code, equalTo(200))

        mockServer?.verify(HttpRequest.request()
            .withMethod("GET")
            .withPath("/foo"))

    }
}