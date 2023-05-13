package com.dan.chatpapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dan.chatpapp.R
import com.dan.chatpapp.entities.MessagesList

class MessagesAdapter(var list: List<MessagesList>,private val context:Context): RecyclerView.Adapter<MessagesAdapter.TilleView>(){
    inner class TilleView(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TilleView {
        return TilleView(
            LayoutInflater.from(parent.context).inflate(R.layout.lo_messages_adapter,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TilleView, position: Int) {
        holder.itemView.apply {
            val profilePic = findViewById<ImageView>(R.id.imgProfile)
            val name = findViewById<TextView>(R.id.txtname)
            val lastmess = findViewById<TextView>(R.id.txtlastMess)
            val unseenMess = findViewById<TextView>(R.id.unseenMessages)
        }
    }

}