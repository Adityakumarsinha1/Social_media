package com.example.socialmedia.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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


        Log.d("@","ocvh=${postList}")
        val viewHolder = MyViewHolder(itemview)

        itemview.setOnClickListener {
                listener.onItemClick(postList[viewHolder.absoluteAdapterPosition])
            }

        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = postList[position]

        Log.d("@@","pp=${currentitem}")

        holder.username.text=UserUtil.user?.username
        holder.time.setText(currentitem.uploadtime)
        holder.caption.setText(currentitem.caption)
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

    }
}

interface CommentsButtonClicked {
    fun onItemClick(item: Posts)
}