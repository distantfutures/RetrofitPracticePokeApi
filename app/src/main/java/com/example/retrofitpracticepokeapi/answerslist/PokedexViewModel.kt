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

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var lastRequestedOffset = LIST_OFFSET

    private var pokemonFetchList = mutableListOf<Pokemon>()

    private var _pokemonInfoList = MutableLiveData<List<Pokemon>>()
    val pokemonInfoList: LiveData<List<Pokemon>>
        get() = _pokemonInfoList

    val pokemonListInfo: Flow<PagingData<Pokemon>> = pokeRepo.getPokedexList()

    init {
//        getPokemonList()
        Log.i(TAG, "getPokemonList INITIALIZED")
    }

    fun requestNewList() {
        lastRequestedOffset += 20
        pokemonFetchList.clear()
//        getPokemonList()
    }

//    private fun getPokemonList() {
//        coroutineScope.launch {
//            val response = pokeRepo.getPokedexList(lastRequestedOffset, LIST_LIMIT)
//            if (response.isSuccessful) {
//                val pokemonNamesList = response.body()
//                pokemonFetchList.addAll(pokemonNamesList?.results!!)
//                getPokemon()
//                Log.i(TAG, "Pokemon Fetch List: ${_pokemonInfoList.value}")
//                Log.i(TAG, "Response Success!")
//            } else {
//                Log.i(TAG, "Response Failed")
//            }
//        }
//    }

    // Try to reduce API calls
    private fun getPokemon() {
        coroutineScope.launch {
            val pokeList = mutableListOf<Pokemon>()
            for (pokemon in pokemonFetchList) {
                val response = pokeRepo.getPokemonInfo(pokemon.name)
                if (response.isSuccessful) {
                    val pokemonInfoObj = response.body()
                    pokeList.add(pokemonInfoObj!!)

                    Log.i(TAG, "Pokemon Object: ${pokemonInfoObj}")
                    Log.i(TAG, "Response Success!")
                } else {
                    Log.i(TAG, "Response Failed")
                }
            }
            // Adds to existing list if not empty
            _pokemonInfoList.value = _pokemonInfoList.value?.plus(pokeList) ?: pokeList
        }
    }
}