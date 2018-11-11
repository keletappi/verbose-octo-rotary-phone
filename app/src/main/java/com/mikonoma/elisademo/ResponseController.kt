package com.mikonoma.elisademo

import android.support.annotation.UiThread
import android.view.View
import com.mikonoma.elisademo.network.ENWResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_response.*
import kotlinx.android.synthetic.main.http_response.*
import javax.inject.Inject

class ResponseController @Inject constructor () {

    lateinit var activity: MainActivity
        @Inject set

    @UiThread
    fun showResponse(response: ENWResponse) {
        if (response.hasError) {
            showError(response)
        } else {
            showHttpResponse(response)
        }
    }

    private fun showHttpResponse(response: ENWResponse) {
        activity.error_response_container.visibility = View.GONE
        activity.http_response_container.visibility = View.VISIBLE
        activity.response_body.text = response.body!!.reader().readText()
    }

    private fun showError(response: ENWResponse) {
        activity.http_response_container.visibility = View.GONE
        activity.error_response_container.visibility = View.VISIBLE
        activity.error_message.text = response.error!!.exception.toString()
    }
}