package com.example.jikan.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jikan.viewmodel.MainViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jikan.data.network.NetworkResponse


@Composable
fun AnimeListScreen(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()) {
    val apiResponse = mainViewModel.apiResponse.collectAsState().value
    var isDataFetched = false
    LaunchedEffect(Unit) {
        if (!isDataFetched){
            isDataFetched = true
         mainViewModel.getAnimeDetails()}
    }

    ScaffoldWithTopBar(navController){
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            when(apiResponse){
                is NetworkResponse.Loading -> {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.LightGray
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center){
                            CircularProgressIndicator()
                        }
                    }
                }
                is NetworkResponse.Success -> {
                    LazyColumn (modifier = Modifier.padding(0.dp,30.dp,0.dp,0.dp)){
                        val listAnima = apiResponse.data.data
                        items(listAnima.size){item ->
                            AnimeListCard(navController, listAnima.get(item).mal_id.toString(), listAnima.get(item).title, listAnima.get(item).episodes.toString(), listAnima.get(item).score.toString(), listAnima.get(item).images.jpg.image_url,  )
                        }
                    }
                }
                is NetworkResponse.Error -> {
                    Text(text = "Error: ${apiResponse.error}", color = MaterialTheme.colorScheme.error)
                }
            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun AnimeListCard(navController: NavHostController, itemId: String,title: String, noOfEpisodes: String, rating: String, posterImage: String) {

    Card(
        onClick = {
            navController.navigate("videoInfoScreen/${itemId}")
        },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEAE5E5)
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Row {
            GlideImage(model = posterImage,
                modifier = Modifier.padding(8.dp),
                contentDescription =posterImage)

            Column(Modifier.padding(8.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Episodes : $noOfEpisodes",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )
                Text(
                    text = "Rating : $rating",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )

            }

        }
    }
}

