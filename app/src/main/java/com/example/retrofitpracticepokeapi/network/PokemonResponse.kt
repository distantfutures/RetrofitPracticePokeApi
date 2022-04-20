package com.example.retrofitpracticepokeapi.network

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Pokemon>
)
