package com.mikonoma.elisademo

import androidx.room.Room
import android.content.Context
import com.mikonoma.elisademo.network.ENWConnection
import com.mikonoma.elisademo.network.ENWSelectableConnection
import com.mikonoma.elisademo.persistence.AppDatabase
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: ENWApplication) {

    @Provides @Singleton
    fun provideApp() = app

    @Provides @Singleton
    fun provideDB() = Room.databaseBuilder(
        app.applicationContext,
        AppDatabase::class.java, "database"
    ).build()

    @Provides @Singleton
    fun provideMockResponseDao() = app.db.responseDao()

    @Provides @Singleton
    fun provideConnection(): ENWConnection = ENWSelectableConnection(
        app.getSharedPreferences("prefs", Context.MODE_PRIVATE),
        app.connectionImplementations,
        app.fuelConnection
    )
}

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: ENWApplication)

    fun plus(mainActivityModule: MainActivityModule): MainActivityComponent
}