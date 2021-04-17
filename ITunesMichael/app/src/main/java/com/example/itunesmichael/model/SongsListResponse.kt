package com.example.itunesmichael.model

import com.google.gson.annotations.SerializedName

data class SongsListResponse(
    @SerializedName("resultCount") val resultCount : Int,
    @SerializedName("results") val results : List<SongsDetail>
)