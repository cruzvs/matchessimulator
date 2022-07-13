package com.cruz.matchessimulator.domain

import com.google.gson.annotations.SerializedName

data class Place (
    @SerializedName("nome")
    var name: String,
    @SerializedName("imagem")
    var image: String
)