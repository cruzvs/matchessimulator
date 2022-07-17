package com.cruz.matchessimulator.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place (
    @SerializedName("nome")
    var name: String,
    @SerializedName("imagem")
    var image: String
):Parcelable