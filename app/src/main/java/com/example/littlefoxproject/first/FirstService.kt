package com.example.littlefoxproject.first

import com.example.littlefoxproject.first.models.Video
import com.example.littlefoxproject.first.models.VideosResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstService(val view: FirstActivity) {

    fun tryGetVideo(auth:String, onSuccess: (videos : List<Video>) -> Unit, onError: () -> Unit){
        val firstRetrofitInterface = RetrofitClientVideos.sRetrofit.create(FirstRetrofitInterface::class.java)
        firstRetrofitInterface.getVideos(auth).enqueue(object : Callback<VideosResponse> {

            override fun onResponse(call: Call<VideosResponse>, response: Response<VideosResponse>) {
                view.onGetSuccess(response.body() as VideosResponse)
            }

            override fun onFailure(call: Call<VideosResponse>, t: Throwable) {
                view.onGetFailure(t.message ?: "통신 오류")
            }
        })
    }
}