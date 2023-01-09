package com.example.socialmedia.model

data class Users(
    val uid: String? ="",
    val fullname: String? ="",
    val email: String? ="",
    val bio: String? ="",
    var username: String? ="",
    val imageUrl: String? =""
)