package com.example.socialmedia.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmedia.EditProfileActivity
import com.example.socialmedia.LoginActivity
import com.example.socialmedia.R
import com.example.socialmedia.adapter.CommentsButtonClicked
import com.example.socialmedia.adapter.ProfilePostAdapter
import com.example.socialmedia.adapter.Useradapter
import com.example.socialmedia.databinding.FragmentProfileBinding
import com.example.socialmedia.model.Posts
import com.example.socialmedia.model.Users
import com.example.socialmedia.util.UserUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment  : Fragment(R.layout.fragment_profile) , CommentsButtonClicked{


    var mPost = ArrayList<Posts>()
    lateinit var useradapter: ProfilePostAdapter
    lateinit var recyclerView: RecyclerView

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProfileBinding.bind(view)



        recyclerView=view.findViewById(R.id.posts_recyclerview)
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        useradapter=ProfilePostAdapter(this)
        recyclerView.adapter=useradapter



        binding.apply {
            fullname.text=UserUtil.user?.fullname.toString()
            username.text = UserUtil.user?.username.toString()
            profileToolbar.title = UserUtil.user?.username.toString()
            bio.text = UserUtil.user?.bio.toString()
            followerscount.text=UserUtil.followers.count().toString()
            followngcount.text=UserUtil.following.count().toString()
            postcount.text=mPost.count().toString()
//            Log.d("@@@","mpost=${mPost}")
        }

        Glide.with(requireContext())
            .load(UserUtil.user?.imageUrl.toString())
            .placeholder(R.drawable.profile)
            .centerCrop()
            .into(binding.profileImage)


        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            context?.startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
            }

          binding.editprofile.setOnClickListener {
            context?.startActivity(Intent(activity, EditProfileActivity::class.java))
            activity?.finish()
            }
        showpost()
    }



    private fun showpost() {

        val query= FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
            .reference
            .child("Posts")
            .child(UserUtil.user?.uid.toString())
            .orderByChild("uploadtime")


        query.addValueEventListener(object: ValueEventListener
        {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(datasnapshot: DataSnapshot) {

                mPost.clear()

                for(snapshot in datasnapshot.children)
                {
                    //searching all users
//                    Log.d("@@","user=${snapshot}")
                    val post=snapshot.getValue(Posts::class.java)
                    if(post!=null)
                    {
                        mPost.add(post)
                    }
                }
//                showing post counts
                binding.postcount.text=mPost.count().toString()
//                Log.d("@@@","mpost2=${mPost}")
                useradapter.updatePostList(mPost)
            }
        })
    }

    override fun onCommentClick(item: Posts) {
        TODO("Not yet implemented")
    }

    override fun onLikeClick(item: Posts) {
        TODO("Not yet implemented")
    }

    override fun onPostCommentClick(item: Posts) {
        TODO("Not yet implemented")
    }

    override fun onShareClick(item: Posts) {
        TODO("Not yet implemented")
    }


}