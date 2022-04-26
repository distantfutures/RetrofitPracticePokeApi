package com.example.retrofitpracticepokeapi.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_remote_keys")
data class PokemonResponse(
    @PrimaryKey(autoGenerate = true)
    var repoId: Int,
    var count: Int,
    var next: Int?,
    var previous: Int?,
    @Embedded
    @Ignore var results: List<Pokemon>?
) {
    constructor() : this(0, 0, null, null, null)
}
