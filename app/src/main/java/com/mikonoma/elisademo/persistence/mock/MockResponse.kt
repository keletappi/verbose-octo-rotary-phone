package com.mikonoma.elisademo.persistence.mock

import android.arch.persistence.room.*
import com.mikonoma.elisademo.network.ENWResponse

@Entity(tableName = "mock_responses")
data class MockResponseData(
    @PrimaryKey var url: String = "",
    @ColumnInfo var body: String = "") {

}

@Entity(tableName = "mock_headers",
    foreignKeys = arrayOf(ForeignKey(
        entity = MockResponseData::class,
        parentColumns = arrayOf("url"),
        childColumns = arrayOf("url"),
        onDelete = ForeignKey.CASCADE
    ))
)
data class MockResponseHeader(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo var url: String = "",
    @ColumnInfo var key: String = "",
    @ColumnInfo var value: String = ""
)


@Dao
interface MockResponseDao {

    @Query("SELECT * FROM mock_responses WHERE url LIKE :url")
    fun get(url: String)

    @Query("SELECT * FROM mock_responses")
    fun getAll(): List<MockResponseData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MockResponseData)

    @Delete
    fun delete(item: MockResponseData)

}