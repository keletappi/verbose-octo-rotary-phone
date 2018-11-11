package com.mikonoma.elisademo

import android.app.Application
import com.mikonoma.elisademo.network.ENWConnection
import com.mikonoma.elisademo.network.fuel.FuelConnection
import com.mikonoma.elisademo.network.mock.MockConnection
import com.mikonoma.elisademo.persistence.AppDatabase
import javax.inject.Inject

class ENWApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    lateinit var db: AppDatabase
        @Inject set

    lateinit var mockConnection: MockConnection
        @Inject set

    lateinit var fuelConnection: FuelConnection
        @Inject set

    lateinit var connectionImplementations: Map<String, ENWConnection>

    @Inject fun initializeConnectionMap() {
        connectionImplementations = hashMapOf(
            PREF_VALUE_FUEL_CONNECTION to fuelConnection,
            PREF_VALUE_MOCK_CONNECTION to mockConnection
        )
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)

    }

}