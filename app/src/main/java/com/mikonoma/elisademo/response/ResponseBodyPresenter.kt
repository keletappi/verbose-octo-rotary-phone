package com.mikonoma.elisademo.response

import android.graphics.BitmapFactory
import android.view.View
import com.mikonoma.elisademo.MainActivity
import com.mikonoma.elisademo.asUTF8String
import com.mikonoma.elisademo.network.ENWResponse
import kotlinx.android.synthetic.main.http_response.*
import javax.inject.Inject

class ResponseBodyPresenter @Inject constructor() {

    lateinit var activity: MainActivity
        @Inject set

    fun present(response: ENWResponse) {
        if (isImage(response)) {
            activity.response_image.visibility = View.VISIBLE
            activity.response_body_text.visibility = View.GONE
            showResponseAsImage(response.body)
        } else {
            activity.response_image.visibility = View.GONE
            activity.response_body_text.visibility = View.VISIBLE
            showResponseAsText(response.body)
        }
    }

    private fun showResponseAsText(body: ByteArray) {
        activity.response_body_text.text = body.asUTF8String()
    }

    private fun isImage(response: ENWResponse): Boolean {
        val contentType = response.headers.get("Content-Type")?: emptyList()
        return contentType[0].startsWith("image/")
    }

    private fun showResponseAsImage(body: ByteArray) {
        val bm = BitmapFactory.decodeByteArray(body, 0, body.size)
        activity.response_image.setImageBitmap(bm)
    }
}
