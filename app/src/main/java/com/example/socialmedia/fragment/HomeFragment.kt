package com.example.socialmedia.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.adapter.*
import com.example.socialmedia.databinding.FragmentHomeBinding
import com.example.socialmedia.firebaseUser
import com.example.socialmedia.model.Posts
import com.example.socialmedia.util.UserUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment(R.layout.fragment_home), CommentButtonClicked {

    var mPost = ArrayList<Posts>()
    var owner = ArrayList<ArrayList<String>>()
    var cowner = ArrayList<String>()
    private lateinit var database: FirebaseDatabase

    lateinit var useradapter: HomePostAdapter
    lateinit var recyclerView: RecyclerView

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)


        binding.recyclerviewHome.layoutManager = LinearLayoutManager(context)
        database =
            FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")


        recyclerView = view.findViewById(R.id.recyclerview_home)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        useradapter = HomePostAdapter(this)
        recyclerView.adapter = useradapter
        showpost()
    }

        private fun showpost() {

            mPost.clear()
            owner.clear()
            for(snap in UserUtil.following)
            {
                val query =
                    database.reference
                        .child("Posts")
                        .child(snap)
                        .orderByChild("uploadtime")


                query.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {

                    }

                    override fun onDataChange(datasnapshot: DataSnapshot) {



                        for (snapshot in datasnapshot.children) {
                            //searching all users
                            val post = snapshot.getValue(Posts::class.java)
                            if (post != null) {
                                mPost.add(post)
                                owner.addAll(listOf(post.comments!![0]))
                            }
                        }
                        useradapter.updateHomePostList(mPost,owner)
                    }
                })
            }
        }




//    implementing recycler view clicks

    override fun onCommentClick(item: Posts) {
        Toast.makeText(context, "Opening all comments", Toast.LENGTH_LONG).show()
    }

    override fun onLikeClick(item: Posts) {
        cowner.clear()
        cowner.addAll(item.comments!![0])

        if (item.likes.isNullOrEmpty())
        {
//            item.likes.equals(UserUtil.user?.username.toString())
            FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
                .reference
                .child("Posts")
                .child(cowner[2])
                .child(item.uploadtime.toString()).child("likes")
                .setValue(listOf( UserUtil.user?.username)).addOnSuccessListener {
                    showpost()
                    Toast.makeText(context, "like the o post", Toast.LENGTH_LONG).show()
                }

        }
        else if (item.likes!!.contains(UserUtil.user?.username))
        {
            item.likes!!.remove(UserUtil.user?.username.toString())
            FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
                .reference
                .child("Posts")
                .child(cowner[2].toString())
                .child(item.uploadtime.toString()).child("likes")
                .setValue(item.likes).addOnSuccessListener {
                    showpost()
                    Toast.makeText(context, "unliked the post", Toast.LENGTH_LONG).show()
                }

        }
        else
        {

            item.likes!!.add(UserUtil.user?.username.toString())
            FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
            .reference
            .child("Posts")
            .child(cowner[2].toString())
            .child(item.uploadtime.toString()).child("likes")
            .setValue(item.likes).addOnSuccessListener {
                    showpost()
                    Toast.makeText(context, "like the post", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onPostCommentClick(item: Posts) {
//        var comment = ArrayList<String>()
//        comment.add(UserUtil.user?.username.toString())
//        comment.add("")
//        comment.add(UserUtil.user?.uid.toString())
//        FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
//            .reference
//            .child("Posts")
//            .child(cowner[2].toString())
//            .child(item.uploadtime.toString()).child("comments")
//            .setValue(item.likes).addOnSuccessListener {
//                showpost()
//                Toast.makeText(context, "posted a comment", Toast.LENGTH_LONG).show()
//            }
    }

    override fun onShareClick(item: Posts) {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"check this meme out!  \n+${item.caption}+\n+${item.imageUrl}")
        val chooser= Intent.createChooser(intent,"share this Post using...")
        startActivity(chooser)
        Toast.makeText(context, "Share button clicked", Toast.LENGTH_LONG).show()
    }

}