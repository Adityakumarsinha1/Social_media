package com.example.socialmedia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.adapter.CommentsAdapter
import com.example.socialmedia.databinding.ActivityCommentsBinding


class CommentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommentsBinding
    private lateinit var commentRV: RecyclerView
    private lateinit var commentAdapter: CommentsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intent = intent
        val args = intent.getBundleExtra("BUNDLE")
        val comment = args!!.getSerializable("ARRAYLIST") as ArrayList<*>?

        commentRV=findViewById(R.id.commentsRV)
        commentAdapter = CommentsAdapter(this, comment as ArrayList<ArrayList<String>>)
        commentRV.layoutManager = LinearLayoutManager(this)
        commentRV.adapter=commentAdapter


        binding.backtohome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}