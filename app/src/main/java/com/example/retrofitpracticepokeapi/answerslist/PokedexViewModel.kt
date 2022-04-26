package com.example.retrofitpracticepokeapi.answerslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

const val TAG = "CheckVM"

class PokedexViewModel(
    repository: PokemonRepository
) : ViewModel() {

    val pokemonListInfo: Flow<PagingData<Pokemon>> = repository.getPokedexListRep()

    init {
        Log.i(TAG, "getPokemonList INITIALIZED")
        Log.i(TAG, "getList: $pokemonListInfo")
    }
}