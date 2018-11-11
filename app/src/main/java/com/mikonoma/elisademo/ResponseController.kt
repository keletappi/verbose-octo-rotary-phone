package com.mikonoma.elisademo

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import androidx.annotation.UiThread
import android.util.Log
import android.view.View
import com.mikonoma.elisademo.network.ENWResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_response.*
import kotlinx.android.synthetic.main.http_response.*
import java.io.InputStream
import javax.inject.Inject


class ResponseController @Inject constructor () {

    lateinit var activity: MainActivity
        @Inject set

    @UiThread
    fun showResponse(response: ENWResponse) {
        if (response.hasError) {
            showError(response)
        } else {
            showHttpResponseHeaders(response.headers)
            showHttpResponseBody(response)
        }
    }

    private fun showHttpResponseHeaders(headers: Map<String, List<String>>) {
        Log.d(TAG, "TODO: implement showHttpResponseHeaders")
    }

    private fun showHttpResponseBody(response: ENWResponse) {
        activity.error_response_container.visibility = View.GONE
        activity.http_response_container.visibility = View.VISIBLE

        if (isImage(response)) {
            activity.response_image.visibility = View.VISIBLE
            showResponseBodyAsImage(response.body!!)
        } else {
            activity.response_image.visibility = View.GONE
            showResponseBodyText(response.body!!)
        }
    }

    private fun showResponseBodyText(body: InputStream) {
        activity.response_body_text.text = body.reader().readText()
        body.reset()
    }

    private fun isImage(response: ENWResponse): Boolean {
        val contentType = response.headers.get("Content-Type")?: emptyList()
        return contentType[0].startsWith("image/")
    }

    private fun showResponseBodyAsImage(body: InputStream) {
        val bytes = body.readBytes()
        body.reset()
        val bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        activity.response_image.setImageBitmap(bm)
    }

    private fun showError(response: ENWResponse) {
        activity.http_response_container.visibility = View.GONE
        activity.error_response_container.visibility = View.VISIBLE
        activity.error_message.text = response.error!!.exception.toString()
    }
}