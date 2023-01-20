package com.example.socialmedia.adapter

import android.annotation.SuppressLint
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
import com.example.socialmedia.fragment.SearchFragment
import com.example.socialmedia.model.Users
import com.example.socialmedia.util.UserUtil.user
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.firestore.auth.User

class Useradapter(private val listener: FollowButtonClicked): RecyclerView.Adapter<Useradapter.MyViewHolder>() {

    private val userList=ArrayList<Users>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item_layout,
            parent,false
        )
        val viewHolder = MyViewHolder(itemview)
        itemview.findViewById<Button>(R.id.searchfragmentfollowbutton)
            .setOnClickListener {
                listener.onItemClick(userList[viewHolder.absoluteAdapterPosition])
            }

        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]

        holder.fullname.text = currentitem.fullname
        holder.username.text = currentitem.username
        Glide.with(holder.itemView.context).load(currentitem.imageUrl).into(holder.imageurl)


        firebaseUser?.uid.let { it1 ->
            FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                .child("Follow").child(it1.toString())
                .child("Following").get().addOnSuccessListener {
//                    Log.d("@@@@@@@@","user=${it}")
                    if (it.hasChild(currentitem.uid.toString())) {
                        holder.follow.setText("unfollow")
                    } else {
                        holder.follow.setText("follow")
                    }
                }
        }



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



//    fun checkFollowingStatus(){
//
//        firebaseUser?.uid.let { it1 ->
//            FirebaseDatabase.getInstance().reference
//                .child("Follow").child(it1.toString())
//                .child("Following").get().addOnSuccessListener {
//                    if (it.hasChild(user?.uid.toString()))
//                    {
//                        //        unfollowing users
//
//                        firebaseUser?.uid.let { it1 ->
//                            FirebaseDatabase.getInstance().reference
//                                .child("Follow").child(it1.toString())
//                                .child("Following").child(user?.uid.toString())
//                                .removeValue().addOnCompleteListener { task -> //reversing following action
//                                    if (task.isSuccessful) {
//                                        firebaseUser?.uid.let { it1 ->
//                                            FirebaseDatabase.getInstance().reference
//                                                .child("Follow").child(user?.uid.toString())
//                                                .child("Followers").child(it1.toString())
//                                                .removeValue()
//
//                                        }
//                                    }
//                                }
//                        }
//
//                    }
//                    else
//                    {
//                        //following users
//                        firebaseUser?.uid.let { it1 ->
//                            FirebaseDatabase.getInstance().reference
//                                .child("Follow").child(it1.toString())
//                                .child("Following").child(user?.uid.toString())
//                                .setValue(true).addOnCompleteListener { task ->
//                                    if (task.isSuccessful) {
//
//                                        firebaseUser?.uid.let { it1 ->
//                                            FirebaseDatabase.getInstance().reference
//                                                .child("Follow").child(user?.uid.toString())
//                                                .child("Followers").child(it1.toString())
//                                                .setValue(true)
//                                        }
//                                    }
//                                }
//                        }
//                    }
//                }
//        }
//    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val username: TextView = itemview.findViewById(R.id.searchfragmentuserusername)
        val fullname: TextView = itemview.findViewById(R.id.searchfragmentuserusername)
        val imageurl: ImageView = itemview.findViewById(R.id.searchfragmentuserimage)
        val follow: Button=itemview.findViewById(R.id.searchfragmentfollowbutton)
    }
}
interface FollowButtonClicked {
    fun onItemClick(item: Users)
}