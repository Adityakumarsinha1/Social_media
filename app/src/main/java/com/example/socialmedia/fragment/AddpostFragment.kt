package com.example.socialmedia.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.socialmedia.EditProfileActivity
import com.example.socialmedia.MainActivity
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentAddpostBinding
import com.example.socialmedia.databinding.FragmentHomeBinding
import com.example.socialmedia.firebaseUser
import com.example.socialmedia.model.Posts
import com.example.socialmedia.model.Users
import com.example.socialmedia.util.UserUtil
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class AddpostFragment  : Fragment(R.layout.fragment_addpost) {

    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var imageUri: Uri


    private var _binding: FragmentAddpostBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddpostBinding.bind(view)

        database = FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
        storage = FirebaseStorage.getInstance()





        binding.postimage.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        binding.cancle.setOnClickListener {
            context?.startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
        binding.post.setOnClickListener {
            uploaddata()
        }

    }

    private fun uploaddata() {

        val reference = storage.reference.child("Posts").child(firebaseUser.uid)
        reference.putFile(imageUri).addOnCompleteListener {
            if (it.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener {  task ->
                    uploadinfo(task.toString())
//                            Log.d("@","user=${task.toString()}")

                }
            }
        }
    }

    private fun uploadinfo(toString: String) {
        val likes= ArrayList<String>()
        likes.add(UserUtil.user?.username.toString())
        val con= ArrayList<String>()
        con.add(UserUtil.user?.username.toString())
        con.add(binding.postcaption.text.toString())
        val comments= ArrayList<ArrayList<String>>()
        comments.add(con)

        val post= Posts(toString,
            binding.postcaption.text.toString(),
            System.currentTimeMillis().toString(),
            likes, comments)
        Log.d("@","user=${post}")
        database.reference.child("Posts").child(firebaseUser.uid).child(System.currentTimeMillis().toString())
            .setValue(post).addOnSuccessListener {
                context?.startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null)
        {
            if (data.data !=null)
            {
                imageUri=data.data!!
                binding.postimage.setImageURI(imageUri)
            }
        }
    }

}