package com.example.socialmedia.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmedia.R
import com.example.socialmedia.model.Posts
import com.example.socialmedia.util.UserUtil
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class HomePostAdapter (private val listener:CommentButtonClicked): RecyclerView.Adapter<HomePostAdapter.MyViewHolder>() {

    private val postList: ArrayList<Posts> = ArrayList()
    private val owner: ArrayList<ArrayList<String>> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.myposts,
            parent,false)


        Log.d("@","ocvh=${postList}")

        return MyViewHolder(itemview)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = postList[position]
        val currentowner=owner[position]

//        implementiong on clicks for like block
        holder.likeblock.setOnClickListener {
            listener.onLikeClick(currentitem)
        }

//        implementiong on clicks for comment block
        holder.commentblock.setOnClickListener {
            listener.onCommentClick(currentitem)
        }

//        implementiong on clicks for share button
        holder.sharepost.setOnClickListener {
            listener.onShareClick(currentitem)
        }

//        implementiong on clicks for post comment buttons
        holder.postcomment.setOnClickListener {
            if(!holder.comment.text.isNullOrEmpty())
            {
                var comment = ArrayList<String>()
                comment.add(UserUtil.user?.username.toString())
                comment.add(holder.comment.text.toString())
                comment.add(UserUtil.user?.uid.toString())
                currentitem.comments!!.add(comment)
                FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .reference
                    .child("Posts")
                    .child(currentowner[2])
                    .child(currentitem.uploadtime.toString()).child("comments")
                    .setValue(currentitem.comments).addOnSuccessListener {
                        holder.comment.setText("")
                    }
            }

        }


        val calendar = currentitem.uploadtime!!.toLong()
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateTime = simpleDateFormat.format(calendar).toString()

//        Log.d("@@@","u=${currentitem.uploadtime}")
        if (!currentitem.likes.isNullOrEmpty())
        if (currentitem.likes?.contains(UserUtil.user?.username)!!)
        {
            Glide.with(holder.itemView.context)
                .load("")
                .placeholder(R.drawable.liked)
                .centerCrop()
                .into(holder.likeimage)
        }

        holder.username.text= currentowner[0]
        holder.time.setText(dateTime)
        holder.caption.setText(currentitem.caption)
        holder.likecount.setText(currentitem.likes?.size.toString())
        holder.commentcount.setText(currentitem.comments?.size.toString())
        Glide.with(holder.itemView.context).load(currentitem.imageUrl).into(holder.imageurl)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun updateHomePostList(postList : ArrayList<Posts>,owner:ArrayList<ArrayList<String>>)
    {

        this.postList.clear()
        this.owner.clear()

        this.postList.addAll(postList)
        this.owner.addAll(owner)
//        postList.reverse()
        notifyDataSetChanged()

    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val username: TextView = itemview.findViewById(R.id.postusername)
        val time: TextView = itemview.findViewById(R.id.posttime)
        val caption: TextView = itemview.findViewById(R.id.profilepostcaption)
        val imageurl: ImageView = itemview.findViewById(R.id.profilepostimage)
        val likecount:TextView = itemview.findViewById(R.id.likecount)
        val commentcount:TextView = itemview.findViewById(R.id.commentscount)
        val comment:EditText = itemview.findViewById(R.id.commenttext)

//        On click blocks
        val likeimage:ImageView = itemview.findViewById(R.id.likebutton)
        val sharepost: ImageView = itemview.findViewById(R.id.sharepostbutton)
        val postcomment: ImageView = itemview.findViewById(R.id.postcommentbutton)
        val likeblock:LinearLayout= itemview.findViewById(R.id.likeblock)
        val commentblock:LinearLayout = itemview.findViewById(R.id.commentblock)
    }
}

interface CommentButtonClicked {
    fun onCommentClick(item: Posts)
    fun onLikeClick(item: Posts)
    fun onShareClick(item: Posts)
}
