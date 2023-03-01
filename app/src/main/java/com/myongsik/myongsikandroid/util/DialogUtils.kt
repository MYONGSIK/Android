package com.myongsik.myongsikandroid.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.myongsik.myongsikandroid.R

class DialogUtils(private val context: Context) {
    @SuppressLint("ClickableViewAccessibility")
    fun showAlertDialog(
        fullText: String,
        value: Int,
        yesClickListener: () -> Unit,
        noClickListener: () -> Unit
    ) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.item_dialog, null)
        val titleTextView = dialogView.findViewById<TextView>(R.id.textView3)

        val spannableString = SpannableString(fullText)
        spannableString.setSpan(
            ForegroundColorSpan(Color.BLUE),
            0, value,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        titleTextView.text = spannableString

        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogView.findViewById<TextView>(R.id.btn_no).setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    dialogView.findViewById<TextView>(R.id.btn_no).setBackgroundResource(R.drawable.round_background_dialog_btn)
                    dialogView.findViewById<TextView>(R.id.btn_no).setTextColor(ContextCompat.getColor(context, R.color.white))
                }

            }
            false
        }


        dialogView.findViewById<TextView>(R.id.btn_yes).setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    dialogView.findViewById<TextView>(R.id.btn_yes).setBackgroundResource(R.drawable.round_background_dialog_right)
                    dialogView.findViewById<TextView>(R.id.btn_yes).setTextColor(ContextCompat.getColor(context, R.color.white))
                }

            }
            false
        }

        dialogView.findViewById<TextView>(R.id.btn_no).setOnClickListener {
            yesClickListener.invoke()
            alertDialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.btn_yes).setOnClickListener {
            noClickListener.invoke()
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    fun showWriteReviewDialog(yesClickListener: (EditText) -> Unit) {
        val dialogView = LayoutInflater.from(this.context).inflate(R.layout.item_review, null)

        val builder = AlertDialog.Builder(this.context)
        builder.setView(dialogView)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogView.findViewById<TextView>(R.id.review_confirm_btn).setOnClickListener {
            yesClickListener.invoke(dialogView.findViewById<EditText>(R.id.review_et))
            alertDialog.dismiss()
        }

        dialogView.findViewById<ImageView>(R.id.review_close_btn).setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }


    fun showConfirmDialog(
        titleText: String,
        contentText: String,
        yesClickListener: () -> Unit,
    ) {
        val dialogView =
            LayoutInflater.from(this.context).inflate(R.layout.item_dialog_confirm, null)

        val builder = AlertDialog.Builder(this.context)
        builder.setView(dialogView)
        if (titleText != "") {
            dialogView.findViewById<TextView>(R.id.textView3).text = titleText
        }
        if (contentText != "") {
            dialogView.findViewById<TextView>(R.id.textView_tv).text = contentText
            dialogView.findViewById<TextView>(R.id.textView).visibility = View.INVISIBLE
            dialogView.findViewById<TextView>(R.id.textView2).visibility = View.INVISIBLE

        }

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogView.findViewById<TextView>(R.id.btn_confirm).setOnClickListener {
            yesClickListener.invoke()
            alertDialog.dismiss()
        }

        alertDialog.show()
    }



}