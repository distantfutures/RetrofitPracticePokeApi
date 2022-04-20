package com.example.retrofitpracticepokeapi.model

import com.google.gson.annotations.SerializedName

data class PokemonSprites (
    @SerializedName("front_default")
    val newUrl: String
)
