package com.example.memoapp10

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.memoapp10.data.MemoDao
import com.example.memoapp10.data.MemoDatabase
import com.example.memoapp10.data.MemoViewmodel
import com.example.memoapp10.ui.DetailScreen
import com.example.memoapp10.ui.MemoScreen

class MemoViewModelFactory(private val dao: MemoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemoViewmodel::class.java)) {
            return MemoViewmodel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

enum class Screen(val route:String){
    MemoScreen("memo_screen"),
    DetailScreen("detail_screen/{id}")
}

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val dao: MemoDao = remember {
        // Initialize your database here using Room
        Room.databaseBuilder(
            context,
            MemoDatabase::class.java,
            "memo.db"
        ).build().dao
    }

    val viewModel: MemoViewmodel = viewModel(factory = MemoViewModelFactory(dao))
    val state by viewModel.state.collectAsState()

    NavHost(navController = navController, startDestination = Screen.MemoScreen.route) {
        composable(route = Screen.MemoScreen.route) {
            MemoScreen(state = state, onEvent = viewModel::onEvent, navController)
        }

        composable(route = Screen.DetailScreen.route) { navBackStackEntry ->
            val idString = navBackStackEntry.arguments?.getString("id")
            val id = idString?.toIntOrNull()
            if (id != null) {
                DetailScreen(detailId = id , state = state  )
            }
        }
    }
}
