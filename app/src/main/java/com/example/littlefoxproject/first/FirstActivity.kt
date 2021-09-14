package com.example.littlefoxproject.first

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.littlefoxproject.databinding.ActivityFirstBinding
import com.example.littlefoxproject.first.models.Video
import com.example.littlefoxproject.first.models.VideoFile
import com.example.littlefoxproject.first.models.VideoPicture
import com.example.littlefoxproject.first.models.VideosResponse
import com.example.littlefoxproject.second.SecondActivity
import java.util.*
import kotlin.collections.ArrayList


class FirstActivity: AppCompatActivity() {

    val auth:String = "enter auth key"
    lateinit var binding: ActivityFirstBinding

    private lateinit var videoRecyclerview: RecyclerView
    private lateinit var videoAdapter: FirstAdapter
    private lateinit var videoLayoutMgr: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoRecyclerview = binding.firstVideoList
        videoLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        videoRecyclerview.layoutManager = videoLayoutMgr
        videoAdapter = FirstAdapter(mutableListOf())
        videoRecyclerview.adapter = videoAdapter

        getVideo()

        videoAdapter.setOnItemClickListener(object: FirstAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

               val bundle = Bundle()
               var videoPictures : List<VideoPicture> = videoAdapter.videos[position].video_pictures

               /** 유저이름, 재생할 비디오 링크, 그리드뷰로 띄울 비디오 이미지들 번들로 보내기*/
               bundle.putString("name", videoAdapter.videos[position].user.name)
               bundle.putString("url", videoAdapter.videos[position].video_files[0].link)
               bundle.putParcelableArrayList("videoPictures", videoPictures as ArrayList<VideoPicture>)

               var intent = Intent(this@FirstActivity, SecondActivity::class.java)
               intent.putExtras(bundle)
               startActivity(intent)
            }
         })
        }

    private fun getVideo() {
        FirstService(this).tryGetVideo(
            auth,
            ::onVideoFetched,
            ::onError)
    }

    private fun onVideoFetched(videos : List<Video>) {
        videoAdapter.appendVideos(videos)
        attachVideoOnScrollListener()
    }

    private fun attachVideoOnScrollListener() {
        videoRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = videoLayoutMgr.itemCount
                val visibleItemCount = videoLayoutMgr.childCount
                val firstVisibleItem = videoLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    videoRecyclerview.removeOnScrollListener(this)
                    getVideo()
                }
            }
        })
    }

    private fun onError() {
        Toast.makeText(this@FirstActivity, "error", Toast.LENGTH_SHORT).show()
    }

    fun onGetSuccess(response: VideosResponse) {
        videoAdapter.appendVideos(response.videos)
        attachVideoOnScrollListener()
    }

    fun onGetFailure(message: String) {
        Toast.makeText(this@FirstActivity, message, Toast.LENGTH_SHORT).show()
    }

}
