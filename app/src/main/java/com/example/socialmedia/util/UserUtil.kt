package com.example.socialmedia.util

import android.util.Log
import android.widget.Toast
import com.example.socialmedia.firebaseUser
import com.example.socialmedia.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.FirebaseDatabase.*
import com.google.firebase.database.ktx.getValue

object UserUtil {
    var user: Users? = null
    private lateinit var database: FirebaseDatabase
    fun getCurrentUser() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            database = FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
            database.reference.child("Users").child(firebaseUser.uid)
                .get().
            addOnSuccessListener {
                user = it.getValue(Users::class.java)
                Log.d("@@@@","user=${user.toString()}")
            }
        }
    }
}