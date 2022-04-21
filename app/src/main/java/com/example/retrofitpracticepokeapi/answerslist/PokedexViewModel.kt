package com.example.retrofitpracticepokeapi.answerslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApi
import com.example.retrofitpracticepokeapi.network.PokemonApiService
import com.example.retrofitpracticepokeapi.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val TAG = "CheckVM"
class PokedexViewModel : ViewModel() {

    private val pokeRetrofit = PokemonApi.retrofitService
    private val pokeRepo = PokemonRepository(pokeRetrofit)

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    private var _pokemonInfoList = MutableLiveData<List<Pokemon>>()
    val pokemonInfoList: LiveData<List<Pokemon>>
        get() = _pokemonInfoList

    init {
        getPokemonList()
        Log.i(TAG, "getPokemonList INITIALIZED")
    }
    private fun getPokemonList() {
        coroutineScope.launch {
            val response = pokeRepo.getPokedexList()
            if (response.isSuccessful) {
                val pokemonNamesList = response.body()
                _pokemonList.value = pokemonNamesList?.results
                getPokemon()
                Log.i(TAG, "Pokemon Object: ${pokemonNamesList}")
                Log.i(TAG, "Response Success!")
            } else {
                Log.i(TAG, "Response Failed")
            }
        }
    }

    // Try to reduce API calls
    private fun getPokemon() {
        coroutineScope.launch {
            val pokeList = mutableListOf<Pokemon>()
            for (pokemon in pokemonList.value!!) {
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
            _pokemonInfoList.value = pokeList
        }
    }
}
