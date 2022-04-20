package com.example.retrofitpracticepokeapi.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.retrofitpracticepokeapi.model.PokemonResponse

@Dao
interface PokemonResponseDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokeData: PokemonResponse)
}