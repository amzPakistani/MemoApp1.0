package com.example.memoapp10.data

data class MemoState (
    val title:String = "",
    val desc:String = "",
    val memo: List<Memo> = emptyList(),
    val isAddingMemo:Boolean = false,
    val sortType: SortType = SortType.TITLE,
    val isFavorite:Boolean = false
)