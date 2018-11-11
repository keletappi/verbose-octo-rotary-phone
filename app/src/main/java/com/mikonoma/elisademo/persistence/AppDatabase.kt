package com.mikonoma.elisademo.persistence

import androidx.room.RoomDatabase
import androidx.room.Database
import com.mikonoma.elisademo.persistence.mock.MockResponseDao
import com.mikonoma.elisademo.persistence.mock.MockResponseData


@Database(entities = arrayOf(MockResponseData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun responseDao(): MockResponseDao
}