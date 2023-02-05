package com.example.socialmedia.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.ChatRoomActivity
import com.example.socialmedia.R
import com.example.socialmedia.adapter.ChatlistAdapter
import com.example.socialmedia.adapter.ContactClicked
import com.example.socialmedia.databinding.FragmentNotificationBinding
import com.example.socialmedia.model.Users
import com.example.socialmedia.util.UserUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationFragment  : Fragment(R.layout.fragment_notification) , ContactClicked{
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
        useradapter = ChatlistAdapter(this)
        recyclerView.adapter = useradapter
        showlist()
    }

    private fun showlist() {

        mUserlist.clear()

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

    override fun onItemClick(item: Users) {


        val intent = Intent(activity, ChatRoomActivity::class.java)
        intent.putExtra(ChatRoomActivity.CUID ,item.uid)
        intent.putExtra(ChatRoomActivity.CUNAME ,item.username)
        intent.putExtra(ChatRoomActivity.CUIMG ,item.imageUrl)
        startActivity(intent)


        Toast.makeText(context, "Chat box should open here $item", Toast.LENGTH_SHORT).show()
    }

}