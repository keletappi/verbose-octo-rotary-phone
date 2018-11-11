package com.mikonoma.elisademo.network.mock

import android.util.Log
import com.mikonoma.elisademo.network.ENWConnection
import com.mikonoma.elisademo.network.ENWRequest
import com.mikonoma.elisademo.network.ENWResponse
import com.mikonoma.elisademo.persistence.mock.MockResponseDao
import javax.inject.Inject


class MockConnection @Inject constructor (val responseDao: MockResponseDao) : ENWConnection {
    val notFound = ENWResponse(404,
        "Not Found",
        "Mocked response not found.")

    override suspend fun execute(request: ENWRequest): ENWResponse {
        Log.w("MockConnection", "TODO: Implement mock connection support")
        return notFound
    }

}
