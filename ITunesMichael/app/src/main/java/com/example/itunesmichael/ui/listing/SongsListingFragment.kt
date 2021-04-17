package com.example.itunesmichael.ui.listing

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.itunesmichael.R
import com.example.itunesmichael.adapter.SongsListingAdapter
import com.example.itunesmichael.api.ApiInterface
import com.example.itunesmichael.model.SongsDetail
import com.example.itunesmichael.model.SongsListResponse
import com.example.itunesmichael.ui.utils.AppUtils.px
import com.notefy.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_songs_listing.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SongsListingFragment : BaseFragment() {

    private lateinit var host: ISongsListingHost

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ISongsListingHost) {
            host = context
        } else
            throw IllegalStateException("host must implement ISongsListingHost")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_songs_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).let {
            it.setSupportActionBar(tlToolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        tlToolbar.setContentInsetsAbsolute(50.px, 50.px)
        tvTitle.text = getString(R.string.playlist)

        tlToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService: ApiInterface = retrofit.create(ApiInterface::class.java)
        val call: Call<SongsListResponse> = apiService.getSongList()
        call.enqueue(object : Callback<SongsListResponse> {
            override fun onResponse(
                call: Call<SongsListResponse>,
                response: Response<SongsListResponse>
            ) {
                val statusCode: Int = response.code()
                val detail: SongsListResponse? = response.body()
                rvList.adapter = SongsListingAdapter(object : SongsListingAdapter.OnSongClickListener{
                    override fun onSongsClicked(notes: SongsDetail) {
                        host.openSongsDetailFragment(notes)
                    }
                }).apply { submitList(detail?.results) }
            }

            override fun onFailure(call: Call<SongsListResponse>, t: Throwable) {
                Log.d("ERRRR", t.message.toString())
                // Log error here since request failed
            }
        })
    }

    interface ISongsListingHost {
        fun openSongsDetailFragment(data: SongsDetail?)
    }

    companion object {
        fun getInstance(): SongsListingFragment {
            return SongsListingFragment()
        }
    }
}