package com.example.socialmedia.model

data class Posts(
    val imageUrl: String? ="",
    val caption: String? ="",
    val uploadtime: String? ="",
    var likes: ArrayList<String>? =null,
    val comments: ArrayList<ArrayList<String>>? =null
)