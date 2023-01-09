package com.example.socialmedia.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.socialmedia.EditProfileActivity
import com.example.socialmedia.LoginActivity
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentHomeBinding
import com.example.socialmedia.databinding.FragmentProfileBinding
import com.example.socialmedia.util.UserUtil
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment  : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProfileBinding.bind(view)


//        Log.d("@@@@","user=${UserUtil.user.toString()}")

        binding.apply {
            fullname.text=UserUtil.user?.fullname.toString()
            username.text = UserUtil.user?.username.toString()
            profileToolbar.title = UserUtil.user?.username.toString()
            bio.text = UserUtil.user?.bio.toString()
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
    }

}