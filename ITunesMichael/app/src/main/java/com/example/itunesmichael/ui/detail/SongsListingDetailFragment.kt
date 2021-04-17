package com.example.itunesmichael.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.itunesmichael.R
import com.example.itunesmichael.model.SongsDetail
import com.example.itunesmichael.ui.utils.AppUtils.px
import com.example.itunesmichael.utils.AppConstants
import com.notefy.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_songs_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class SongsListingDetailFragment : BaseFragment() {
    private lateinit var host: ISongsDetailHost
    private lateinit var detail: SongsDetail

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ISongsDetailHost) {
            host = context
        } else
            throw IllegalStateException("host must implement ISongsDetailHost")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_songs_detail, container, false)
    }

    /**
     * method to display the notes data when opened in edit mode
     */
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            detail = it.getParcelable<SongsDetail>(AppConstants.IntentConstants.DATA)!!
            tvTrack.setText(getString(R.string.track_name)+" "+detail.trackName)
            tvDescription.setText(getString(R.string.artist_name)+" "+detail.artistName)
            tvCensoredName.setText(getString(R.string.censored_name)+" "+detail.collectionCensoredName)

            ivCover.setImageBitmap(getBitmapFromURL(detail.artworkUrl60))
        }
    }

    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            Log.e("src", src.toString())
            val url = URL(src)
            val connection =
                url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val myBitmap = BitmapFactory.decodeStream(input)
            Log.e("Bitmap", "returned")
            myBitmap
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("Exception", e.message.toString())
            null
        }
    }
    /**
     * method to set up toolbar
     */
    override fun setupViews() {

        (requireActivity() as AppCompatActivity).let {
            it.setSupportActionBar(tlToolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        tvTitle.text = getString(R.string.detail)

        tlToolbar.setContentInsetsAbsolute(50.px, 50.px)
        tlToolbar.setNavigationOnClickListener { popFragment() }
    }

    companion object {
        fun getInstance(detail: SongsDetail?): SongsListingDetailFragment {
            detail?.let {
                val detailFragment = SongsListingDetailFragment()
                val bundle = Bundle()
                bundle.putParcelable(AppConstants.IntentConstants.DATA, detail)
                detailFragment.arguments = bundle
                return detailFragment
            } ?: kotlin.run {
                return SongsListingDetailFragment()
            }
        }
    }

    interface ISongsDetailHost
}