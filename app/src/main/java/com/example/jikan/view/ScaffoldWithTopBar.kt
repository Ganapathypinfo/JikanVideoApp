package com.example.jikan.view


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBar(navController: NavHostController, content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(text = "Jikan VideoInfo App" , Modifier.fillMaxWidth())
                    } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF423232), // Change to your desired color
                    titleContentColor = Color.White, // Change title color if needed
                    actionIconContentColor = Color.White // Change action icons color if needed
                ),
                actions = {
                    // Add any action buttons here if needed
                }
            )
        }
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 64.dp, 0.dp, 0.dp),
            color =  Color(0xFF5E5858)//MaterialTheme.colorScheme.primary
        ) {                  // Provide padding to the content based on the Scaffold
                content()
      }
    }

}


