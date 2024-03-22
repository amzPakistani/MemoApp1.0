package com.example.memoapp10.data

sealed interface MemoEvent{
    object SaveContact:MemoEvent
    object ShowDialog: MemoEvent
    object HideDialog: MemoEvent
    data class SetTitle(val title:String):MemoEvent
    data class SetDesc(val desc:String):MemoEvent
    data class DeleteMemo(val memo: Memo):MemoEvent
    data class FavoriteMemo(val memo: Memo):MemoEvent
    data class UnFavoriteMemo(val memo: Memo):MemoEvent
    data class SortType(val sortType: com.example.memoapp10.data.SortType):MemoEvent
}