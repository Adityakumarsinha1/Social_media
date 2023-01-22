package com.example.socialmedia.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmedia.R
import com.example.socialmedia.model.Posts
import com.example.socialmedia.util.UserUtil

class HomePostAdapter (private val listener:CommentsButtonClicked): RecyclerView.Adapter<HomePostAdapter.MyViewHolder>() {

    private val postList: ArrayList<Posts> = ArrayList()
    private val owner: ArrayList<ArrayList<String>> = ArrayList()


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
        val currentowner=owner[position]
        Log.d("@@","pp=${currentowner[1]}")

        holder.username.text= currentowner[0]
        holder.time.setText(currentitem.uploadtime)
        holder.caption.setText(currentitem.caption)
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
        Log.d("@@@","user=${postList}")
    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val username: TextView = itemview.findViewById(R.id.postusername)
        val time: TextView = itemview.findViewById(R.id.posttime)
        val caption: TextView = itemview.findViewById(R.id.profilepostcaption)
        val imageurl: ImageView = itemview.findViewById(R.id.profilepostimage)

    }
}
