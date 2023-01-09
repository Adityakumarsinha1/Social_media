package com.example.socialmedia.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.adapter.Useradapter
import com.example.socialmedia.databinding.FragmentHomeBinding
import com.example.socialmedia.databinding.FragmentSearchBinding
import com.example.socialmedia.model.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User

class SearchFragment  : Fragment(R.layout.fragment_search) {

    var mUser: Users? = null
    lateinit var searchrecyclerview: RecyclerView
    private lateinit var viewModel:Users
    lateinit var adapter: Useradapter


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
    }

    fun getPostOnViewCreated(view: View,savedInstanceState: Bundle?)
    {
        super.onViewCreated(view,savedInstanceState)

        searchrecyclerview = view.findViewById(R.id.searchRV)
        searchrecyclerview.layoutManager = LinearLayoutManager(context)
        searchrecyclerview.setHasFixedSize(true)
        adapter= Useradapter()
        searchrecyclerview.adapter=adapter

//        viewModel = ViewModelProvider(this).get(Users::class.java)
    }




    private fun retrieveUser()
    {
        val usersSearchRef=FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app").
        reference.child("Users")//table name:Users
        usersSearchRef.addValueEventListener(object:ValueEventListener
        {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Could not read from Database", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapShot in dataSnapshot.children) {
                    val user = snapShot.getValue(Users::class.java)
                    val fullName = snapShot.child("fullname").value.toString()
                    val userName = snapShot.child("username").value.toString()
                    val bio = snapShot.child("bio").value.toString()
                    val image = snapShot.child("image").value.toString()
                    val uid = snapShot.child("uid").value.toString()

                    Users(userName, fullName, bio, image, uid)
                    if (user != null) {
                        mUser?.add(Users(userName, fullName, bio, image, uid).toString())
                    }
                    searchrecyclerview?.notifyDataSetChanged()
                }
            }
        })
    }







    private fun searchUser(input:String) {

        val query= FirebaseDatabase.getInstance().reference
            .child("Users")
            .orderByChild("username")
            .startAt(input)
            .endAt(input + "\uf8ff")

        query.addValueEventListener(object: ValueEventListener
        {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(datasnapshot: DataSnapshot) {
                mUser?.to(null)

                for(snapshot in datasnapshot.children)
                {
                    //searching all users
                    val user=snapshot.getValue(Users::class.java)
                    if(user!=null)
                    {
                        mUser?.add(user)
                    }
                }
                useradapter?.notifyDataSetChanged()
            }
        })
    }




}