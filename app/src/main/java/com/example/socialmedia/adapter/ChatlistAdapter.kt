package com.example.socialmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmedia.R
import com.example.socialmedia.model.Posts
import com.example.socialmedia.model.Users
import java.util.ArrayList

class ChatlistAdapter: RecyclerView.Adapter<ChatlistAdapter.MyViewHolder>() {

    var chatList=ArrayList<Users>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.contacts,
            parent,false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ChatlistAdapter.MyViewHolder, position: Int) {

        val currentitem = chatList[position]

        holder.username.setText(currentitem.username)
        holder.message.setText(currentitem.email)
        Glide.with(holder.itemView.context)
            .load(currentitem.imageUrl)
            .placeholder(R.drawable.profile)
            .into(holder.imageurl)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun updateChatList(chatList : ArrayList<Users>)
    {

        this.chatList.clear()

        this.chatList.addAll(chatList)
        notifyDataSetChanged()

    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val username: TextView = itemview.findViewById(R.id.contactusername)
        val message: TextView = itemview.findViewById(R.id.contactmessage)
        val imageurl: ImageView = itemview.findViewById(R.id.contactprofile)
        }
}