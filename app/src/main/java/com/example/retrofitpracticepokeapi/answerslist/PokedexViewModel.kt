package com.example.retrofitpracticepokeapi.answerslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApi
import com.example.retrofitpracticepokeapi.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

const val TAG = "CheckVM"

class PokedexViewModel : ViewModel() {

    private val pokeRetrofit = PokemonApi.retrofitService
    private val pokeRepo = PokemonRepository(pokeRetrofit)

    val pokemonListInfo: Flow<PagingData<Pokemon>> = pokeRepo.getPokedexListRep()

    init {
        Log.i(TAG, "getPokemonList INITIALIZED")
        Log.i(TAG, "getList: $pokemonListInfo")
    }
}