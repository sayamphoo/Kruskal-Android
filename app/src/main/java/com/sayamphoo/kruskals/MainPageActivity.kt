package com.sayamphoo.kruskals

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sayamphoo.kruskals.model.KruskalQuiz
import com.sayamphoo.kruskals.retrofit.APIClient
import com.sayamphoo.kruskals.retrofit.Kruskal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainPageActivity : AppCompatActivity() {

    var apiResponse: ArrayList<KruskalQuiz>? = null
    private  var shareData: SharedPreferences? = null
    private lateinit var showScore: TextView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        getDatabase()
        connectNewwork()

        shareData = getSharedPreferences("KRUSKAL", Context.MODE_PRIVATE)
        showScore = findViewById(R.id.showHighScore)
        showScore.text = shareData?.getInt("highs-core", 0).toString()


        val mKruskalTutorial = findViewById<LinearLayout>(R.id.kruskalTutorial)

        mKruskalTutorial.setOnClickListener {
            val url = "https://myalgo.wordpress.com/2011/09/24/kruskals-algorithm/"
            val mIntent = Intent(this, WebViewActivity::class.java)
            mIntent.putExtra("URL_WEB", url)
            startActivity(mIntent)
        }

        val mMinimumTutorial = findViewById<LinearLayout>(R.id.minimumTutorial)
        mMinimumTutorial.setOnClickListener {
            val url =
                "https://www.hackerearth.com/practice/algorithms/graphs/minimum-spanning-tree/tutorial/"
            val mIntent = Intent(this, WebViewActivity::class.java)
            mIntent.putExtra("URL_WEB", url)
            startActivity(mIntent)
        }

        val mButtPlayKruskal = findViewById<LinearLayout>(R.id.buttPlayKruskal)
        mButtPlayKruskal.setOnClickListener {
            if (apiResponse != null) {
                val mIntent = Intent(this, KruskalActivity::class.java)
                mIntent.putParcelableArrayListExtra("QUIZ_QQ", apiResponse)
                startActivity(mIntent)
            }
        }
    }

    private fun getDatabase() {
        val retrofit = APIClient.getInstance()
        val apiInterface = retrofit.create(Kruskal::class.java)

        //ดึงข้อมูลจาก Api
        apiInterface.getKruskalQuiz().enqueue(object : Callback<List<KruskalQuiz>> {
            override fun onResponse(
                call: Call<List<KruskalQuiz>>,
                response: Response<List<KruskalQuiz>>
            ) {
                apiResponse = response.body() as ArrayList<KruskalQuiz>
                Log.d("sad", apiResponse!![0].branch.toString())
            }

            override fun onFailure(call: Call<List<KruskalQuiz>>, t: Throwable) {}
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun connectNewwork() {
        val connect:ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ac = connect.activeNetwork
        if (ac != null){
            Log.d("Test-connect-network","true")
        } else {
            Log.d("Test-connect-network","false")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

    }

    override fun onResume() {
        super.onResume()
        if (shareData != null){
            showScore.text = shareData?.getInt("highs-core", 0).toString()
        }

    }
}