package com.example.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private lateinit var email: EditText
private lateinit var password: EditText
private lateinit var login: Button
private lateinit var notregisterd: TextView

private lateinit var semail:String
private lateinit var spassword:String


private lateinit var auth: FirebaseAuth


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        email = findViewById(R.id.email)
        password = findViewById(R.id.password_l)
        login = findViewById(R.id.login_button)
        notregisterd = findViewById(R.id.havenotregisterd)

        notregisterd.setOnClickListener {
            semail = email.text.toString().trim()
            spassword = password.text.toString().trim()

            val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
            mAuth.signInWithEmailAndPassword(semail, spassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        progressDialog.dismiss()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    } else {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Password or Email Invalid", Toast.LENGTH_LONG).show()
                        mAuth.signOut()
//                        progressDialog.dismiss()
                    }
                }
        }
        notregisterd.setOnClickListener()
        {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}