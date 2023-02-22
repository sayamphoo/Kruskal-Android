package com.sayamphoo.kruskals

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sayamphoo.kruskals.adapter.ItemClickChoiceListener
import com.sayamphoo.kruskals.adapter.RecyclerQuizChoiceAdapter
import com.sayamphoo.kruskals.dialog.QuizDialog
import com.sayamphoo.kruskals.model.KruskalQuiz
import java.util.regex.Matcher
import java.util.regex.Pattern

@Suppress("DEPRECATION")
class KruskalActivity : AppCompatActivity() {
    private var myProblem = (0..11).toMutableList()
    private var position = 0
    private var rank = 0

    var dataAnswer = arrayListOf<String>()
    private var exit = false
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mCountdown: CountDownTimer
    private lateinit var mDialog: QuizDialog
    private lateinit var mDataQuiz: KruskalQuiz
    private lateinit var shareData: SharedPreferences
    private var time:Double = 0.0
    private lateinit var dataQuiz: ArrayList<KruskalQuiz>
    private lateinit var img: ImageView
    private lateinit var choiceBranch: RecyclerView
    lateinit var textResult: TextView

    private val dataImg = arrayListOf(
        R.drawable.node_1, R.drawable.node_2,
        R.drawable.node_3, R.drawable.node_4, R.drawable.node_5,
        R.drawable.node_6, R.drawable.node_7, R.drawable.node_8,
        R.drawable.node_9, R.drawable.node_10, R.drawable.node_11,
        R.drawable.node_12
    )

    private val dataImgMst = arrayListOf(
        R.drawable.minimum_spanning_tree_1,
        R.drawable.minimum_spanning_tree_2,
        R.drawable.minimum_spanning_tree_3,
        R.drawable.minimum_spanning_tree_4,
        R.drawable.minimum_spanning_tree_5,
        R.drawable.minimum_spanning_tree_6,
        R.drawable.minimum_spanning_tree_7,
        R.drawable.minimum_spanning_tree_8,
        R.drawable.minimum_spanning_tree_9,
        R.drawable.minimum_spanning_tree_10,
        R.drawable.minimum_spanning_tree_11,
        R.drawable.minimum_spanning_tree_12
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kruskal)

        shareData = getSharedPreferences("KRUSKAL", Context.MODE_PRIVATE)
        mDialog = QuizDialog(this)
        //รับค่า intent มาจากหน้าก่อน
        dataQuiz = intent.parcelableArrayList("QUIZ_QQ")!!
        mProgressBar = findViewById(R.id.progressCountDown)
        img = findViewById(R.id.imgKruskal)
        choiceBranch = findViewById(R.id.choiceBranch)
        textResult = findViewById(R.id.textSelect)

        myProblem.shuffle()

        rank = myProblem[position]
        mDialog.setQuizListen(object : QuizDialog.QuizListen {
            override fun reLoadApp() {
                position = if (position == 11) {
                    0
                } else {
                    ++position
                }
                rank = myProblem[position]
                dataAnswer.removeAll(dataAnswer.toSet())
                textResult.text = dataAnswer.toString()
                pageContent()
            }
        })
        pageContent()
    }

    private fun pageContent() {

        mDataQuiz = dataQuiz[rank]
        img.setImageResource(dataImg[mDataQuiz.id])
        //Recycler for Choice

        choiceBranch.layoutManager = GridLayoutManager(this, 3)
        val adapter = RecyclerQuizChoiceAdapter(mDataQuiz.branch)
        choiceBranch.adapter = adapter

        //ข้อมูลคำตอบที่ User เลือก

        adapter.setItemClickChoiceListener(object : ItemClickChoiceListener {
            override fun onClickItemChoice(srt: String) {
                dataAnswer.add(srt)
                textResult.text = dataAnswer.toString()
            }
        })

        //ปุ่มย้อนกลับ หรือออกจากเกม
        val backFinish = findViewById<ImageView>(R.id.backFinish)
        backFinish.setOnClickListener {
            if (!exit) {
                Toast.makeText(this, "กดอีกครั้งเพื่อออก", Toast.LENGTH_LONG).show()
                exit = true
                Thread {
                    Thread.sleep((2 * 1000).toLong())
                    exit = false
                }.start()
            } else {
                super.onBackPressed()
            }
        }

        //รีเซตคำตอบที่เลือกก่อนหน้า
        val reset = findViewById<ImageView>(R.id.reset)
        reset.setOnClickListener {
            dataAnswer.removeAll(dataAnswer.toSet())
            textResult.text = dataAnswer.toString()
        }

        //ตรวจว่าโจทย์ว่ามีคำตอบที่มากกว่า 1 หรือเปล่า
        val submitKruskal = findViewById<TextView>(R.id.submitKruskal)
        submitKruskal.setOnClickListener {
            if (!mDataQuiz.many_events) {
                checkResult()
            } else {
                val pattern = "([0-9]+)"
                val r = Pattern.compile(pattern)
                var m: Matcher
                for (i in dataAnswer.indices) {
                    m = r.matcher(dataAnswer[i])
                    if (m.find()) dataAnswer[i] = m.group(0)!!
                }
                checkResult()
            }
            //ยกเลิดการจับเวลา
            mCountdown.cancel()
        }
        countDownTimer(mDataQuiz.time)
    }

    //ตัวจับเวลาในเกม
    private fun countDownTimer(millis: Int) {
        mProgressBar.max = millis * 1000
        mCountdown = object : CountDownTimer((millis * 1000).toLong(), 1000) {
            override fun onTick(p0: Long) {
                mProgressBar.progress = p0.toInt()
                time = (p0 / 1000).toDouble()
            }

            override fun onFinish() {
                mProgressBar.progress = 0
                checkResult()
            }
        }.start()
    }

    //ตรวจสอบคำตอบ
    private var maxScore:Int = 0
    private fun checkResult() {
        val b = mDataQuiz.mst_branch == dataAnswer
        maxScore = if (b) {
            //การคิดคะแนนของผู้เล่น
            ((time/mDataQuiz.time)*10000).toInt()
        } else {
            0
        }

        if (shareData.getInt("highs-core", 0) < maxScore) {
            shareData.edit {
                putInt("highs-core", maxScore)
            }
        }
        mDialog.openDialog(b, maxScore, dataImgMst[rank])
    }

    private inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
        }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!exit) {
            Toast.makeText(this, "กดอีกครั้งเพื่อออก", Toast.LENGTH_LONG).show()
            exit = true
            Thread {
                Thread.sleep((2 * 1000).toLong())
                exit = false
            }.start()
        } else {
            finish()
        }
    }
}