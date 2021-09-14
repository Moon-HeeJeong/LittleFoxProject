package com.example.littlefoxproject.first.models

data class VideosResponse(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val total_results: Int,
    val url: String,
    val videos: List<Video>
)