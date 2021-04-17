package com.example.itunesmichael.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itunesmichael.R
import com.example.itunesmichael.model.SongsDetail
import kotlinx.android.synthetic.main.row_songs.view.*


class SongsListingAdapter(
    private val onSongsClickListener: OnSongClickListener
) :
    ListAdapter<SongsDetail, SongsListingAdapter.SongsViewHolder>(
        SongsDiffCallback
    ) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        context = parent.context
        return SongsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_songs,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener {
            onSongsClickListener.onSongsClicked(currentList[position])
        }
    }

    interface OnSongClickListener {
        fun onSongsClicked(notes: SongsDetail)
    }

    inner class SongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(details: SongsDetail) {
            details.artworkUrl60?.let {
                Glide.with(context).load(it).into(itemView.ivCover)
            }
//            itemView.ivCover.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_back))
            itemView.tvTrackName.text = details.trackName
            itemView.tvArtistName.text = details.artistName
        }
    }
}

internal object SongsDiffCallback : DiffUtil.ItemCallback<SongsDetail>() {
    override fun areItemsTheSame(oldItem: SongsDetail, newItem: SongsDetail) =
        oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SongsDetail, newItem: SongsDetail) =
        oldItem == newItem
}