package com.sayamphoo.kruskals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sayamphoo.kruskals.R

class ViewPageAdapter() : RecyclerView.Adapter<ViewPageAdapter.ViewHolder>() {

    private val img = arrayListOf<Int>(
        R.drawable.view_1,
        R.drawable.view_2,
        R.drawable.view_3,
        R.drawable.view_4,
        R.drawable.view_5,
        R.drawable.view_6,
        R.drawable.view_7,
    )

    class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById<ImageView>(R.id.imgViewPage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_page,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(img[position])
    }

    override fun getItemCount(): Int {
        return img.size
    }
}