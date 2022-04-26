package com.example.retrofitpracticepokeapi.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity (tableName = "pokemon_table")
data class Pokemon(
    var page: Int = 0,
    @PrimaryKey
    var name: String,
    var url: String,
    @Embedded
    var sprites: PokemonSprites
) {
    constructor() : this(0, "pokemon", "url", PokemonSprites())
}

data class PokemonSprites (
    @SerializedName("front_default")
    var newUrl: String
) {
    constructor() : this("newUrl")
}
