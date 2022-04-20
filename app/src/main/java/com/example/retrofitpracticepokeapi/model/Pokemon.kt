package com.example.retrofitpracticepokeapi.model

import androidx.room.Entity

@Entity (tableName = "pokemon_table")
data class Pokemon(
    var page: Int = 0,
    val name: String,
    var url: String,
    val sprites: PokemonSprites
)
