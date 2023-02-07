package com.example.socialmedia.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmedia.R
import com.example.socialmedia.firebaseUser
import com.example.socialmedia.model.Posts
import com.example.socialmedia.model.Users
import com.example.socialmedia.util.UserUtil
import com.google.firebase.database.FirebaseDatabase

class ProfilePostAdapter (private val listener:CommentsButtonClicked): RecyclerView.Adapter<ProfilePostAdapter.MyViewHolder>() {

    private val postList: ArrayList<Posts> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.myposts,
            parent,false)


//        Log.d("@","ocvh=${postList}")


        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = postList[position]




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
            listener.onPostCommentClick(currentitem)
        }

        if (currentitem.likes?.size!=null)
        if (currentitem.likes?.contains(UserUtil.user?.username)!!)
        {
            Glide.with(holder.itemView.context)
                .load("")
                .placeholder(R.drawable.liked)
                .centerCrop()
                .into(holder.likeimage)
        }

        holder.username.text=UserUtil.user?.username
        holder.time.setText(currentitem.uploadtime)
        holder.caption.setText(currentitem.caption)
        holder.likecount.setText(currentitem.likes?.size.toString())
        holder.commentcount.setText(currentitem.comments?.size.toString())
        Glide.with(holder.itemView.context).load(currentitem.imageUrl).into(holder.imageurl)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
    fun updatePostList(postList : ArrayList<Posts>)
    {

        this.postList.clear()

        this.postList.addAll(postList)
//        postList.reverse()
        notifyDataSetChanged()
        Log.d("@@@","user=${postList}")
    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val username: TextView = itemview.findViewById(R.id.postusername)
        val time: TextView = itemview.findViewById(R.id.posttime)
        val caption:TextView = itemview.findViewById(R.id.profilepostcaption)
        val imageurl: ImageView = itemview.findViewById(R.id.profilepostimage)
        val likecount:TextView = itemview.findViewById(R.id.likecount)
        val commentcount:TextView = itemview.findViewById(R.id.commentscount)


        //        On click blocks
        val likeimage:ImageView = itemview.findViewById(R.id.likebutton)
        val sharepost: ImageView = itemview.findViewById(R.id.sharepostbutton)
        val postcomment: ImageView = itemview.findViewById(R.id.postcommentbutton)
        val likeblock: LinearLayout = itemview.findViewById(R.id.likeblock)
        val commentblock:LinearLayout = itemview.findViewById(R.id.commentblock)

    }
}

interface CommentsButtonClicked {
    fun onCommentClick(item: Posts)
    fun onLikeClick(item: Posts)
    fun onPostCommentClick(item: Posts)
    fun onShareClick(item: Posts)
}