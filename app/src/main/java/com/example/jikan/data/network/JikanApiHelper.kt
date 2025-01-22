package com.example.jikan.data.network

import com.example.jikan.model.AnimeDetailsList
import com.example.jikan.model.AnimeVideoDetails

interface JikanApiHelper {
    suspend fun getAnimeDetails(): AnimeDetailsList
    suspend fun getAnimeVideoDetails(anime_id:String): AnimeVideoDetails
}