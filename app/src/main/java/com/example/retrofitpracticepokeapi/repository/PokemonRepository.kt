package com.example.retrofitpracticepokeapi.repository

import androidx.lifecycle.LiveData
import com.example.retrofitpracticepokeapi.database.PokemonDao
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApiService
import kotlinx.coroutines.flow.Flow

class PokemonRepository(private val service: PokemonApiService) {
    val pokemonListCache = mutableListOf<Pokemon>()

//    suspend fun getPokemonStream(): Flow<Pokemon> {
//
//    }
}