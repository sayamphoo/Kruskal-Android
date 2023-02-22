package com.sayamphoo.kruskals.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sayamphoo.kruskals.R

class QuizDialog(private val context: Context) {
    private lateinit var mQuizListen:QuizListen
    private lateinit var mDialog: Dialog

    fun openDialog(boolean: Boolean, score: Int, img: Int) {
        mDialog = Dialog(context).apply {
            setContentView(R.layout.quiz_dialog)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            show()
        }

        val mAgain = mDialog.findViewById<TextView>(R.id.playAgain)
        val mBlack = mDialog.findViewById<TextView>(R.id.blackHome)
        val mTitle = mDialog.findViewById<TextView>(R.id.title)
        val mSeeAns = mDialog.findViewById<TextView>(R.id.seeAns)
        val mSeeMinimum = mDialog.findViewById<ImageView>(R.id.seeMinimum)
        val mScore = mDialog.findViewById<TextView>(R.id.score)
        mScore.text = score.toString()
        mSeeMinimum.visibility = View.GONE
        mSeeMinimum.setImageResource(img)

        if (boolean) {
            mAgain.setBackgroundResource(R.drawable.dialog_green_again_shape)
            mBlack.setBackgroundResource(R.drawable.dialog_green_black_shape)
            mTitle.setTextColor(Color.parseColor("#007B00"))
        }

        mAgain.setOnClickListener {
            mDialog.dismiss()
            mQuizListen.reLoadApp()
//            (context as Activity).finish()
//            context.startActivity(context.intent)
        }

        mBlack.setOnClickListener {
            mDialog.dismiss()
            (context as Activity).finish()

        }
        var stateSee = false
        mSeeAns.setOnClickListener {
            mSeeMinimum.visibility = if (!stateSee) {
                View.VISIBLE
            } else {
                View.GONE
            }
            stateSee = !stateSee
        }
    }

    fun setQuizListen(quizListen: QuizListen){
        mQuizListen = quizListen
    }

    interface QuizListen {
        fun reLoadApp()
    }
}