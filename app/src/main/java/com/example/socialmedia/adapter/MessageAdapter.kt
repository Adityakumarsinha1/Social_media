package com.example.socialmedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmedia.R
import com.example.socialmedia.model.Message
import com.example.socialmedia.util.UserUtil
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat

class MessageAdapter (val context: Context , val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


//    Log.d("@@@", "userneme=$")
    

    var ITEM_RECEIVE = 1
    var ITEM_SENT = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType ==1)
        {
//            inflating recived block
            val itemview = LayoutInflater.from(context).inflate(
                R.layout.recievedtext,
                parent, false)

            return ReceivedViewHolder(itemview)
        }
        else{
//            inflating sent block
            val itemview = LayoutInflater.from(context).inflate(
                R.layout.mytext,
                parent, false)

            return SentViewHolder(itemview)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentmessage = messageList[position]

        if (holder.javaClass==SentViewHolder::class.java)
        {

            val calendar = currentmessage.time!!.toLong()
            val simpleDateFormat = SimpleDateFormat("dd/HH/MM")
            val dateTime = simpleDateFormat.format(calendar).toString()


            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text=currentmessage.message
            holder.sentTime.text=dateTime
        }
        else{
            val viewHolder = holder as ReceivedViewHolder

            val calendar = currentmessage.time!!.toLong()
            val simpleDateFormat = SimpleDateFormat("dd/HH/MM")
            val dateTime = simpleDateFormat.format(calendar).toString()


            holder.receivedMessage.text =currentmessage.message
            holder.receivedTime.text=dateTime
        }
    }

    override fun getItemViewType(position: Int): Int {
        var currentmessage = messageList[position]

        if(UserUtil.user?.uid.equals(currentmessage.sendersUID))
        {
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }


//    Two view holders for sender and receiver each
    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val sentMessage = itemView.findViewById<TextView>(R.id.stext)
        val sentTime = itemView.findViewById<TextView>(R.id.stexttime)
    }

    class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val receivedMessage = itemView.findViewById<TextView>(R.id.rtext)
        val receivedTime = itemView.findViewById<TextView>(R.id.rtexttime)
        val imageUrl = itemView.findViewById<CircleImageView>(R.id.senderspp)
    }
}