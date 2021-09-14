package com.example.littlefoxproject.second

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.littlefoxproject.databinding.ActivitySecondBinding
import com.example.littlefoxproject.first.models.VideoPicture
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import android.net.Uri

class SecondActivity: AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    private lateinit var videoGridRecyclerview: RecyclerView
    private lateinit var videoGridAdapter: SecondAdapter
    private lateinit var videoGridLayoutMgr: LinearLayoutManager

    lateinit var videoPicture : ArrayList<VideoPicture>

    private var videoPlayer: SimpleExoPlayer? = null
    private lateinit var videoUrl :String
    private lateinit var video_player_view: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoGridRecyclerview = binding.secondVideoList
        videoGridLayoutMgr = GridLayoutManager(this, 3)
        videoGridRecyclerview.layoutManager = videoGridLayoutMgr
        videoGridAdapter = SecondAdapter(mutableListOf())
        videoGridRecyclerview.adapter = videoGridAdapter


        /** videoPicture : 번들로 받아 온 그리드뷰 속 데이터 */
        val bundle = intent.extras
        var name = bundle!!.getString("name")
        videoPicture = bundle.getParcelableArrayList("videoPictures")!!
        videoUrl = bundle.getString("url").toString()


        binding.secondTxtUserName.text = name

        /** ExoPlayer 인스턴스를 생성하고 비디오 초기화 */
        video_player_view =  binding.secondPlayer
        videoPlayer = SimpleExoPlayer.Builder(this).build()
        video_player_view?.player = videoPlayer
        buildMediaSource()?.let {
            videoPlayer?.prepare(it)
        }

        getGridVideo()


        /** 툴바 뒤로가기 버튼 클릭 */
        binding.toolbarBack.setOnClickListener {
            finish()
        }
    }

    /** MediaSource: 비디오에 출력할 미디어 정보를 가져오는 클래스 */
    private fun buildMediaSource(): MediaSource? {
        val dataSourceFactory = DefaultDataSourceFactory(this, "sample")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(videoUrl))
    }

    private fun getGridVideo() {
        onVideoFetched(videoPicture)
    }

    private fun onVideoFetched(videos : List<VideoPicture>) {
        videoGridAdapter.appendVideos(videos)
        attachVideoOnScrollListener()
    }

    private fun attachVideoOnScrollListener() {
        videoGridRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = videoGridLayoutMgr.itemCount
                val visibleItemCount = videoGridLayoutMgr.childCount
                val firstVisibleItem = videoGridLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    videoGridRecyclerview.removeOnScrollListener(this)
                    getGridVideo()
                }
            }
        })
    }

    /** 화면이 종료되면 비디오 해제 */
    override fun onDestroy() {
        super.onDestroy()
        videoPlayer?.release()
    }
}