package com.example.socialmedia

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase




//val database = Firebase.database

class RegisterActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password:EditText
    private lateinit var fullname:EditText
    private lateinit var username:EditText
    private lateinit var register: Button
    private lateinit var registerd: TextView

    private lateinit var semail:String
    private lateinit var spassword:String
    private lateinit var sfullname:String
    private lateinit var susername:String


    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth

        email = findViewById(R.id.email)
        password = findViewById(R.id.password_r)
        fullname = findViewById(R.id.fullname)
        username = findViewById(R.id.username)
        register = findViewById(R.id.register_button)
        registerd = findViewById(R.id.alreadyregisterd)

        register.setOnClickListener {

            semail = email.text.toString().trim()
            spassword = password.text.toString().trim()
            sfullname = fullname.text.toString().trim()
            susername = username.text.toString().trim()


            val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
            mAuth.createUserWithEmailAndPassword(semail, spassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUserInfo(sfullname, susername, semail)
                    } else {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error : $message", Toast.LENGTH_LONG).show()
//                    mAuth.signOut()
//                    progressDialog.dismiss()
                    }
                }
        }
        registerd.setOnClickListener()
        {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    private fun saveUserInfo(
        fullName: String,
        userName: String,
        email: String,
    ) {

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child("Users")
//        using hashmap to store values
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["fullname"] = fullName
        userMap["username"] = userName
        userMap["email"] = email
        userMap["bio"] = "Hey! I am using InstaApp"
        userMap["image"] = "image url"

        //pasting data in database
        userRef.child(currentUserId).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Account has been created", Toast.LENGTH_SHORT).show()

//                    to follow own account by default
                    FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                        .child("Follow").child(currentUserId)
                        .child("Following").child(currentUserId)
                        .setValue(true)

                    //forwarding to home page
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    } else {
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error : $message", Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                }
            }
    }
    }
