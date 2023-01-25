package com.example.socialmedia.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.adapter.ChatlistAdapter
import com.example.socialmedia.adapter.HomePostAdapter
import com.example.socialmedia.databinding.FragmentHomeBinding
import com.example.socialmedia.databinding.FragmentNotificationBinding
import com.example.socialmedia.model.Posts
import com.example.socialmedia.model.Users
import com.example.socialmedia.util.UserUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class NotificationFragment  : Fragment(R.layout.fragment_notification) {
    var mUserlist =ArrayList<Users>()
    lateinit var useradapter: ChatlistAdapter
    lateinit var recyclerView: RecyclerView

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotificationBinding.bind(view)

        binding.contacts.layoutManager = LinearLayoutManager(context)


        recyclerView = view.findViewById(R.id.contacts)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        useradapter = ChatlistAdapter()
        recyclerView.adapter = useradapter
        showlist()
    }

    private fun showlist() {


        for (snap in UserUtil.following) {
            val query =
                FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .reference
                    .child("Users")
                    .child(snap)

            query.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }
                override fun onDataChange(datasnapshot: DataSnapshot) {
                    Log.d("@@@", "uu=${datasnapshot}")

                    val user = datasnapshot.getValue(Users::class.java)

                    if (user != null) {
                        mUserlist.add(user)
                        useradapter.updateChatList(mUserlist)
                    }
                }
            })
        }
    }

}