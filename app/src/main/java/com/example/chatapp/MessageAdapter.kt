package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList : ArrayList<Message>) : RecyclerView.Adapter<ViewHolder>() {

    val ITEM_RECEIVED = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View
        if(viewType == ITEM_RECEIVED){
            view = LayoutInflater.from(parent.context).inflate(R.layout.received_layout, parent, false)
            return ReceivedViewHolder(view)
        }
        else{
            view = LayoutInflater.from(parent.context).inflate(R.layout.sent_layout, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        val msgUid = currentMessage.senderId
        if(FirebaseAuth.getInstance().currentUser?.uid == msgUid){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVED
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            viewHolder.tvSentMsg.text = currentMessage.message
        }
        else {
            val viewHolder = holder as ReceivedViewHolder
            viewHolder.tvReceivedMsg.text = currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    inner class SentViewHolder(itemView : View) : ViewHolder(itemView) {
        val tvSentMsg = itemView.findViewById<TextView>(R.id.tvSentMsg)
    }

    inner class ReceivedViewHolder(itemView : View) : ViewHolder(itemView) {
        val tvReceivedMsg = itemView.findViewById<TextView>(R.id.tvReceivedMsg)
    }
}