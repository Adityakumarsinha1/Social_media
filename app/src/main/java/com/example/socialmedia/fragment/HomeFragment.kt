package com.example.socialmedia.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.adapter.CommentsButtonClicked
import com.example.socialmedia.adapter.HomeAdapter
import com.example.socialmedia.adapter.HomePostAdapter
import com.example.socialmedia.adapter.ProfilePostAdapter
import com.example.socialmedia.databinding.FragmentHomeBinding
import com.example.socialmedia.model.Posts
import com.example.socialmedia.model.Users
import com.example.socialmedia.util.UserUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment(R.layout.fragment_home), CommentsButtonClicked {

    var mPost = ArrayList<Posts>()
    var owner = ArrayList<ArrayList<String>>()
    lateinit var useradapter: HomePostAdapter
    lateinit var recyclerView: RecyclerView

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)


        binding.recyclerviewHome.layoutManager = LinearLayoutManager(context)


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
                    FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
                        .reference
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
//                showing post counts
                        useradapter.updateHomePostList(mPost,owner)
                    }
                })
            }
        }

        override fun onItemClick(item: Posts) {
            TODO("Not yet implemented")
        }
    }