package com.example.littlefoxproject.first

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientVideos {
    val sRetrofit = initRetrofit()
    private const val VIDEO_URL = "https://api.pexels.com/videos/"
    private fun initRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(VIDEO_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}