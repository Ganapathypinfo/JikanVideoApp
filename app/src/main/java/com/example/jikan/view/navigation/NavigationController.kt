package com.example.jikan.view.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jikan.view.AnimeListScreen
import com.example.jikan.view.VideoInfoScreen

@Composable
fun NavigationController(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "animeListScreen") {
        composable("animeListScreen"){
            AnimeListScreen(navController)
        }
        composable("videoInfoScreen/{data}"){
            backStackEntry -> val  data= backStackEntry.arguments?.getString("data")
            Log.i("", "data : ${data?:"No data"}")
            VideoInfoScreen(navController, data = data?:"No data")
        }
    }
}

