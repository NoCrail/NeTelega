package com.home.netelega.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.home.netelega.R
import com.home.netelega.structures.Message

class MessagingAdapter (private val messages: List<Message>, private val userId: String, private val opponentName: String): RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() { //, private val actionClicker: ActionClick
    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val senderNameTv: TextView = view.findViewById(R.id.sender_name_tv)
        val messageTv: TextView = view.findViewById(R.id.message_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_layout, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.messageTv.text = message.msg
        if(message.senderId==userId) holder.senderNameTv.text = "Me" else holder.senderNameTv.text = opponentName
    }

//    interface ActionClick {
//        fun onDialogClick(dialog: Dialog)
//    }
}