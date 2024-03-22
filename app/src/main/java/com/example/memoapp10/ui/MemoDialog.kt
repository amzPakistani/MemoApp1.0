package com.example.memoapp10.ui




import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoapp10.data.MemoEvent
import com.example.memoapp10.data.MemoState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoDialog(state: MemoState, onEvent: (MemoEvent) -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        onDismissRequest = { onEvent(MemoEvent.HideDialog) },
        title = {
            Text(text = "Add Memo", style = MaterialTheme.typography.headlineLarge, fontSize = 24.sp)
        }, modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxSize(),
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(4.dp))
                TextField(
                    value = state.title,
                    onValueChange = { onEvent(MemoEvent.SetTitle(it)) },
                    placeholder = {
                        Text(text = "Title")
                    },modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = state.desc,
                    onValueChange = { onEvent(MemoEvent.SetDesc(it)) },
                    placeholder = {
                        Text(text = "Description")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(MemoEvent.SaveContact)
                    onEvent(MemoEvent.HideDialog) // Close the dialog after saving
                }
            ) {
                Text(text = "Save")
            }
        })
}