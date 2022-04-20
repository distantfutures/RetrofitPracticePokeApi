package com.example.retrofitpracticepokeapi.network

data class Pokemon(
    var page: Int = 0,
    val name: String,
    var url: String,
    val sprites: PokemonSprites
)
