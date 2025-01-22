package com.example.jikan.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jikan.data.network.NetworkResponse
import com.example.jikan.viewmodel.MainViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoInfoScreen(navController: NavHostController, data:String, mainViewModel:MainViewModel= hiltViewModel()) {
     val apiResponse = mainViewModel.apiSingleItemRes.collectAsState().value

    LaunchedEffect (Unit){
        mainViewModel.getAnimeVideoDetails(data)
    }
    when(apiResponse){
        is NetworkResponse.Error ->  {
            Text(text = "Error: ${apiResponse.error}", color = MaterialTheme.colorScheme.error)
        }
        NetworkResponse.Loading -> {
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
            Column(modifier = Modifier.padding(18.dp, 30.dp, 18.dp, 20.dp)) {
                val resp = apiResponse.data.data
                if(resp.trailer.youtube_id.isNotEmpty()){
                    AndroidView(
                        factory = { context ->
                            var view = YouTubePlayerView(context)
                            view.addYouTubePlayerListener(
                                object : AbstractYouTubePlayerListener() {
                                    override fun onReady(youTubePlayer: YouTubePlayer) {
                                        super.onReady(youTubePlayer)
                                        youTubePlayer.loadVideo(resp.trailer.youtube_id, 0f)
                                    }
                                }
                            )
                            view
                        },
                        modifier = Modifier.fillMaxWidth().aspectRatio(16 / 9f)
                    )
                }else{
                    GlideImage(model = resp.images.jpg.image_url,
                        modifier = Modifier.fillMaxWidth().aspectRatio(16 / 9f).padding(8.dp),
                        contentDescription =resp.images.jpg.large_image_url)
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    /*Text(text="Title  :",
                        style = TextStyle(fontWeight  = FontWeight.Bold)
                    )*/
                    Text(text=resp.title, style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,)
                }
                Spacer(modifier = Modifier.padding(3.dp))
                Row (verticalAlignment = Alignment.CenterVertically){
                    Text(text="Genre(s) :",
                        style = TextStyle(fontWeight  = FontWeight.Bold)
                    )
                    Text(text=resp.genres.get(0).name)
                }
                Spacer(modifier = Modifier.padding(3.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text="Main Cast:",
                        style = TextStyle(fontWeight  = FontWeight.Bold)
                    )
                    Text(text=resp.type)
                }
                Spacer(modifier = Modifier.padding(3.dp))
                Row (verticalAlignment = Alignment.CenterVertically){
                    Text(text="Number of Episodes :",
                        style = TextStyle(fontWeight  = FontWeight.Bold)
                    )
                    Text(text="${resp.episodes}")
                }
                Spacer(modifier = Modifier.padding(3.dp))
                Row (verticalAlignment = Alignment.CenterVertically){
                    Text(text="Rating :",
                        style = TextStyle(fontWeight  = FontWeight.Bold)
                    )
                    Text(text="${resp.score}")
                }

                Spacer(modifier = Modifier.padding(3.dp))
                Row {
                    Text(text="Plot/Synopsis  :",
                            style = TextStyle(fontWeight  = FontWeight.Bold)
                    )

                }
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f, false)

                ) {
                    Text(resp.synopsis)
                }

            }
        }
    }



}

