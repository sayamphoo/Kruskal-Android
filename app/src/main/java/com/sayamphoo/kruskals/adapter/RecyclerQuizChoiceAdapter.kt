package com.sayamphoo.kruskals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sayamphoo.kruskals.R

public class RecyclerQuizChoiceAdapter(val testData:List<String>) : RecyclerView.Adapter<RecyclerQuizChoiceAdapter.ViewHolder>() {
     lateinit var mItemClickChoiceListener: ItemClickChoiceListener
     
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textChoice: TextView = view.findViewById(R.id.textChoice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_butt_quiz, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textChoice.text = testData[position].toString()
        holder.textChoice.setOnClickListener {
            mItemClickChoiceListener.onClickItemChoice(testData[position])
        }
    }
    override fun getItemCount(): Int {
        return testData.size
    }

    public fun setItemClickChoiceListener(listener: ItemClickChoiceListener){
        mItemClickChoiceListener = listener
    }
}