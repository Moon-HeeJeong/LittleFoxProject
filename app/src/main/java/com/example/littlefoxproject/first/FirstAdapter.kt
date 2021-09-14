package com.example.littlefoxproject.first

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.littlefoxproject.R
import com.example.littlefoxproject.databinding.ActivityFirstBinding.inflate

import com.example.littlefoxproject.databinding.FirstVideoItemBinding
import com.example.littlefoxproject.first.models.Video

class FirstAdapter(var videos : MutableList<Video>) : RecyclerView.Adapter<FirstAdapter.VideoViewHolder>() {

    private lateinit var mListener : onItemClickListener
    private var limit = 15

    inner class VideoViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private var img: ImageView = itemView.findViewById(R.id.first_video_img)
        var userName: TextView? = itemView.findViewById(R.id.first_video_txt_uesr_name)

        fun bind(videos: Video) {
            Glide.with(itemView)
                .load("${videos.video_pictures[0].picture}")
                .transform(CenterCrop())
                .into(img)

            userName?.text = videos.user.name
        }

        init {
            /** 리스트의 이미지 클릭 */
            img.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    interface  onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.first_video_item, parent, false)
        return VideoViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    /** limit=15개만 출력 */
    override fun getItemCount(): Int {
        if(videos.size > limit){
            return limit
        }
        else
        {
            return videos.size
        }
    }

    fun appendVideos(videos : List<Video>) {
        this.videos.addAll(videos)
        notifyDataSetChanged()
    }
}