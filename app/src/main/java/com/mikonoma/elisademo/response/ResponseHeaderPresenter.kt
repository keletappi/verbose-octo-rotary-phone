package com.mikonoma.elisademo.response

import com.mikonoma.elisademo.MainActivity
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

    fun showHttpResponseHeaders(headers: Map<String, List<String>>) {
        val buffer = StringBuffer()
        for (entry in headers) {
            buffer.append(entry.key + ": " + entry.value).append("\n")
        }
        activity.response_headers_text.text = buffer.toString()
    }
}