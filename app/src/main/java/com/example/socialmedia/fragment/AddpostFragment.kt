package com.example.socialmedia.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentAddpostBinding
import com.example.socialmedia.databinding.FragmentHomeBinding


class AddpostFragment  : Fragment(R.layout.fragment_addpost) {

    private var _binding: FragmentAddpostBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddpostBinding.bind(view)
    }

}