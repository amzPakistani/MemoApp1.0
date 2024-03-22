package com.example.memoapp10.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(
    val title: String,
    val desc:String,
    @ColumnInfo(name = "favorite") val isFavorite: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
