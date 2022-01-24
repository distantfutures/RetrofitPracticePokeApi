package com.example.retrofitpracticepokeapi.network

data class Pokemon(
    val name: String,
    var url: String,
    val sprites: PokemonSprites
)
