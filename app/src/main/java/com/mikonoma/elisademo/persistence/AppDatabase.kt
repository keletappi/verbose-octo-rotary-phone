package com.mikonoma.elisademo.persistence

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.mikonoma.elisademo.persistence.mock.MockResponseDao
import com.mikonoma.elisademo.persistence.mock.MockResponseData


@Database(entities = arrayOf(MockResponseData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun responseDao(): MockResponseDao
}