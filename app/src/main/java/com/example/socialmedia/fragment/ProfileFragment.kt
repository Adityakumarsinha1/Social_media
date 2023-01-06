package com.example.socialmedia.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.socialmedia.EditProfileActivity
import com.example.socialmedia.LoginActivity
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentHomeBinding
import com.example.socialmedia.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment  : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

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