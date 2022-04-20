package com.example.retrofitpracticepokeapi.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity (tableName = "pokemon_table")
data class Pokemon(
    var page: Int = 0,
    @PrimaryKey
    val name: String,
    var url: String,
    @Embedded
    val sprites: PokemonSprites
)

data class PokemonSprites (
    @SerializedName("front_default")
    val newUrl: String
)
