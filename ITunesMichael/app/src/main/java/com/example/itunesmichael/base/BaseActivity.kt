package com.example.itunesmichael.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.itunesmichael.R
import com.example.itunesmichael.ui.utils.AppUtils
import com.notefy.base.BaseFragment

open class BaseActivity : AppCompatActivity() {

    private var mProgressDialog: Dialog? = null

    fun addFragment(layoutResId: Int, fragment: BaseFragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager.beginTransaction()
                .add(layoutResId, fragment, tag)
                .commit()
    }

    fun addFragmentWithBackStack(layoutResId: Int, fragment: BaseFragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_up, R.anim.slide_out_up,
                    R.anim.slide_in_down, R.anim.slide_out_down
                )
                .add(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }

    fun addFragmentWithStateLoss(layoutResId: Int, fragment: BaseFragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_up, R.anim.slide_out_up,
                    R.anim.slide_in_down, R.anim.slide_out_down
                )
                .add(layoutResId, fragment, tag)
                .commitAllowingStateLoss()
    }

    fun addFragmentWithBackStackWithStateLoss(
        layoutResId: Int,
        fragment: BaseFragment,
        tag: String
    ) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_up, R.anim.slide_out_up,
                    R.anim.slide_in_down, R.anim.slide_out_down
                )
                .add(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commitAllowingStateLoss()
    }

    fun replaceFragment(layoutResId: Int, fragment: BaseFragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_up, R.anim.slide_out_up,
                    R.anim.slide_in_down, R.anim.slide_out_down
                )
                .replace(layoutResId, fragment, tag)
                .commit()
    }


    fun replaceFragmentWithBackStack(layoutResId: Int, fragment: BaseFragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_up, R.anim.slide_out_up,
                R.anim.slide_in_down, R.anim.slide_out_down
            )
            .replace(layoutResId, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    fun replaceFragmentWithBackStackWithStateLoss(
        layoutResId: Int,
        fragment: BaseFragment,
        tag: String
    ) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_up, R.anim.slide_out_up,
                R.anim.slide_in_down, R.anim.slide_out_down
            )
            .replace(layoutResId, fragment, tag)
            .addToBackStack(tag)
            .commitAllowingStateLoss()
    }

    private fun getCurrentFragment(): Fragment? {
        val fragmentManager = supportFragmentManager
        return if (fragmentManager.backStackEntryCount > 0) {
            val fragmentTag =
                fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1)
                    .name
            fragmentManager.findFragmentByTag(fragmentTag)
        } else null
    }

    fun showToastLong(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showToastShort(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = AppUtils.showLoader(this)
        }
        (mProgressDialog as Dialog).show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && (mProgressDialog as Dialog).isShowing) {
            (mProgressDialog as Dialog).hide()
        }
    }

    fun hideKeyboard() {
        AppUtils.hideKeyboard(this)
    }

    fun showKeyboard() {
        AppUtils.showKeyboard(this)
    }

    fun popFragment() {
        supportFragmentManager.popBackStackImmediate()
    }

    fun onBackPressed(v: View) {
        if (supportFragmentManager.backStackEntryCount != 0) {
            popFragment()
        } else {
            finish()
        }
    }


    /**
     * hides keyboard onClick anywhere besides edit text
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE)
            && view is EditText && !view.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    this.window.decorView.applicationWindowToken,
                    0
                )
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun makeStatusBarTransparent() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        window.statusBarColor = Color.TRANSPARENT
    }

}