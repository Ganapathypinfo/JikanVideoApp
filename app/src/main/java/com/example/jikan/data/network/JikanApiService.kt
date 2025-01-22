package com.example.jikan.data.network

import com.example.jikan.model.AnimeDetailsList
import com.example.jikan.model.AnimeVideoDetails
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApiService {
    @GET("top/anime")
    suspend fun getAnimeDetails():AnimeDetailsList
    @GET("anime/{anime_id}")
    suspend fun getAnimeVideoDetails(@Path("anime_id") anime_id:String): AnimeVideoDetails
}