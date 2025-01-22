package com.example.jikan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikan.data.network.NetworkResponse
import com.example.jikan.model.AnimeDetailsList
import com.example.jikan.model.AnimeVideoDetails
import com.example.jikan.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository) :ViewModel() {
        private val _apiResponse = MutableStateFlow<NetworkResponse<AnimeDetailsList>>(NetworkResponse.Loading)
        val apiResponse:StateFlow<NetworkResponse<AnimeDetailsList>> = _apiResponse

    private val _apiSingleItemRes = MutableStateFlow<NetworkResponse<AnimeVideoDetails>>(NetworkResponse.Loading)
    val apiSingleItemRes:StateFlow<NetworkResponse<AnimeVideoDetails>> = _apiSingleItemRes

    fun getAnimeDetails(){
        viewModelScope.launch {
            _apiResponse.value = NetworkResponse.Loading
            try{
                var animeDetailsList = mainRepository.getAnimeDetails()
                _apiResponse.value = NetworkResponse.Success(animeDetailsList)
            }catch (e:Exception){
                _apiResponse.value = NetworkResponse.Error(e.message.toString())
            }
        }
    }

    fun getAnimeVideoDetails(itemId:String){
        viewModelScope.launch {
            _apiSingleItemRes.value = NetworkResponse.Loading
            try{
                var animeDetailsList = mainRepository.getAnimeVideoDetails(itemId)
                _apiSingleItemRes.value = NetworkResponse.Success(animeDetailsList)
            }catch (e:Exception){
                _apiSingleItemRes.value = NetworkResponse.Error(e.message.toString())
            }
        }
    }
}