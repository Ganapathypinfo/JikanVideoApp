package com.example.jikan.data.network

import com.example.jikan.model.AnimeDetailsList
import com.example.jikan.model.AnimeVideoDetails
import javax.inject.Inject

class JikanApiHelperImp @Inject constructor(private val jikanApiService: JikanApiService):JikanApiHelper {
    override suspend fun getAnimeDetails(): AnimeDetailsList = jikanApiService.getAnimeDetails()
    override suspend fun getAnimeVideoDetails(anime_id: String): AnimeVideoDetails = jikanApiService.getAnimeVideoDetails(anime_id)
}