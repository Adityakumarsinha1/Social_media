package com.example.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.adapter.MessageAdapter
import com.example.socialmedia.databinding.ActivityChatRoomBinding
import com.example.socialmedia.model.Message
import com.example.socialmedia.util.UserUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatRoomActivity : AppCompatActivity() {



    private lateinit var binding: ActivityChatRoomBinding
    private lateinit var messageRV: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var database: FirebaseDatabase


    var recevierroom:String?= null
    var senderroom:String?= null

    companion object{
        val CUIMG= "url_extra"
        const val CUID="id_extra"
        const val CUNAME="name_extra"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val receiverID = intent.getStringExtra(CUID)
        val receiverName = intent.getStringExtra(CUNAME)
        val receiverIMG = intent.getStringExtra(CUIMG)

        messageRV=findViewById(R.id.messageRV)
        messageList= ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        database = FirebaseDatabase.getInstance("https://socialmedia-e0647-default-rtdb.asia-southeast1.firebasedatabase.app")
        messageRV.layoutManager = LinearLayoutManager(this)
        messageRV.adapter=messageAdapter

        senderroom= receiverID+UserUtil.user?.uid
        recevierroom=UserUtil.user?.uid+ receiverID

        binding.sendersname.text=receiverName


        database.reference.child("Chats").child(senderroom!!)
            .child("Messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for (postsnapshot in snapshot.children)
                    {
                        val message = postsnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })



        binding.backtomlist.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        binding.sendmessageb.setOnClickListener {
            if (binding.themessage.toString().isNotEmpty()) {

                val messageObject = Message(
                    binding.themessage.text.toString(),
                    System.currentTimeMillis().toString(),
                    UserUtil.user?.uid)

                database.reference.child("Chats").child(senderroom!!)
                    .child("Messages").push()
                    .setValue(messageObject).addOnSuccessListener {
                        database.reference.child("Chats").child(recevierroom!!)
                            .child("Messages").push()
                            .setValue(messageObject)
                    }
//                Log.d("@@@", "messgae=${binding.themessage.text}")
            } else {
                Toast.makeText(this, "Type the message to send", Toast.LENGTH_SHORT).show()
            }


            binding.themessage.setText("")
        }
    }

}