package com.example.memoapp10.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoViewmodel(private val dao: MemoDao):ViewModel() {

    private val _state = MutableStateFlow(MemoState())
    private val _sortType = MutableStateFlow(SortType.TITLE)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _memo = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.TITLE -> dao.getMemoByTitle()
                SortType.FAVORITE -> dao.getMemoByFavorites()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state,_sortType,_memo){state,sortType,memo->
        state.copy(
            sortType = sortType,
            memo = memo
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MemoState())

    fun onEvent(event: MemoEvent){
        when(event){
            is MemoEvent.DeleteMemo -> {
                viewModelScope.launch {
                    dao.deleteMemo(event.memo)
                }
            }
            MemoEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingMemo = false
                    )
                }
            }
            MemoEvent.SaveContact -> {
                val title = state.value.title
                val desc = state.value.desc
                val favorite = state.value.isFavorite

                if (title.isBlank()||desc.isBlank()){
                    return
                }

                val memo = Memo(
                    title = title,
                    isFavorite = favorite,
                    desc = desc
                )

                viewModelScope.launch {
                    dao.upsertMemo(memo)
                }

                _state.update { it.copy(
                    isAddingMemo = false,
                    isFavorite = false,
                    title = "",
                    desc = ""
                ) }
            }
            is MemoEvent.SetDesc -> {
                _state.update {
                    it.copy(
                        desc = event.desc
                    )
                }
            }
            is MemoEvent.SetTitle -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }
            MemoEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingMemo = true
                    )
                }
            }
            is MemoEvent.SortType -> {
                _sortType.value = event.sortType
            }

            is MemoEvent.FavoriteMemo -> {
                val updatedMemo = event.memo.copy(isFavorite = true)
                viewModelScope.launch {
                    dao.updateMemo(updatedMemo)
                }
            }

            is MemoEvent.UnFavoriteMemo -> {
                val updatedMemo = event.memo.copy(isFavorite = false)
                viewModelScope.launch {
                    dao.updateMemo(updatedMemo)
                }
            }

/*            is MemoEvent.FavoriteMemo -> {
                _state.update {
                    it.copy(
                        isFavorite = true
                    )
                }
            }

            is MemoEvent.UnFavoriteMemo -> {
                _state.update {
                    it.copy(
                        isFavorite = false
                    )
                }
            }*/

        }
    }

}