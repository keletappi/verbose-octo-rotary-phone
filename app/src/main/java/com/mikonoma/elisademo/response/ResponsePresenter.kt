package com.mikonoma.elisademo.response

import android.view.View
import androidx.annotation.UiThread
import com.mikonoma.elisademo.MainActivity
import com.mikonoma.elisademo.network.ENWResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_response.*
import javax.inject.Inject


class ResponsePresenter @Inject constructor () {

    lateinit var activity: MainActivity
        @Inject set

    lateinit var headerPresenter: ResponseHeaderPresenter
        @Inject set

    lateinit var bodyPresenter: ResponseBodyPresenter
        @Inject set

    @UiThread
    fun present(response: ENWResponse) {
        if (response.hasError) {
            showError(response)
        } else {
            showResponse(response)
        }
    }

    private fun showResponse(response: ENWResponse) {
        activity.error_response_container.visibility = View.GONE
        activity.http_response_container.visibility = View.VISIBLE

        headerPresenter.showHttpResponseHeaders(response)
        bodyPresenter.showHttpResponseBody(response)
    }


    private fun showError(response: ENWResponse) {
        activity.http_response_container.visibility = View.GONE
        activity.error_response_container.visibility = View.VISIBLE
        activity.error_message.text = response.error!!.exception.toString()
    }
}