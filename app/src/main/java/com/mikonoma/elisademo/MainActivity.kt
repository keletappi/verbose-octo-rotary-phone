package com.mikonoma.elisademo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.mikonoma.elisademo.network.ENWConnection
import com.mikonoma.elisademo.network.ENWRequest
import com.mikonoma.elisademo.network.ENWResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.url_input_header.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val component by lazy { app.component.plus(MainActivityModule(this)) }

    lateinit var connection: ENWConnection
        @Inject set

    lateinit var responsePresenter: ResponseController
        @Inject set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        goButton.setOnClickListener { view -> run {
            executeRequest()
        }}
    }

    @UiThread
    private fun executeRequest() {
        disableInput()

        val requestUrl: String = urlInput.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            val response = fetch(requestUrl)
            Log.d("MainActivity", "Response received (" + response + ")")
            withContext(Dispatchers.Main) {
                no_data.visibility = View.GONE
                enableInput()
                responsePresenter.showResponse(response)
            }
        }
    }

    @UiThread
    private suspend fun fetch(url: String): ENWResponse {
        return connection.execute(ENWRequest(url))
    }

    @UiThread
    private fun disableInput() {
        goButton.isEnabled = false
        urlContainer.isEnabled = false
    }

    @UiThread
    private fun enableInput() {
        urlContainer.isEnabled = true
        goButton.isEnabled = true
    }

}
