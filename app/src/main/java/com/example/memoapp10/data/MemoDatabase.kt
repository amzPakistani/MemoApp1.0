package com.example.memoapp10.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Memo::class],
    version = 1
)
abstract class MemoDatabase : RoomDatabase(){
    abstract val dao:MemoDao
}