package com.example.retrofitpracticepokeapi.answerslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApi
import com.example.retrofitpracticepokeapi.network.PokemonApiService
import com.example.retrofitpracticepokeapi.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

const val TAG = "CheckVM"
const val LIST_OFFSET = 0
const val LIST_LIMIT = 20
class PokedexViewModel : ViewModel() {

    private val pokeRetrofit = PokemonApi.retrofitService
    private val pokeRepo = PokemonRepository(pokeRetrofit)

    var lastRequestedOffset = LIST_OFFSET

    private var pokemonFetchList = mutableListOf<Pokemon>()

    val pokemonListInfo: Flow<PagingData<Pokemon>> = pokeRepo.getPokedexListRep()

    init {
        Log.i(TAG, "getPokemonList INITIALIZED")
        Log.i(TAG, "getList: $pokemonListInfo")
    }

    fun requestNewList() {
        lastRequestedOffset += 20
        pokemonFetchList.clear()
    }
}