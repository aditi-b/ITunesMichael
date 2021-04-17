package com.example.itunesmichael.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SongsDetail(
    @SerializedName("wrapperType") val wrapperType : String,
    @SerializedName("artistId") val artistId : Int,
    @SerializedName("collectionId") val collectionId : Int,
    @SerializedName("artistName") val artistName : String,
    @SerializedName("collectionName") val collectionName : String,
    @SerializedName("collectionCensoredName") val collectionCensoredName : String,
    @SerializedName("artistViewUrl") val artistViewUrl : String,
    @SerializedName("collectionViewUrl") val collectionViewUrl : String,
    @SerializedName("artworkUrl60") val artworkUrl60 : String,
    @SerializedName("artworkUrl100") val artworkUrl100 : String,
    @SerializedName("collectionPrice") val collectionPrice : Double,
    @SerializedName("collectionExplicitness") val collectionExplicitness : String,
    @SerializedName("trackCount") val trackCount : Int,
    @SerializedName("copyright") val copyright : String,
    @SerializedName("country") val country : String,
    @SerializedName("currency") val currency : String,
    @SerializedName("releaseDate") val releaseDate : String,
    @SerializedName("primaryGenreName") val primaryGenreName : String,
    @SerializedName("previewUrl") val previewUrl : String,
    @SerializedName("description") val description : String,
    @SerializedName("trackName") val trackName: String = "",
    @SerializedName("artworkUrl30") val artworkUrl30 : String? = null

    ): Parcelable
