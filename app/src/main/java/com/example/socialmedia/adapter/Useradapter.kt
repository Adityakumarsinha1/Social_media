package com.example.socialmedia.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmedia.R
import com.example.socialmedia.model.Users
import com.example.socialmedia.util.UserUtil
import com.google.firebase.firestore.auth.User

class Useradapter: RecyclerView.Adapter<Useradapter.MyViewHolder>() {

    private val userList=ArrayList<Users>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item_layout,
            parent,false
        )
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem= userList[position]

        holder.fullname.text=currentitem.fullname
        holder.username.text=currentitem.username
       Glide.with(holder.itemView.context).load(currentitem.imageUrl).into(holder.imageurl)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateUserList(userList : List<Users>)
    {
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val username: TextView = itemview.findViewById(R.id.searchfragmentuserusername)
        val fullname: TextView = itemview.findViewById(R.id.searchfragmentuserusername)
        val imageurl: ImageView = itemview.findViewById(R.id.searchfragmentuserimage)

    }
}