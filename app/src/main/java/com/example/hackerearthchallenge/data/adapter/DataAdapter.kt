package com.example.hackerearthchallenge.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hackerearthchallenge.R
import com.example.hackerearthchallenge.data.models.Item
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat


class DataAdapter(private val context: Context, private val list: List<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_A = 1
    val TYPE_B = 2

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(position%9 ==0){
            TYPE_B
        }else{
            TYPE_A
        }
    }

    inner class viewholder1 (val view: View):RecyclerView.ViewHolder(view){
        private val textView: TextView = view.findViewById(R.id.title)
        private val textView2: TextView = view.findViewById(R.id.basic_data)
        private val textView3: TextView = view.findViewById(R.id.posted_on)
        private val img: ImageView = view.findViewById(R.id.recyler_item)

        fun bind(position: Int) {
            textView.text = list[position].title
            textView2.text = list[position].owner.display_name
            val dat = list[position].creation_date
            val date = Date(dat * 1000L)
            val jdf = SimpleDateFormat("yyyy-MM-dd")
            val formatted_date = jdf.format(date)
            textView3.text = formatted_date

            Glide.with(context).load(list[position].owner.profile_image)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_android_24)
                .error(R.drawable.ic_baseline_android_24)
                .into(img)
        }
    }


    inner class viewholder2 (val view: View):RecyclerView.ViewHolder(view){
        private val textView5: TextView = view.findViewById(R.id.ad_text)

        fun bind(position: Int) {
            textView5.text = "This is Advertisement Item"
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is viewholder1 -> {
                holder.bind(position)
                holder.view.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(list[position].link))
                    startActivity(holder.view.context, intent, null);
                }
            }
            is viewholder2 ->{
                holder.bind(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == TYPE_A){
            viewholder1(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_home,parent,false))
        }else{
            viewholder2(LayoutInflater.from(parent.context).inflate(R.layout.ads_item,parent,false))
        }
    }

}