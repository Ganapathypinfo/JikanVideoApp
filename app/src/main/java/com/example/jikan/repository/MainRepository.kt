package com.example.jikan.repository

import com.example.jikan.data.network.JikanApiHelper
import com.example.jikan.data.network.JikanApiService
import com.example.jikan.model.AnimeDetailsList
import com.example.jikan.model.AnimeVideoDetails
import javax.inject.Inject

class MainRepository @Inject constructor(private val jikanApiService: JikanApiService):JikanApiHelper{
    override suspend fun getAnimeDetails(): AnimeDetailsList =jikanApiService.getAnimeDetails()
    override suspend fun getAnimeVideoDetails(anime_id:String): AnimeVideoDetails =jikanApiService.getAnimeVideoDetails(anime_id)
}