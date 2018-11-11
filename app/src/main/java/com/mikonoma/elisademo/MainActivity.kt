package com.mikonoma.elisademo

import android.os.Bundle
import android.util.Log
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mikonoma.elisademo.model.ResponseViewModel
import com.mikonoma.elisademo.network.ENWConnection
import com.mikonoma.elisademo.network.ENWRequest
import com.mikonoma.elisademo.network.ENWResponse
import com.mikonoma.elisademo.response.ResponsePresenter
import kotlinx.android.synthetic.main.url_input_header.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    val component by lazy { app.component.plus(MainActivityModule(this)) }

    lateinit var connection: ENWConnection
        @Inject set

    lateinit var responsePresenter: ResponsePresenter
        @Inject set

    lateinit var responseModel : ResponseViewModel
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        goButton.setOnClickListener { view -> run {
            executeRequest()
        }}

        responseModel.response.observe(this, Observer { response -> run {
            Log.d(TAG, "LiveData response set (" + response + ")")
            responsePresenter.present(response)
        }})
    }

    @UiThread
    private fun executeRequest() {
        disableInput()

        val requestUrl: String = urlInput.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            val response = fetch(requestUrl)
            Log.d(TAG, "Response received (" + response + ")")
            withContext(Dispatchers.Main) {
                Log.d(TAG, "Switched to main thread (" + response + ")")
                responseModel.response.value = response
                enableInput()
//                responsePresenter.present(response)
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
