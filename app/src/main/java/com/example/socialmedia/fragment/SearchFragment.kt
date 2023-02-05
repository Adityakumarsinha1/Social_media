package com.example.socialmedia.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmedia.R
import com.example.socialmedia.adapter.FollowButtonClicked
import com.example.socialmedia.adapter.Useradapter
import com.example.socialmedia.databinding.FragmentSearchBinding
import com.example.socialmedia.firebaseUser
import com.example.socialmedia.model.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment  : Fragment(R.layout.fragment_search) , FollowButtonClicked{

    val mUser = ArrayList<Users>()
    lateinit var useradapter: Useradapter
//    binding our fragment with the nav iew

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        binding.searchRV.layoutManager = LinearLayoutManager(context)
        useradapter = Useradapter(this)
        binding.searchRV.adapter = useradapter


        binding.searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            @SuppressLint("SuspiciousIndentation")
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty())
                searchUser(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchUser(newText.toString())
                return false
            }
        })
    }


    private fun searchUser(input:String) {

        val query=FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
            .reference
            .child("Users")
            .orderByChild("username")
            .startAt(input)
            .endAt(input + "\uf8ff")

        query.addValueEventListener(object:ValueEventListener
        {
            override fun onCancelled(error: DatabaseError) {

            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(datasnapshot: DataSnapshot) {

                mUser.clear()

                for(snapshot in datasnapshot.children)
                {
                    //searching all users
                    val user=snapshot.getValue(Users::class.java)
                    if(user!=null)
                    {
                        mUser.add(user)
                    }
                }
                useradapter.updateUserList(mUser)
            }
        })
    }



    override fun onItemClick(item:Users){
    firebaseUser.uid.let { it1 ->
        FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app").reference
            .child("Follow").child(it1)
            .child("Following").get().addOnSuccessListener {
//                    Log.d("@@@@@@@@","user=${it}")
                if (it.hasChild(item.uid.toString())) {
                    //        unfollowing users
                    firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                            .child("Follow").child(it1)
                            .child("Following").child(item.uid.toString())
                            .removeValue()
                            .addOnCompleteListener { task -> //reversing following action
                                if (task.isSuccessful) {
                                    firebaseUser.uid.let { it1 ->
                                        FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                                            .child("Follow").child(item.uid.toString())
                                            .child("Followers").child(it1)
                                            .removeValue()

                                    }
                                }
                            }
                    }
                }
                else {
//                            following users
                    firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                            .child("Follow").child(it1)
                            .child("Following").child(item.uid.toString())
                            .setValue(true).addOnSuccessListener {
                                    firebaseUser.uid.let { it1 ->
                                        FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                                            .child("Follow").child(item.uid.toString())
                                            .child("Followers").child(it1)
                                            .setValue(true)
                                    }
                            }
                    }
                }
            }
        }
    }
}
