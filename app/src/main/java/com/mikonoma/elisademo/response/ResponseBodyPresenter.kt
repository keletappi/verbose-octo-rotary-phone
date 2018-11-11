package com.mikonoma.elisademo.response

import android.graphics.BitmapFactory
import android.view.View
import com.mikonoma.elisademo.MainActivity
import com.mikonoma.elisademo.network.ENWResponse
import kotlinx.android.synthetic.main.http_response.*
import java.io.InputStream
import javax.inject.Inject

class ResponseBodyPresenter @Inject constructor() {

    lateinit var activity: MainActivity
        @Inject set

    fun showHttpResponseBody(response: ENWResponse) {

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
}
