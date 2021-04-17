package com.example.helloworldandroid.ui.greetings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworldandroid.R
import com.example.helloworldandroid.greetings.Greeting
import com.example.helloworldandroid.ui.ItemClickListener

class GreetingAdapter(var itemClickListener: ItemClickListener<Greeting>?): RecyclerView.Adapter<GreetingAdapter.GreetingViewHolder>() {
    private val greetings = mutableListOf<Greeting>()

    fun setGreetings(greeting: List<Greeting>){
        greetings.clear()
        greetings.addAll(greeting)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreetingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_greeting, parent, false)
        return GreetingViewHolder(view)
    }

    override fun getItemCount() = greetings.size

    override fun onBindViewHolder(holder: GreetingViewHolder, position: Int) {
        val greeting = greetings[position]
        holder.bind(greeting)
    }

    inner class GreetingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(greeting: Greeting){
            itemView.findViewById<TextView>(R.id.greetingTextView).text = greeting.text
            itemView.setOnClickListener { this@GreetingAdapter.itemClickListener?.onItemClick(greeting, itemView) }
        }
    }

}
