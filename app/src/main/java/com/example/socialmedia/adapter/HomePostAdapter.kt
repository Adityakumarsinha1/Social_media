package com.example.socialmedia.adapter

import android.annotation.SuppressLint
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

        val viewHolder = MyViewHolder(itemview)

        itemview.findViewById<ImageView>(R.id.comments).setOnClickListener {
            listener.onCommentClick(postList[viewHolder.absoluteAdapterPosition])
        }
        itemview.findViewById<ImageView>(R.id.likebutton).setOnClickListener {
            listener.onLikeClick(postList[viewHolder.absoluteAdapterPosition])
        }
        itemview.findViewById<ImageView>(R.id.postcommentbutton).setOnClickListener {
            listener.onPostCommentClick(postList[viewHolder.absoluteAdapterPosition])
        }
        itemview.findViewById<ImageView>(R.id.sharepostbutton).setOnClickListener {
            listener.onShareClick(postList[viewHolder.absoluteAdapterPosition])
        }

        return MyViewHolder(itemview)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = postList[position]
        val currentowner=owner[position]


        val calendar = currentitem.uploadtime!!.toLong()
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
//        Log.d("@@@","uu=${calendar}")
        val dateTime = simpleDateFormat.format(calendar).toString()

//        Log.d("@@@","u=${currentitem.uploadtime}")

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


    }
}

interface CommentButtonClicked {
    fun onCommentClick(item: Posts)
    fun onLikeClick(item: Posts)
    fun onPostCommentClick(item: Posts)
    fun onShareClick(item: Posts)
}
