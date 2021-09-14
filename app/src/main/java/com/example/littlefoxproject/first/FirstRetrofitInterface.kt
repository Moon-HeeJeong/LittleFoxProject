package com.example.littlefoxproject.first

import com.example.littlefoxproject.first.models.VideosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FirstRetrofitInterface {
    @GET("popular")
    fun getVideos(
        @Header("Authorization") auth: String
    ) : Call<VideosResponse>
}