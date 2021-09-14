package com.example.littlefoxproject.second

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.littlefoxproject.R
import com.example.littlefoxproject.databinding.SecondVideoItemBinding
import com.example.littlefoxproject.first.models.VideoPicture

class SecondAdapter(var videos : MutableList<VideoPicture>) : RecyclerView.Adapter<SecondAdapter.GridVideoViewHolder>() {

    private var limit = 15

    inner class GridVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var img: ImageView? = itemView.findViewById(R.id.second_item_img_grid)

        /** 그리드뷰에 들어갈 데이터 */
        fun bind(videos: VideoPicture) {
            img?.let {
                Glide.with(itemView)
                    .load("${videos.picture}")
                    .transform(CenterCrop())
                    .into(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridVideoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.second_video_item, parent, false)
        return GridVideoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GridVideoViewHolder, position: Int) {
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

    fun appendVideos(videos : List<VideoPicture>) {
        this.videos.addAll(videos)
        notifyDataSetChanged()
    }
}