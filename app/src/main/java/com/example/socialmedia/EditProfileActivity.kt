package com.example.socialmedia

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
//import com.squareup.picasso.Picasso



val firebaseUser=FirebaseAuth.getInstance().currentUser!!

class EditProfileActivity : AppCompatActivity() {
    private lateinit var bio: EditText
    private lateinit var fullname: EditText
    private lateinit var username: EditText
    private lateinit var deleteaccount: Button
    private lateinit var changephoto: TextView
    private lateinit var submitedit: ImageButton
    private lateinit var dontedit: ImageButton


    private lateinit var sbio:String
    private lateinit var sfullname:String
    private lateinit var susername:String


    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)


        auth = Firebase.auth

        bio = findViewById(R.id.edit_bio)
        fullname = findViewById(R.id.edit_fullname)
        username = findViewById(R.id.edit_username)
        submitedit = findViewById(R.id.submitedit)
        dontedit = findViewById(R.id.dontedit)
        changephoto = findViewById(R.id.edit_changephoto)
        deleteaccount = findViewById(R.id.delete_account)



        dontedit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        submitedit.setOnClickListener {
            sbio = bio.text.toString().trim()
            sfullname = fullname.text.toString().trim()
            susername = username.text.toString().trim()

            when {
                TextUtils.isEmpty(sfullname) -> {
                    Toast.makeText(this, "Full Name is required", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(susername) -> {
                    Toast.makeText(this, "username is required", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val userRef: DatabaseReference = FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child("Users")
                    //using hashmap to store values
                    val userMap = HashMap<String, Any>()
                    userMap["fullname"] = sfullname
                    userMap["username"] = susername
                    userMap["bio"] = sbio

                    userRef.child(firebaseUser.uid).updateChildren(userMap)

                    Toast.makeText(this, "Account is updated", Toast.LENGTH_SHORT).show()

                    //forward to home page using intent
                }
            }
        }
        }
}