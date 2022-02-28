package com.amadydev.intermedia.ui.base

import android.app.Dialog
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.amadydev.intermedia.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    private var mProgressDialog: Dialog? = null

    fun showProgressDialog(show: Boolean, text: String? = null) {
        when {
            show -> mProgressDialog =
                Dialog(this).apply {
                    setContentView(R.layout.dialog_progress)
                    text?.let { findViewById<TextView>(R.id.tv_progress).text = it }
                    setCancelable(false)
                    show()
                }
            !show -> mProgressDialog?.dismiss()
        }
    }


    fun showErrorSnackBar(v: View, message: String) {
        Snackbar.make(
            v,
            message, Snackbar.LENGTH_LONG
        ).apply {
            view.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorPrimaryDark
                )
            )
            show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mProgressDialog?.dismiss()
        mProgressDialog = null
    }

}