package com.example.itunesmichael.ui.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.example.itunesmichael.R
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


object AppUtils {

    fun hideKeyboard(context: Activity) {
        val inputManager: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            context.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    fun showKeyboard(context: Activity) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(context.currentFocus, InputMethodManager.SHOW_IMPLICIT)
    }

    fun showLoader(context: Context): Dialog {
        val dialog = Dialog(context)
        dialog.window?.let {
            it.requestFeature(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawableResource(android.R.color.transparent)
            it.setLayout(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            )
            it.setGravity(Gravity.CENTER)
        }
        dialog.setContentView(R.layout.layout_loading_dialog)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        return dialog
    }

    fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            null
        }
    }

    val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

}
