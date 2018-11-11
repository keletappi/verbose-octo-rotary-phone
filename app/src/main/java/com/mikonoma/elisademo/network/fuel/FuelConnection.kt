package com.mikonoma.elisademo.network.fuel

import com.github.kittinunf.fuel.httpGet
import com.mikonoma.elisademo.network.ENWConnection
import com.mikonoma.elisademo.network.ENWError
import com.mikonoma.elisademo.network.ENWRequest
import com.mikonoma.elisademo.network.ENWResponse

object FuelConnection : ENWConnection {
    override fun execute(request: ENWRequest): ENWResponse {
        val (fuelRequest, fuelResponse, fuelResult) = request.URL.httpGet().responseString()
        return ENWResponse(
            fuelResponse.statusCode,
            fuelResponse.dataStream,
            fuelResponse.headers,
            when (fuelResult.component2()) {
                null -> null
                else -> ENWError(fuelResult.component2()?.exception)
            }
        )
    }

}
