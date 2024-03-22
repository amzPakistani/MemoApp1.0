package com.example.memoapp10.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {

    @Upsert
    suspend fun upsertMemo(memo: Memo)

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Update
    suspend fun updateMemo(memo: Memo)

    @Query("SELECT * FROM Memo ORDER BY favorite DESC")
    fun getMemoByFavorites():Flow<List<Memo>>

    @Query("SELECT * FROM Memo ORDER BY title")
    fun getMemoByTitle():Flow<List<Memo>>
}