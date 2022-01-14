package com.example.retrofitpracticepokeapi.network

import com.google.gson.annotations.SerializedName

data class PokemonList(
    //@SerializedName("Pokemon")
    val results: ArrayList<Pokemon>
)
