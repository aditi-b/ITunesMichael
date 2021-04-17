package com.example.itunesmichael.ui

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.itunesmichael.R
import com.example.itunesmichael.model.SongsDetail
import com.example.itunesmichael.ui.base.BaseActivity
import com.example.itunesmichael.ui.detail.SongsListingDetailFragment
import com.example.itunesmichael.ui.listing.SongsListingFragment

class HomeActivity : BaseActivity(), SongsListingFragment.ISongsListingHost,
    SongsListingDetailFragment.ISongsDetailHost {

    private lateinit var manager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        openSongsListingFragment()
    }

    fun openSongsListingFragment() {
        addFragment(
            R.id.fl_container,
            SongsListingFragment.getInstance(),
            SongsListingFragment::class.java.simpleName
        )
    }

    override fun openSongsDetailFragment(data: SongsDetail?) {
        addFragmentWithBackStack(
            R.id.fl_container,
            SongsListingDetailFragment.getInstance(data),
            SongsListingDetailFragment::class.java.simpleName
        )
    }


}