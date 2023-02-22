package com.sayamphoo.kruskals

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.edit
import androidx.viewpager2.widget.ViewPager2
import com.sayamphoo.kruskals.adapter.ViewPageAdapter

class InstructionActivity : AppCompatActivity() {
   lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruction)

        sharedPreferences = getSharedPreferences("KRUSKAL", Context.MODE_PRIVATE)
        val mViewPage = findViewById<ViewPager2>(R.id.viewpageShowTutorial)
        mViewPage.adapter = ViewPageAdapter()

        val skip = findViewById<TextView>(R.id.skips)

        skip.setOnClickListener {
            if (sharedPreferences.getBoolean("TUTORIAL",true)) {
                sharedPreferences.edit {
                    putBoolean("TUTORIAL",false)
                }
            }

            startActivity(Intent(this,MainPageActivity::class.java))
            finish()
        }
    }
}