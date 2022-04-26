package com.example.retrofitpracticepokeapi.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitpracticepokeapi.model.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(pokemon: List<Pokemon>)

    @Query("SELECT * FROM pokemon_table" )
    fun getPokemonList(): PagingSource<Int, Pokemon>

    @Query("DELETE FROM pokemon_table")
    fun clearRepos()
}