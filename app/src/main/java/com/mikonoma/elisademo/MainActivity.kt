package com.mikonoma.elisademo

import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.mikonoma.elisademo.network.ENWConnection
import com.mikonoma.elisademo.network.ENWRequest
import com.mikonoma.elisademo.network.ENWResponse
import kotlinx.android.synthetic.main.url_input_header.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val component by lazy { app.component.plus(MainActivityModule(this)) }

    lateinit var connection: ENWConnection
        @Inject set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        goButton.setOnClickListener { view -> run {
//            disableInput()
            executeRequest()
//            enableInput()
        }}
    }

    @UiThread
    private fun executeRequest() {

        val url: String = urlInput.text.toString()
        GlobalScope.launch {
            val response = fetch(url)
            show(response)
        }
    }

    @UiThread
    private fun show(response: ENWResponse) {
        Log.d("MainActivity", "TODO: Not implemented - show(" + response + ")")
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
