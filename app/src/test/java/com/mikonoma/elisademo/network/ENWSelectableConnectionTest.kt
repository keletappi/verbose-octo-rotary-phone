package com.mikonoma.elisademo.network

import android.content.SharedPreferences
import com.mikonoma.elisademo.PREF_KEY_CONNECTION_IMPLEMENTATION
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

// SuspendableENWConnection(Impl) is a workaround to allow mocking ENWConnection which
// defines suspendable function. Until Mockito has better understanding of coroutines,
// compiler fails with error when mocking a suspendable call.
//
// See https://discuss.kotlinlang.org/t/verifying-suspending-functions-with-mockito-or-alternatives/2492/2
interface SuspendableENWConnection {
    fun execute(request: ENWRequest): ENWResponse
}

class SuspendableENWConnectionDelegate(val mock: SuspendableENWConnection) : ENWConnection {
    suspend override fun execute(request: ENWRequest): ENWResponse = mock.execute(request)
}

class ENWSelectableConnectionTest {

    @Test
    fun testExecuteSelectsCorrectImplementation() {
        val request = ENWRequest("test_url://rairai")

        val fooResponse = ENWResponse(200, "foo")
        val barResponse = ENWResponse(200, "bar")
        val defaultResponse = ENWResponse(200, "def")

        val fooConnection = mock<SuspendableENWConnection> { on { execute(any()) } doReturn fooResponse}
        val barConnection = mock<SuspendableENWConnection> { on { execute(any()) } doReturn barResponse}
        val defaultConnection = mock<SuspendableENWConnection> { on { execute(any()) } doReturn defaultResponse}

        val implementations = hashMapOf(
            "foo" to SuspendableENWConnectionDelegate(fooConnection),
            "bar" to SuspendableENWConnectionDelegate(barConnection)
        )

        var connectionPreference: String? = null

        val mockPrefs = mock<SharedPreferences>() {
            on { getString(PREF_KEY_CONNECTION_IMPLEMENTATION, null) } doAnswer { connectionPreference }
        }

        runBlocking {
            val testedConnection = ENWSelectableConnection(
                mockPrefs,
                implementations,
                SuspendableENWConnectionDelegate(defaultConnection)
            )

            connectionPreference = "foo"
            assertThat(testedConnection.execute(request), equalTo(fooResponse))
            verify(fooConnection).execute(request)
            verifyNoMoreInteractions(fooConnection, barConnection, defaultConnection)

            connectionPreference = "bar"
            assertThat(testedConnection.execute(request), equalTo(barResponse))
            verify(barConnection).execute(request)
            verifyNoMoreInteractions(fooConnection, barConnection, defaultConnection)

            // when unknown connection type selected, use default
            connectionPreference = "diibadaaba"
            assertThat(testedConnection.execute(request), equalTo(defaultResponse))
            verify(defaultConnection).execute(request)
            verifyNoMoreInteractions(fooConnection, barConnection, defaultConnection)

        }
    }
}