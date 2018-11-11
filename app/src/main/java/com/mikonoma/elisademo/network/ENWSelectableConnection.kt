package com.mikonoma.elisademo.network

import android.content.SharedPreferences
import com.mikonoma.elisademo.PREF_KEY_CONNECTION_IMPLEMENTATION

/**
 * Selects the network connection implementation used based on user preferences.
 *
 * @param prefs shared preferences object
 * @param implementations Mapping from preference key to implementation objects
 * @param default connection to use if no implementation for user preference is found
 */
class ENWSelectableConnection(private val prefs: SharedPreferences,
                              private val implementations: Map<String, ENWConnection>,
                              private val default: ENWConnection) : ENWConnection {

    override suspend fun execute(request: ENWRequest): ENWResponse {
        val userPreference = prefs.getString(PREF_KEY_CONNECTION_IMPLEMENTATION, null)
        return selectImplementation(userPreference).execute(request)
    }

    private fun selectImplementation(key: String?): ENWConnection {
        return implementations[key] ?: default
    }


}

