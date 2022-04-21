package com.example.retrofitpracticepokeapi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.retrofitpracticepokeapi.answerslist.LIST_OFFSET
import com.example.retrofitpracticepokeapi.answerslist.TAG
import com.example.retrofitpracticepokeapi.database.PokemonDao
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.model.PokemonResponse
import com.example.retrofitpracticepokeapi.network.PokemonApi
import com.example.retrofitpracticepokeapi.network.PokemonApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PokemonRepository(private val service: PokemonApiService) {

//    var lastRequestedOffset = LIST_OFFSET
//
//    var pokemonFetchList = mutableListOf<Pokemon>()
//
//    private suspend fun getPokemonList() {
//        withContext(Dispatchers.IO) {
//            val response = service.getPokedexList(offset = lastRequestedOffset)
//            if (response.isSuccessful) {
//                val pokemonNamesList = response.body()
//                pokemonFetchList.addAll(pokemonNamesList?.results!!)
//
//                Log.i(TAG, "Pokemon Fetch List: ${pokemonFetchList}")
//                Log.i(TAG, "Response Success!")
//            } else {
//                Log.i(TAG, "Response Failed")
//            }
//        }
//    }

    suspend fun getPokedexList(offset: Int, limit: Int): Response<PokemonResponse> {
        return service.getPokedexList(offset = offset, limit = limit)
    }

    suspend fun getPokemonInfo(pokemonName: String): Response<Pokemon> {
        return service.getPokemonInfo(pokemonName)
    }
}