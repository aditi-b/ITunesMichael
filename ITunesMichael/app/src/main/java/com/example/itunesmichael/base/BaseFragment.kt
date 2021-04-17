package com.notefy.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.itunesmichael.ui.base.BaseActivity

open class BaseFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.let {
            ViewCompat.requestApplyInsets(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initLiveData()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setListeners()
        setData()
    }

    /**
     * Method to initialize class variables.
     */
    protected open fun init() {}

    /**
     * Method to initialize LiveData
     */
    protected open fun initLiveData() {}

    /**
     * Method to setup views.
     */
    protected open fun setupViews() {}

    /**
     * Method to set data.
     */
    protected open fun setData() {}

    /**
     * Method to set listeners
     */
    protected open fun setListeners() {}


    fun showToastLong(message: String) {
        (activity as? BaseActivity)?.showToastLong(message)
    }

    fun showToastShort(message: String) {
        (activity as? BaseActivity)?.showToastShort(message)
    }

    fun showProgressDialog() {
        (activity as? BaseActivity)?.showProgressDialog()
    }

    fun hideProgressDialog() {
        (activity as? BaseActivity)?.hideProgressDialog()
    }

    fun hideKeyboard() {
        (activity as? BaseActivity)?.hideKeyboard()
    }

    fun showKeyboard() {
        (activity as? BaseActivity)?.showKeyboard()
    }

    fun popFragment() {
        (activity as BaseActivity).popFragment()
    }

    override fun onStop() {
        hideProgressDialog()
        super.onStop()
    }
}