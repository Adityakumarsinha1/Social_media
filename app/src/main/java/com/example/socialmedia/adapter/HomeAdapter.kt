package com.example.socialmedia.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

class HomeAdapter {
}
//
//    private val postList: ArrayList<Posts> = ArrayList()
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemview = LayoutInflater.from(parent.context).inflate(
//            R.layout.myposts,
//            parent,false)
//
////        val viewHolder = MyViewHolder(itemview)
////        itemview.findViewById<Button>(R.id.comments)
////            .setOnClickListener {
////                listener.onItemClick(userList[viewHolder.absoluteAdapterPosition])
////            }
//
//        return MyViewHolder(itemview)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentitem = postList[position]
//
//        Log.d("@", "pp=${postList[position]}")
//
//        holder.username.setText(UserUtil.user?.username)
//
//        Glide.with(holder.itemView.context).load(currentitem.imageUrl).into(holder.imageurl)
//    }
//
//
//    override fun getItemCount(): Int {
//        return postList.size
//    }
//    @SuppressLint("NotifyDataSetChanged")
//    fun updatepostList(postList: List<Posts>)
//    {
//        this.postList.clear()
//        this.postList.addAll(postList)
//        notifyDataSetChanged()
//    }
//
//    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
//        val username: TextView = itemview.findViewById(R.id.postusername)
//        val time: TextView = itemview.findViewById(R.id.posttime)
//        val imageurl: ImageView = itemview.findViewById(R.id.postimage)
////        val follow: Button =itemview.findViewById(R.id.searchfragmentfollowbutton)
//    }
//}
//
//interface CommentButtonClicked {
//    fun onItemClick(item: Users)
//}
