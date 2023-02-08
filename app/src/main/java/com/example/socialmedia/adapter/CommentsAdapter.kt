package com.example.socialmedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import de.hdodenhof.circleimageview.CircleImageView

class CommentsAdapter(val context: Context, val commentList: ArrayList<ArrayList<String>>): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.mycomments,
            parent,false)

//        Log.d("@","ocvh=${postList}")

        return CommentsViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder=holder as CommentsViewHolder
        val currentitem = commentList[position]
        holder.thecomment.setText(currentitem[1])
        holder.commmentusername.setText(currentitem[0])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }


    class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val thecomment = itemView.findViewById<TextView>(R.id.thecomment)
        val commmentusername = itemView.findViewById<TextView>(R.id.commentusername)
    }
}