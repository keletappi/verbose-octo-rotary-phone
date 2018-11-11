package com.mikonoma.elisademo.network.mock

import com.mikonoma.elisademo.network.ENWConnection
import com.mikonoma.elisademo.network.ENWRequest
import com.mikonoma.elisademo.network.ENWResponse
import com.mikonoma.elisademo.persistence.mock.MockAccessor
import com.mikonoma.elisademo.persistence.mock.MockResponseDao
import java.io.ByteArrayInputStream
import java.io.InputStream

class MockConnection(val responseDao: MockResponseDao) : ENWConnection {

    val notFound = ENWResponse(404, "Mocked response not found.")

    override fun execute(request: ENWRequest): ENWResponse {

        responseDao.get(request.URL)


        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
