package com.example.retrofitpracticepokeapi.repository

import androidx.lifecycle.LiveData
import com.example.retrofitpracticepokeapi.database.PokemonDao
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.model.PokemonResponse
import com.example.retrofitpracticepokeapi.network.PokemonApi
import com.example.retrofitpracticepokeapi.network.PokemonApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PokemonRepository(private val service: PokemonApiService) {

    suspend fun getPokedexList(offset: Int): Response<PokemonResponse> {
        return service.getPokedexList(offset = offset)
    }

    suspend fun getPokemonInfo(pokemonName: String): Response<Pokemon> {
        return service.getPokemonInfo(pokemonName)
    }
}