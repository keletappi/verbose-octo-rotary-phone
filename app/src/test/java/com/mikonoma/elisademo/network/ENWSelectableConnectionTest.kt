package com.mikonoma.elisademo.network

import android.content.SharedPreferences
import com.mikonoma.elisademo.PREF_NETWORK_IMPLEMENTATION
import com.nhaarman.mockitokotlin2.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ENWSelectableConnectionTest {

    @Test
    fun testExecuteSelectsCorrectImplementation() {
        val request = ENWRequest("test_url://rairai")

        val fooResponse = ENWResponse(200, "foo")
        val barResponse = ENWResponse(200, "bar")
        val defaultResponse = ENWResponse(200, "def")

        val fooConnection = mock<ENWConnection> { on { execute(any()) } doReturn fooResponse}
        val barConnection = mock<ENWConnection> { on { execute(any()) } doReturn barResponse}
        val defaultConnection = mock<ENWConnection> { on { execute(any()) } doReturn defaultResponse}

        val implementations = hashMapOf(
            "foo" to fooConnection,
            "bar" to barConnection
        )

        var connectionPreference: String? = null

        val mockPrefs = mock<SharedPreferences>() {
            on { getString(PREF_NETWORK_IMPLEMENTATION, null) } doAnswer { connectionPreference }
        }

        val testedConnection = ENWSelectableConnection(
            mockPrefs,
            implementations,
            defaultConnection
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