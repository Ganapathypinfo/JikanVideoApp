package com.example.jikan.data.di

import com.example.jikan.data.network.JikanApiHelper
import com.example.jikan.data.network.JikanApiHelperImp
import com.example.jikan.data.network.JikanApiService
import com.example.jikan.utils.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn (SingletonComponent::class)
object JikanAppModule {

    @Provides
    fun providesBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun providesOkHttpClient() = run{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient,
                         baseUrl:String): Retrofit = Retrofit.Builder()
                             .baseUrl(baseUrl)
                             .addConverterFactory(GsonConverterFactory.create())
                             .client(okHttpClient)
                             .build()

    @Provides
    @Singleton
    fun providesJikanApiService(retrofit: Retrofit) = retrofit.create(JikanApiService::class.java)

    @Provides
    @Singleton
    fun providesJikanApiHelper(jikanApiHelperImp: JikanApiHelperImp):JikanApiHelper = jikanApiHelperImp
}