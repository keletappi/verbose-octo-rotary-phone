package com.mikonoma.elisademo.response

import com.mikonoma.elisademo.MainActivity
import com.mikonoma.elisademo.network.ENWResponse
import kotlinx.android.synthetic.main.http_response.*
import javax.inject.Inject

class ResponseHeaderPresenter @Inject constructor() {

    lateinit var activity: MainActivity
        @Inject set

    @Inject
    fun initViews() {
        activity.header_title.setOnClickListener { activity.response_headers_text.toggle() }
        activity.response_headers_text.setOnClickListener { activity.response_headers_text.toggle() }
    }

    fun showHttpResponseHeaders(response: ENWResponse) {

        val buffer = StringBuffer()
        buffer.append("${response.code} / ${response.message}\n\n")
        for (entry in response.headers) {
            buffer.append("${entry.key}: ${entry.value}\n")
        }
        activity.response_headers_text.text = buffer.toString()
    }

    fun showHttpResponseHeaders() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}