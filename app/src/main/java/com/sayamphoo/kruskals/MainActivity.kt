package com.sayamphoo.kruskals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val shared = getSharedPreferences("KRUSKAL", Context.MODE_PRIVATE)

        Thread {
            Thread.sleep(3000)
            if (shared.getBoolean("TUTORIAL", true)) {
                startActivity(Intent(this, InstructionActivity::class.java))
            } else {
                startActivity(Intent(this, MainPageActivity::class.java))
            }
            finish()
        }.start()

    }
}

//Thread {
//    Thread.sleep((4.5*1000).toLong())
//    startActivity(Intent(this,MainPageActivity::class.java))
//}.start()