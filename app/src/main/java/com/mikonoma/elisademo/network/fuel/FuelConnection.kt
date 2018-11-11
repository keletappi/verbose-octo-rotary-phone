package com.mikonoma.elisademo.network.fuel

import com.github.kittinunf.fuel.httpGet
import com.mikonoma.elisademo.network.ENWConnection
import com.mikonoma.elisademo.network.ENWError
import com.mikonoma.elisademo.network.ENWRequest
import com.mikonoma.elisademo.network.ENWResponse
import javax.inject.Inject

class FuelConnection @Inject constructor () : ENWConnection {
    override suspend fun execute(request: ENWRequest): ENWResponse {
        val (fuelRequest, fuelResponse, fuelResult) = try {
            request.URL.httpGet().response()
        } catch (e: Exception) {
            return ENWResponse(code = -1,
                body = "",
                error = ENWError(e)
            )
        }
        val (resultBytes, resultError) = fuelResult
        return ENWResponse(
            fuelResponse.statusCode,
            resultBytes,
            fuelResponse.headers,
            when (fuelResult.component2()) {
                null -> null
                else -> ENWError(resultError?.exception)
            }
        )
    }

}
