package com.example.retrofitpracticepokeapi.answerslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitpracticepokeapi.model.Pokemon
import com.example.retrofitpracticepokeapi.network.PokemonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val TAG = "CheckVM"
class PokedexViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    init {
        getPokemonList()
        Log.i(TAG, "getPokemonList INITIALIZED")
    }

    // Try to reduce API calls
    private fun getPokemonList() {
        coroutineScope.launch {
            val pokeList = mutableListOf<Pokemon>()
            for (i in 0 until 151) {
                val response = PokemonApi.retrofitService.getPokedexList(i)
                if (response.isSuccessful) {
                    val pokemonObject = response.body()
                    pokeList.add(pokemonObject!!)

                    Log.i(TAG, "Pokemon Object: ${pokemonObject}")
                    Log.i(TAG, "Response Success!")
                } else {
                    Log.i(TAG, "Response Failed")
                }
            }
            _pokemonList.value = pokeList
        }
    }
}
