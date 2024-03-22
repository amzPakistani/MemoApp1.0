package com.example.memoapp10.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memoapp10.data.Memo
import com.example.memoapp10.data.MemoEvent
import com.example.memoapp10.data.MemoState
import com.example.memoapp10.data.SortType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoScreen(
    state: MemoState,
    onEvent: (MemoEvent) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(MemoEvent.ShowDialog)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    ) { padding ->
        if (state.isAddingMemo) {
            MemoDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = padding,
            modifier = modifier.fillMaxSize()
        ) {
            item {
                SortType.values().forEach { sortType ->
                    TopRowItem(sortType = sortType, onEvent = onEvent, state = state)
                }
            }
            items(state.memo) { memo ->
                ListItems(onEvent = onEvent, memo = memo, onClick = {
                    navController.navigate("detail_screen/${memo.id}")
                })
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopRowItem(
    sortType: SortType,
    onEvent: (MemoEvent) -> Unit,
    state: MemoState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                onEvent(MemoEvent.SortType(sortType))
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = state.sortType == sortType,
            onClick = { onEvent(MemoEvent.SortType(sortType)) })
        Text(text = sortType.name)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItems(
    onEvent: (MemoEvent) -> Unit,
    memo: Memo,
    modifier: Modifier = Modifier,
    onClick:() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
        ,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 8.dp, bottom = 8.dp, start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = memo.title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.headlineLarge
            )

            IconButton(
                onClick = {
                    if (memo.isFavorite) {
                        onEvent(MemoEvent.UnFavoriteMemo(memo))
                    } else {
                        onEvent(MemoEvent.FavoriteMemo(memo))
                    }
                }
            ) {
                if (memo.isFavorite) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite")
                } else {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }

            IconButton(
                onClick = { onEvent(MemoEvent.DeleteMemo(memo)) }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

