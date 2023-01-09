package com.example.socialmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.model.Users
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
//        holder.imageurl.text=currentitem.imageUrl
    }

    override fun getItemCount(): Int {
        return userList.size
    }
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